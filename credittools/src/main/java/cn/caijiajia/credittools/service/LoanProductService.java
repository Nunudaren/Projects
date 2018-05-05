package cn.caijiajia.credittools.service;

import cn.caijiajia.cloud.service.FileUploadService;
import cn.caijiajia.credittools.common.constant.CredittoolsConstants;
import cn.caijiajia.credittools.common.constant.ErrorResponseConstants;
import cn.caijiajia.credittools.configuration.Configs;
import cn.caijiajia.credittools.constant.ProductFilterTypeEnum;
import cn.caijiajia.credittools.constant.ProductSortEnum;
import cn.caijiajia.credittools.domain.Product;
import cn.caijiajia.credittools.domain.ProductExample;
import cn.caijiajia.credittools.form.*;
import cn.caijiajia.credittools.mapper.ProductMapper;
import cn.caijiajia.credittools.utils.DateUtil;
import cn.caijiajia.credittools.utils.MoneyUtil;
import cn.caijiajia.credittools.vo.*;
import cn.caijiajia.framework.exceptions.CjjClientException;
import cn.caijiajia.framework.exceptions.CjjServerException;
import cn.caijiajia.framework.threadlocal.ParameterThreadLocal;
import cn.caijiajia.loanproduct.common.Resp.LoanProductResp;
import cn.caijiajia.user.common.resp.UserVo;
import cn.caijiajia.user.rpc.UserRpc;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Author:chendongdong
 * @Date:2018/4/27
 */
@Service
@Slf4j
public class LoanProductService {

    @Autowired
    private Configs configs;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private DataSourceTransactionManager txManager;

    @Autowired
    private UserRpc userRpc;

    @Autowired
    private ProductsFactory productsFactory;

    @Autowired
    private UnionLoginLogService unionLoginLogService;

    /**
     * 按条件查询产品列表
     *
     * @param loanProductListForm
     * @return
     */
    public LoanProductVo getProductList(LoanProductListForm loanProductListForm) {
        ProductExample example = new ProductExample();
        example.setOrderByClause("rank asc");
        ProductExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(loanProductListForm.getProductName())) {
            criteria.andNameIsNotNull().andNameLike("%" + loanProductListForm.getProductName() + "%");
        }
        if (StringUtils.isNotEmpty(loanProductListForm.getProductId())) {
            criteria.andProductIdIsNotNull().andProductIdEqualTo(loanProductListForm.getProductId());
        }
        if (!CredittoolsConstants.ALL_STATUS.equals(loanProductListForm.getStatus())) {
            criteria.andStatusIsNotNull().andStatusEqualTo(loanProductListForm.getStatus().equals("1"));
        }
        PageHelper.startPage(loanProductListForm.getPageNo(), loanProductListForm.getPageSize());
        List<Product> productList = productMapper.selectByExample(example);
        Integer total = productMapper.countByExample(example);
        PageInfo<LoanProductListVo> pageInfo = transForm(productList);

        return LoanProductVo.builder()
                .totalCount(total)
                .productVoList(pageInfo.getList())
                .build();
    }

    private PageInfo<LoanProductListVo> transForm(List<Product> productList) {
        List<LoanProductListVo> productVoList = Lists.newArrayList();
        for (Product product : productList) {
            LoanProductListVo loanProductListVo = LoanProductListVo.builder()
                    .rank(product.getRank())
                    .productId(product.getProductId())
                    .productName(product.getName())
                    .iconUrl(product.getIconUrl())
                    .onlineTime(product.getOnlinetime() == null ? "" : DateUtil.convert2Str(product.getOnlinetime(), DateUtil.NYRSF))
                    .offlineTime(product.getOfflinetime() == null ? "" : DateUtil.convert2Str(product.getOfflinetime(), DateUtil.NYRSF))
                    .status(product.getStatus() ? "1" : "0")//1：上线 0：下线
                    .build();
            productVoList.add(loanProductListVo);
        }
        return new PageInfo<>(productVoList);
    }

    /**
     * 修改产品的展示位置
     *
     * @param rankForm
     */
        public void upateRankByProductId(RankForm rankForm) {
        if (rankForm.getChangedRank() > getMaxRank()) {
            throw new CjjServerException(ErrorResponseConstants.CHANGED_RANK_OVERFLOW_CODE, ErrorResponseConstants.CHANGED_RANK_OVERFLOW_MSG);
        }
        ProductExample example = new ProductExample();
        ProductExample.Criteria criteria = example.or();
        if (rankForm.getCurrentRank() == rankForm.getChangedRank()) {
            return;
        } else if (rankForm.getCurrentRank() < rankForm.getChangedRank()) {
            criteria.andRankGreaterThan(rankForm.getCurrentRank()).andRankLessThanOrEqualTo(rankForm.getChangedRank());//--
        } else {
            criteria.andRankLessThan(rankForm.getCurrentRank()).andRankGreaterThanOrEqualTo(rankForm.getChangedRank());//++
        }
        List<Product> productList = productMapper.selectByExample(example);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transactionStatus = txManager.getTransaction(def);
        try {
            for (Product record : productList) {
                Integer rank;
                if (rankForm.getCurrentRank() < rankForm.getChangedRank()) {
                    rank = record.getRank() - 1;
                } else {
                    rank = record.getRank() + 1;
                }
                Product update = new Product();
                update.setId(record.getId());
                update.setRank(rank);
                productMapper.updateByPrimaryKeySelective(update);
            }
            Product toUpdate = getProductById(rankForm.getProductId());
            toUpdate.setRank(rankForm.getChangedRank());
            productMapper.updateByPrimaryKeySelective(toUpdate);
            txManager.commit(transactionStatus);
        } catch (Exception e) {
            txManager.rollback(transactionStatus);
            if (e instanceof CjjServerException) {
                log.error("更新产品位置序号失败！");
                throw new CjjServerException(((CjjServerException) e).getCode(), e.getMessage());
            }
            log.error("更新产品位置序号失败！");
            throw new CjjServerException(ErrorResponseConstants.CHANGE_PRODUCT_RANK_FAILED_CODE, ErrorResponseConstants.CHANGE_PRODUCT_RANK_FAILED_MSG);
        }

    }

    /**
     * 更改产品上/下线状态
     *
     * @param statusForm
     */
    public void updateLineStatus(StatusForm statusForm) {
        ProductExample example = new ProductExample();
        example.or().andProductIdEqualTo(statusForm.getProductId());
        List<Product> productList = productMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(productList)) {
            if (statusForm.getStatus().equals((productList.get(0).getStatus() ? "1" : "0"))) {
                log.warn("要修改的状态和当前产品的数据库状态一致，用户可能没有刷新页面");
                return;
            }
            Product update = new Product();
            BeanUtils.copyProperties(productList.get(0), update);
            update.setStatus(statusForm.getStatus().equals("1") ? true : false);
            if (update.getStatus()) {
                update.setOfflinetime(null);
                update.setOnlinetime(new Date());
            } else {
                update.setOfflinetime(new Date());
            }
            productMapper.updateByPrimaryKey(update);
        } else {
            log.error("更新产品位置序号失败！");
            throw new CjjServerException(ErrorResponseConstants.CHANGE_PRODUCT_STATUS_FAILED_CODE, ErrorResponseConstants.CHANGE_PRODUCT_STATUS_FAILED_MSG);
        }
    }


    public Product getProductById(String productId) {
        ProductExample example = new ProductExample();
        example.or().andProductIdEqualTo(productId);
        List<Product> products = productMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(products)) {
            return products.get(0);
        } else {
            log.error("产品编号：{}的贷款产品不存在！", productId);
            throw new CjjServerException(ErrorResponseConstants.GET_PRODUCT_NOT_EXIST_CODE, ErrorResponseConstants.GET_PRODUCT_NOT_EXIST_MSG);
        }
    }

    public void addOrUpdateProduct(ProductForm productForm) {
        if (productForm.getId() == null) {
            Product product = new Product();
            BeanUtils.copyProperties(productForm, product);
            product.setTags(StringUtils.join(productForm.getTags().toArray(), CredittoolsConstants.SPLIT_MARK));
            product.setStatus(false);
            product.setAomountFirst(productForm.getAmountFirst());
            product.setOfflinetime(new Date());
            product.setRank(getMaxRank() + 1);
            product.setProductId(createProductId());
            productMapper.insertSelective(product);
        } else {
            Product product = getProductById(productForm.getId());
            BeanUtils.copyProperties(productForm, product);
            product.setTags(StringUtils.join(productForm.getTags().toArray(), CredittoolsConstants.SPLIT_MARK));
            productMapper.updateByPrimaryKey(product);
        }
    }

    private Integer getMaxRank() {
        ProductExample productExample = new ProductExample();
        productExample.setOrderByClause("rank desc");
        List<Product> products = productMapper.selectByExample(productExample);
        if (CollectionUtils.isEmpty(products)) {
            return 0;
        }
        return products.get(0).getRank();
    }

    private String getMaxProductId() {
        ProductExample example = new ProductExample();
        example.setOrderByClause("rank desc");
        List<Product> productList = productMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(productList)) {
            return CredittoolsConstants.PRODUCT_ID_INITIAL_VALUE;
        }
        return productList.get(0).getProductId();
    }

    private String createProductId() {
        String maxProductId = getMaxProductId();
        if (maxProductId.length() > CredittoolsConstants.PRODUCT_ID_INITIAL_VALUE.length()) {
            return String.valueOf(Integer.valueOf(maxProductId) + 1);
        }
        Integer rank = Integer.parseInt(maxProductId.replaceAll("^(0+)", "")) + 1;
        String productId = maxProductId.substring(0, maxProductId.length() - String.valueOf(rank).length()) + String.valueOf(rank);
        return productId;
    }

    private Product getProductById(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }

    public ProductVo getProductVoById(String productId) {
        ProductVo productVo = ProductVo.builder().build();
        Product product = getProductById(productId);
        if (product == null) {
            throw new CjjClientException(ErrorResponseConstants.PRODUCT_NOT_FOUND_CODE, ErrorResponseConstants.PRODUCT_NOT_FOUND_MSG);
        }
        BeanUtils.copyProperties(product, productVo);
        setTagList(productVo, product);
        productVo.setConfigTags(getLoanProductTags());
        return productVo;
    }

    /**
     * 获取配置项标签
     *
     * @return
     */
    public List<String> getLoanProductTags() {
        List<String> tags = Lists.newArrayList();
        if (null != configs.getLoanProductTags()) {
            tags = configs.getLoanProductTags();
        }
        return tags;
    }

    private void setTagList(ProductVo productVo, Product product) {
        List<String> tag = Lists.newArrayList();
        if(StringUtils.isNotEmpty(product.getTags())){
            tag = Arrays.asList(product.getTags().split(CredittoolsConstants.SPLIT_MARK));
        }
        productVo.setTags(tag);
    }

    public String uploadImg(MultipartFile file) throws Exception {

        String serial = UUID.randomUUID().toString();
        String fileName = "LP_ICON_" + serial + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        String imgUrl;
        try {
            imgUrl = fileUploadService.uploadFile(fileName, file.getBytes());
        } catch (FileUploadException e) {
            throw new CjjServerException(ErrorResponseConstants.ERR_RESX_UPLOAD_FAILURE_CODE, ErrorResponseConstants.ERR_RESX_UPLOAD_FAILURE_MSG, e);
        }
        return imgUrl;
    }

    public Set<String> getUsedTags() {
        Set<String> set = Sets.newHashSet();
        List<String> tags = configs.getLoanProductTags();
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andStatusEqualTo(CredittoolsConstants.ONLINE_STATUS);
        List<Product> products = productMapper.selectByExample(productExample);
        if (CollectionUtils.isNotEmpty(products)) {
            for (Product product : products) {
                List<String> tagids = Lists.newArrayList(product.getTags().split(CredittoolsConstants.SPLIT_MARK));
                for (String tagid : tagids) {
                    if (tags.contains(tagid)) {
                        set.add(tagid);
                    }
                    if (set.size() == tags.size()) {
                        return set;
                    }
                }
            }
        }

        return set;
    }

    public ProductListClientVo getProductListClient(ProductListClientForm productListClientForm) {
        String guideWords = null;
        List<Product> products = getProducts(productListClientForm);
        if (null != configs.getGuideWords()) {
            guideWords = configs.getGuideWords();
        }
        List<ProductClientVo> rtnVo = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(products)) {
            rtnVo = transform(products);
        }
        return ProductListClientVo.builder()
                .loanProductList(rtnVo)
                .guideWords(guideWords)
                .build();
    }

    private List<Product> getProducts(ProductListClientForm productListClientForm) {
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andStatusEqualTo(CredittoolsConstants.ONLINE_STATUS);
        if (StringUtils.isEmpty(productListClientForm.getSortValue()) || ProductSortEnum.DEFAULT.getValue().equals(productListClientForm.getSortValue())) {
            productExample.setOrderByClause("rank asc");
        } else if (ProductSortEnum.LEND_FAST.getValue().equals(productListClientForm.getSortValue())) {
            productExample.setOrderByClause("lend_time asc");
        } else if (ProductSortEnum.FEE_RATE_LOW.getValue().equals(productListClientForm.getSortValue())) {
            productExample.setOrderByClause("annual_rate asc");
        } else if (ProductSortEnum.PASS_RATE_HIGH.getValue().equals(productListClientForm.getSortValue())) {
            productExample.setOrderByClause("pass_rate desc");
        }
        if (null == productListClientForm.getFilterType() || StringUtils.isEmpty(productListClientForm.getFilterValue())) {
            return productMapper.selectByExample(productExample);
        }
        if (ProductFilterTypeEnum.LEND_AMOUNT == productListClientForm.getFilterType()) {
            String[] filterAmount = StringUtils.split(productListClientForm.getFilterValue(), ",");
            Integer filterMinAmount = Integer.parseInt(filterAmount[0]);
            Integer filterMaxAmount = Integer.parseInt(filterAmount[1]);

            criteria.andMinAmountLessThanOrEqualTo(filterMaxAmount).andMaxAmountGreaterThanOrEqualTo(filterMinAmount);
            return productMapper.selectByExample(productExample);

        } else if (ProductFilterTypeEnum.PRODUCT_TAGS == productListClientForm.getFilterType()) {

            criteria.andTagsLike(productListClientForm.getFilterValue());
            return productMapper.selectByExample(productExample);
        }

        return null;
    }

    private List<ProductClientVo> transform(List<Product> loanProducts) {
        return Lists.newArrayList(Collections2.transform(loanProducts, new Function<Product, ProductClientVo>() {
            @Override
            public ProductClientVo apply(Product input) {
                return ProductClientVo.builder()
                        .feeRate(input.getShowFeeRate() ? input.getFeeRate() : null)
                        .iconUrl(input.getIconUrl())
                        .id(input.getProductId())
                        .jumpUrl(input.getJumpUrl() + (ParameterThreadLocal.getUid() == null? "" : "&p_u=" + ParameterThreadLocal.getUid()))
                        .mark(input.getMark())
                        .name(input.getName())
                        .promotion(input.getPromotion())
                        .optionalInfo(Lists.newArrayList(Collections2.transform(buildOptionalInfo(input), new Function<LoanProductResp.OptionalInfo, ProductClientVo.OptionalInfo>() {
                            @Override
                            public ProductClientVo.OptionalInfo apply(LoanProductResp.OptionalInfo input) {
                                return ProductClientVo.OptionalInfo.builder().type(input.getType()).value(input.getValue()).build();
                            }
                        })))
                        .build();
            }
        }));
    }

    private List<LoanProductResp.OptionalInfo> buildOptionalInfo(Product product) {
        List<LoanProductResp.OptionalInfo> optionalInfo = Lists.newArrayList();
        Integer minAmount = product.getMinAmount();
        Integer maxAmount = product.getMaxAmount();
        optionalInfo.add(LoanProductResp.OptionalInfo.builder()
                .type("额度范围")
                //最低额度与最高额度相等时只显示一个额度值
                .value(minAmount.intValue() == maxAmount.intValue() ?
                        MoneyUtil.decimalFormat(maxAmount) + "元" :
                        MoneyUtil.decimalFormat(minAmount) + "-" + MoneyUtil.decimalFormat(maxAmount) + "元")
                .build());
        if (product.getShowFeeRate()) {
            optionalInfo.add(LoanProductResp.OptionalInfo.builder()
                    .type("参考利率")
                    .value(product.getFeeRate())
                    .build());
        } else {
            optionalInfo.add(LoanProductResp.OptionalInfo.builder()
                    .type("借款周期")
                    .value(product.getLendPeriod())
                    .build());
        }
        if (!product.getAomountFirst()) {
            LoanProductResp.OptionalInfo temp = optionalInfo.get(0);
            optionalInfo.set(0, optionalInfo.get(1));
            optionalInfo.set(1, temp);
        }
        return optionalInfo;
    }

    public void unionLogin(HttpServletRequest request, HttpServletResponse response) {
        String uid = request.getParameter("p_u");
        String key = request.getParameter("key");
        String jumpUrl;
        if(StringUtils.isEmpty(uid)){
            jumpUrl = getUnionLoginUrl(key);
        }else{
            String mobile = getMobileNoCheck(uid);
            IProductsService productsService = productsFactory.getProductService(key);
            jumpUrl = productsService.unionLogin(uid, key);

            final String channelName = productsService.getChannelName();

            unionLoginLogService.addUnionLoginLog(uid, channelName, mobile);
        }

        try {
            response.sendRedirect(jumpUrl);
        } catch (IOException e) {
            throw new CjjClientException(ErrorResponseConstants.UNION_LOGIN_FAILED_CODE, ErrorResponseConstants.UNION_LOGIN_FAILED_MSG);
        }
    }

    public String getUnionLoginUrl(String key){
        final Map<String, String> unionLoginUrl = configs.getUnionLoginUrl();
        if(MapUtils.isNotEmpty(unionLoginUrl)){
            return unionLoginUrl.get(key);
        }
        log.error("未配置联合登录Url, loanmarket_union_login_url");
        throw new CjjClientException(ErrorResponseConstants.GET_UNION_URL_ERR_CODE, ErrorResponseConstants.GET_UNION_URL_ERR_MSG);
    }

    private String getMobileNoCheck(String uid) {
        UserVo userInfo = userRpc.getUserInfo(uid);
        if (userInfo == null) {
            return null;
        }
        return userInfo.getMobile();
    }

}

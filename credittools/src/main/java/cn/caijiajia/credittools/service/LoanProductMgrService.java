package cn.caijiajia.credittools.service;

import cn.caijiajia.cloud.service.FileUploadService;
import cn.caijiajia.credittools.common.constant.CredittoolsConstants;
import cn.caijiajia.credittools.common.constant.ErrorResponseConstants;
import cn.caijiajia.credittools.configuration.Configs;
import cn.caijiajia.credittools.domain.Product;
import cn.caijiajia.credittools.domain.ProductExample;
import cn.caijiajia.credittools.form.LoanProductListForm;
import cn.caijiajia.credittools.form.ProductForm;
import cn.caijiajia.credittools.form.RankForm;
import cn.caijiajia.credittools.form.StatusForm;
import cn.caijiajia.credittools.mapper.ProductMapper;
import cn.caijiajia.credittools.utils.DateUtil;
import cn.caijiajia.credittools.vo.LoanProductListVo;
import cn.caijiajia.credittools.vo.LoanProductVo;
import cn.caijiajia.credittools.vo.ProductVo;
import cn.caijiajia.framework.exceptions.CjjClientException;
import cn.caijiajia.framework.exceptions.CjjServerException;
import cn.caijiajia.redis.client.RedisClient;
import cn.caijiajia.user.rpc.UserRpc;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @Author:chendongdong
 * @Date:2018/4/27
 */
@Service
@Slf4j
public class LoanProductMgrService {

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

    @Autowired
    private RedisClient redisClient;

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
            toUpdate.setUpdatedAt(new Date());
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
        flushDb();

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
            Product update = productList.get(0);
            update.setStatus(statusForm.getStatus().equals("1") ? true : false);
            if (update.getStatus()) {
                update.setOfflinetime(null);
                update.setOnlinetime(new Date());
            } else {
                update.setOfflinetime(new Date());
            }
            update.setUpdatedAt(new Date());
            productMapper.updateByPrimaryKey(update);
        } else {
            log.error("更新产品位置序号失败！");
            throw new CjjServerException(ErrorResponseConstants.CHANGE_PRODUCT_STATUS_FAILED_CODE, ErrorResponseConstants.CHANGE_PRODUCT_STATUS_FAILED_MSG);
        }
        flushDb();
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
            product.setUpdatedAt(new Date());
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

    private String createProductId() {
        ProductExample example = new ProductExample();
        example.setOrderByClause("product_id desc");
        List<Product> productList = productMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(productList)) {
            return CredittoolsConstants.PRODUCT_ID_INITIAL_VALUE;
        }
        return addSelfProductId(productList.get(0).getProductId());
    }

    private String addSelfProductId(String maxProductId) {
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
        if (StringUtils.isNotEmpty(product.getTags())) {
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

    public String getUnionLoginUrl(String key) {
        final Map<String, String> unionLoginUrl = configs.getUnionLoginUrl();
        if (MapUtils.isNotEmpty(unionLoginUrl)) {
            return unionLoginUrl.get(key);
        }
        log.error("未配置联合登录Url, loanmarket_union_login_url");
        throw new CjjClientException(ErrorResponseConstants.GET_UNION_URL_ERR_CODE, ErrorResponseConstants.GET_UNION_URL_ERR_MSG);
    }

    /**
     * 清除缓存
     */
    private void flushDb(){
        redisClient.getRedisTemplate().execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return null;
            }
        });
    }

}

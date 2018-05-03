package cn.caijiajia.credittools.service;

import cn.caijiajia.cloud.service.FileUploadService;
import cn.caijiajia.credittools.common.constant.CredittoolsConstants;
import cn.caijiajia.credittools.common.constant.ErrorResponseConstants;
import cn.caijiajia.credittools.configuration.Configs;
import cn.caijiajia.credittools.domain.Product;
import cn.caijiajia.credittools.domain.ProductExample;
import cn.caijiajia.credittools.domain.Tag;
import cn.caijiajia.credittools.form.LoanProductListForm;
import cn.caijiajia.credittools.form.ProductForm;
import cn.caijiajia.credittools.mapper.ProductMapper;
import cn.caijiajia.credittools.vo.LoanProductListVo;
import cn.caijiajia.credittools.vo.TagVo;
import cn.caijiajia.framework.exceptions.CjjClientException;
import cn.caijiajia.framework.exceptions.CjjServerException;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileUploadException;
import cn.caijiajia.credittools.form.RankForm;
import cn.caijiajia.credittools.form.StatusForm;
import cn.caijiajia.credittools.utils.DateUtil;
import cn.caijiajia.credittools.vo.ProductVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
    private TagService tagService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private DataSourceTransactionManager txManager;

    /**
     * 按条件查询产品列表
     *
     * @param loanProductListForm
     * @return
     */
    public List<LoanProductListVo> getProductList(LoanProductListForm loanProductListForm) {
        PageHelper.startPage(loanProductListForm.getPageNo(), loanProductListForm.getPageSize());
        ProductExample example = new ProductExample();
        example.setOrderByClause("rank asc");
        ProductExample.Criteria criteria = example.or();

        if (StringUtils.isNotEmpty(loanProductListForm.getProductName())) {
            criteria.andNameIsNotNull().andNameLike("%" + loanProductListForm.getProductName() + "%");
        }
        if (StringUtils.isNotEmpty(loanProductListForm.getProductId())) {
            criteria.andProductIdIsNotNull().andProductIdEqualTo(loanProductListForm.getProductId());
        }
        if (StringUtils.isNotEmpty(loanProductListForm.getStatus())) {
            criteria.andStatusIsNotNull().andStatusEqualTo(loanProductListForm.getStatus().equals("1") ? true : false);
        }
        List<Product> productList = productMapper.selectByExample(example);
        List<LoanProductListVo> productVoList = transForm(productList);

        return productVoList;
    }

    private List<LoanProductListVo> transForm(List<Product> productList) {
        List<LoanProductListVo> productVoList = Lists.newArrayList();
        for (Product product : productList) {
            LoanProductListVo loanProductListVo = LoanProductListVo.builder()
                    .rank(product.getRank())
                    .productId(product.getProductId())
                    .productName(product.getName())
                    .iconUrl(product.getIconUrl())
                    .onlineTime(DateUtil.convert2Str(product.getOnlinetime(), DateUtil.NYRSF))
                    .offlineTime(DateUtil.convert2Str(product.getOfflinetime(), DateUtil.NYRSF))
                    .status(product.getStatus() ? "1" : "0")//1：上线 0：下线
                    .build();
            productVoList.add(loanProductListVo);
        }
        return productVoList;
    }

    /**
     * 修改产品的展示位置
     *
     * @param rankForm
     */
    public void upateRankByProductId(RankForm rankForm) {
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
            Product toUpdate = getProductRankById(rankForm.getProductId());
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
            update.setStatus(statusForm.getStatus().equals("1") ? true : false);
            productMapper.updateByPrimaryKeySelective(update);
        } else {
            log.error("更新产品位置序号失败！");
            throw new CjjServerException(ErrorResponseConstants.CHANGE_PRODUCT_STATUS_FAILED_CODE, ErrorResponseConstants.CHANGE_PRODUCT_STATUS_FAILED_MSG);
        }
    }


    public Product getProductRankById(String productId) {
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
            product.setStatus(false);
            product.setAomountFirst(productForm.getAmountFirst());
            product.setOfflinetime(new Date());
            product.setRank(getMaxRank() + 1);
            productMapper.insertSelective(product);
        } else {
            Product product = getProductById(productForm.getId());
            BeanUtils.copyProperties(productForm, product);
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

    private Product getProductById(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }

    public ProductVo getProductVoById(Integer id) {
        ProductVo productVo = ProductVo.builder().build();
        Product product = getProductById(id);
        if (product == null) {
            throw new CjjClientException(ErrorResponseConstants.PRODUCT_NOT_FOUND_CODE, ErrorResponseConstants.PRODUCT_NOT_FOUND_MSG);
        }
        String tagId = product.getTags();
        List<String> tags = Lists.newArrayList(tagId.split(CredittoolsConstants.SPLIT_MARK));
        List<ProductVo.Tag> tagList = Lists.newArrayList();
        for (String tag : tags) {
            Tag tagById = tagService.getTagById(Integer.valueOf(tag));
            if (tagById != null) {
                tagList.add(ProductVo.Tag.builder()
                        .tagId(tagById.getTagId())
                        .tagName(tagById.getTagName())
                        .build());
            }
        }
        BeanUtils.copyProperties(product, productVo);
        productVo.setTags(tagList);
        return productVo;
    }

    public String uploadImg(MultipartFile file) throws Exception  {

        String serial = UUID.randomUUID().toString();
        String fileName = "LP_ICON_" + serial + ".png";
        String imgUrl;
        try {
            imgUrl = fileUploadService.uploadFile(fileName, file.getBytes());
        } catch (FileUploadException e) {
            throw new CjjServerException(ErrorResponseConstants.ERR_RESX_UPLOAD_FAILURE_CODE, ErrorResponseConstants.ERR_RESX_UPLOAD_FAILURE_MSG, e);
        }
        return imgUrl;
    }

    public Set<TagVo> getUsedTags(){
        Set<TagVo> set = Sets.newHashSet();
//        String tagsstr = "{\n" +
//                "  \"1\": \"用芝麻分贷款\",\n" +
//                "  \"2\": \"大额贷款\",\n" +
//                "  \"3\": \"低门槛\",\n" +
//                "  \"4\": \"不查征信\",\n" +
//                "  \"5\": \"凭身份证可贷\",\n" +
//                "  \"6\": \"代还信用卡\",\n" +
//                "  \"7\": \"小额极速贷\",\n" +
//                "  \"8\": \"新用户有优惠\"\n" +
//                "}";
//        Map<String, String> tags = JSON.parseObject(tagsstr, Map.class);
        Map<String, String> tags = configs.getTags();
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andStatusEqualTo(true);
        List<Product> products = productMapper.selectByExample(productExample);
        if(CollectionUtils.isNotEmpty(products)){
            for (Product product : products) {
                List<String> tagids = Lists.newArrayList(product.getTags().split(","));
                for (String tagid: tagids) {
                    if(tags.containsKey(tagid)){
                        set.add(TagVo.builder()
                                .tagId(Integer.valueOf(tagid))
                                .tagName(tags.get(tagid))
                                .build());
                    }
                    if(set.size() == tags.size()){
                        return set;
                    }
                }
            }
        }

        return set;
    }

}

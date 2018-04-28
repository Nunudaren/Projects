package cn.caijiajia.credittools.service;

import cn.caijiajia.credittools.common.constant.CredittoolsConstants;
import cn.caijiajia.credittools.common.constant.ErrorResponseConstants;
import cn.caijiajia.credittools.constant.PaginationContext;
import cn.caijiajia.credittools.domain.Product;
import cn.caijiajia.credittools.domain.ProductExample;
import cn.caijiajia.credittools.domain.Tag;
import cn.caijiajia.credittools.form.LoanProductListForm;
import cn.caijiajia.credittools.form.ProductForm;
import cn.caijiajia.credittools.mapper.ProductMapper;
import cn.caijiajia.credittools.vo.ProductVo;
import cn.caijiajia.framework.exceptions.CjjClientException;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author:chendongdong
 * @Date:2018/4/27
 */
@Service
public class LoanProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private TagService tagService;

    /**
     * @param loanProductListForm
     * @return
     */
    public List<Product> getProductList(LoanProductListForm loanProductListForm) {
        PageHelper.startPage(PaginationContext.getPageNum(), PaginationContext.getPageSize());
        ProductExample example = new ProductExample();
        ProductExample.Criteria criteria = example.or();

        if (StringUtils.isNotEmpty(loanProductListForm.getProductName())) {
            criteria.andNameIsNotNull().andNameLike("%" + loanProductListForm.getProductName() + "%");
        }
        if (StringUtils.isNotEmpty(loanProductListForm.getProductId())) {
            criteria.andProductIdIsNotNull().andProductIdEqualTo(loanProductListForm.getProductId());
        }
        if (StringUtils.isNotEmpty(loanProductListForm.getStatus())) {
            criteria.andStatusIsNotNull().andStatusEqualTo(loanProductListForm.getStatus().equals(1) ? true : false);
        }
        List<Product> productList = productMapper.selectByExample(example);
        return productList;
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
        String tagId = product.getTagid();
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

}

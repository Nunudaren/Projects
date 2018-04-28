package cn.caijiajia.credittools.service;

import cn.caijiajia.credittools.constant.PaginationContext;
import cn.caijiajia.credittools.domain.Product;
import cn.caijiajia.credittools.domain.ProductExample;
import cn.caijiajia.credittools.domain.ProductWithBLOBs;
import cn.caijiajia.credittools.form.LoanProductListForm;
import cn.caijiajia.credittools.form.ProductForm;
import cn.caijiajia.credittools.mapper.ProductMapper;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author:chendongdong
 * @Date:2018/4/27
 */
@Service
public class LoanProductService {

    @Autowired
    private ProductMapper productMapper;

    /**
     *
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

    public void addOrUpdateProduct(ProductForm productForm){
        if(StringUtils.isEmpty(productForm.getId())){
            ProductWithBLOBs productWithBLOBs = new ProductWithBLOBs();
            BeanUtils.copyProperties(productForm, productWithBLOBs);
            productWithBLOBs.setStatus(false);
            productWithBLOBs.setRank(getMaxRank());
            productMapper.insertSelective(productWithBLOBs);
        } else {
            Product product = getProductById(productForm.getId());
            BeanUtils.copyProperties(productForm, product);
            productMapper.updateByPrimaryKey(product);
        }

    }

    private Integer getMaxRank(){
        ProductExample productExample = new ProductExample();
        productExample.setOrderByClause("rank desc");
        List<Product> products = productMapper.selectByExample(productExample);
        if(CollectionUtils.isEmpty(products)){
            return 1;
        }
        return products.get(0).getRank();
    }

    private Product getProductById(String id){
        return productMapper.selectByPrimaryKey(id);
    }


}

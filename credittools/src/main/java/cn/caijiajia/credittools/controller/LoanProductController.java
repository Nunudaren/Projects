package cn.caijiajia.credittools.controller;

import cn.caijiajia.credittools.domain.Product;
import cn.caijiajia.credittools.form.LoanProductListForm;
import cn.caijiajia.credittools.form.ProductForm;
import cn.caijiajia.credittools.form.RankForm;
import cn.caijiajia.credittools.service.LoanProductService;
import cn.caijiajia.credittools.vo.LoanProductListVo;
import cn.caijiajia.credittools.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:chendongdong
 * @Date:2018/4/27
 */
@RestController
public class LoanProductController {

    @Autowired
    private LoanProductService loanProductService;

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public void addOrUpdateProduct(@RequestBody ProductForm productForm) {
        productForm.checkField();
        loanProductService.addOrUpdateProduct(productForm);
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public ProductVo getProduct(@PathVariable Integer id) {
        return loanProductService.getProductVoById(id);
    }

    /**
     * 按条件查询产品列表
     * @param loanProductListForm
     * @return
     */
    @RequestMapping(value = "/getProductList", method = RequestMethod.POST)
    public List<LoanProductListVo> getProductList(@RequestBody LoanProductListForm loanProductListForm) {
        return loanProductService.getProductList(loanProductListForm);
    }

    @RequestMapping(value = "/getProductRank", method = RequestMethod.GET)
    public Product getProductRankById(String productId) {
        return loanProductService.getProductRankById(productId);
    }

    /**
     * 修改产品的展示位置
     * @param rankForm
     */
    @RequestMapping(value = "/changeProductRank", method = RequestMethod.POST)
    public void ChangeRankByProductId(@RequestBody RankForm rankForm) {
        loanProductService.upateRankByProductId(rankForm);
    }
}

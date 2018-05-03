package cn.caijiajia.credittools.controller;

import cn.caijiajia.credittools.domain.Product;
import cn.caijiajia.credittools.form.LoanProductListForm;
import cn.caijiajia.credittools.form.ProductForm;
import cn.caijiajia.credittools.form.RankForm;
import cn.caijiajia.credittools.form.StatusForm;
import cn.caijiajia.credittools.service.LoanProductService;
import cn.caijiajia.credittools.vo.LoanProductListVo;
import cn.caijiajia.credittools.vo.ProductVo;
import cn.caijiajia.credittools.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

/**
 * @Author:chendongdong
 * @Date:2018/4/27
 */
@RestController
public class LoanProductController {

    @Autowired
    private LoanProductService loanProductService;

    @RequestMapping(value = "/editProduct", method = RequestMethod.POST)
    public void addOrUpdateProduct(@RequestBody ProductForm productForm) {
        productForm.checkField();
        loanProductService.addOrUpdateProduct(productForm);
    }

    @RequestMapping(value = "/productDetail/{productId}", method = RequestMethod.GET)
    public ProductVo getProduct(@PathVariable String productId) {
        return loanProductService.getProductVoById(productId);
    }

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public String uploadIcon(@RequestParam MultipartFile file) throws Exception {
        return loanProductService.uploadImg(file);
    }

    /**
     * 按条件查询产品列表
     *
     * @param loanProductListForm
     * @return
     */
    @RequestMapping(value = "/getProductList", method = RequestMethod.POST)
    public List<LoanProductListVo> getProductList(@RequestBody LoanProductListForm loanProductListForm) {
        return loanProductService.getProductList(loanProductListForm);
    }

    @RequestMapping(value = "/getProductRank", method = RequestMethod.GET)
    public Product getProductById(String productId) {
        return loanProductService.getProductById(productId);
    }

    /**
     * 修改产品的展示位置
     *
     * @param rankForm
     */
    @RequestMapping(value = "/changeProductRank", method = RequestMethod.POST)
    public void changeRankByProductId(@RequestBody RankForm rankForm) {
        loanProductService.upateRankByProductId(rankForm);
    }

    /**
     *
     * 获取使用到的标签
     */
    @RequestMapping(value = "/usedTag", method = RequestMethod.GET)
    public Set<TagVo> getUsedTags() {
        return loanProductService.getUsedTags();
    }


    /**
     * 更改产品上/下线状态
     * @param statusForm
     */
    @RequestMapping(value = "/changeProductStatus", method = RequestMethod.POST)
    public void changeProductStatus(StatusForm statusForm) {
        loanProductService.updateLineStatus(statusForm);
    }
}

package cn.caijiajia.credittools.controller;

import cn.caijiajia.credittools.form.LoanProductListForm;
import cn.caijiajia.credittools.form.ProductForm;
import cn.caijiajia.credittools.form.RankForm;
import cn.caijiajia.credittools.form.StatusForm;
import cn.caijiajia.credittools.service.LoanProductMgrService;
import cn.caijiajia.credittools.vo.LoanProductVo;
import cn.caijiajia.credittools.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author:chendongdong
 * @Date:2018/4/27
 */
@RestController
public class LoanProductMgrController {

    @Autowired
    private LoanProductMgrService loanProductMgrService;

    @RequestMapping(value = "/editProduct", method = RequestMethod.POST)
    public void addOrUpdateProduct(@RequestBody ProductForm productForm) {
        productForm.checkField();
        loanProductMgrService.addOrUpdateProduct(productForm);
    }

    @RequestMapping(value = "/productDetail/{productId}", method = RequestMethod.GET)
    public ProductVo getProduct(@PathVariable String productId) {
        return loanProductMgrService.getProductVoById(productId);
    }

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public String uploadIcon(@RequestParam MultipartFile file) throws Exception {
        return loanProductMgrService.uploadImg(file);
    }

    /**
     * 按条件查询产品列表
     *
     * @param loanProductListForm
     * @return
     */
    @RequestMapping(value = "/getProductList", method = RequestMethod.POST)
    public LoanProductVo getProductList(@RequestBody LoanProductListForm loanProductListForm) {
        return loanProductMgrService.getProductList(loanProductListForm);
    }

    /**
     * 修改产品的展示位置
     *
     * @param rankForm
     */
    @RequestMapping(value = "/changeProductRank", method = RequestMethod.POST)
    public void changeRankByProductId(@RequestBody RankForm rankForm) {
        loanProductMgrService.upateRankByProductId(rankForm);
    }

    /**
     * 获取配置项标签
     * @return
     */
    @RequestMapping(value = "/productConfigTags", method = RequestMethod.GET)
    public List<String> getLoanProductTags(){
        return loanProductMgrService.getLoanProductTags();
    }

    /**
     * 更改产品上/下线状态
     *
     * @param statusForm
     */
    @RequestMapping(value = "/changeProductStatus", method = RequestMethod.POST)
    public void changeProductStatus(@RequestBody StatusForm statusForm) {
        loanProductMgrService.updateLineStatus(statusForm);
    }


}

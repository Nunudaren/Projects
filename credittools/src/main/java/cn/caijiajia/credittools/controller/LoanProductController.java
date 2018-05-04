package cn.caijiajia.credittools.controller;

import cn.caijiajia.credittools.form.*;
import cn.caijiajia.credittools.service.LoanProductService;
import cn.caijiajia.credittools.service.LoanProductsService;
import cn.caijiajia.credittools.vo.LoanProductFilterVo;
import cn.caijiajia.credittools.vo.LoanProductListVo;
import cn.caijiajia.credittools.vo.ProductListClientVo;
import cn.caijiajia.credittools.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private LoanProductsService loanProductsService;

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
     * 获取使用到的标签
     */
    @RequestMapping(value = "/usedTag", method = RequestMethod.GET)
    public Set<String> getUsedTags() {
        return loanProductService.getUsedTags();
    }

    /**
     * 贷款产品聚合页，获取贷款产品列表
     */
    @RequestMapping(value = "/v2/getLoanProductList", method = RequestMethod.GET)
    public ProductListClientVo getLoanProductListClient(ProductListClientForm productListClientForm) {
        return loanProductService.getProductListClient(productListClientForm);
    }

    /**
     * 更改产品上/下线状态
     *
     * @param statusForm
     */
    @RequestMapping(value = "/changeProductStatus", method = RequestMethod.POST)
    public void changeProductStatus(StatusForm statusForm) {
        loanProductService.updateLineStatus(statusForm);
    }

    /**
     * 贷款产品聚合页，获取筛选选项接口
     *
     * @return
     */
    @RequestMapping(value = "/getLoanProductFilter", method = RequestMethod.GET)
    public LoanProductFilterVo getLoanProductFilter() {
        return loanProductsService.getLoanProductFilter();
    }

    /**
     * 贷款产品联合登陆
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/union/login", method = RequestMethod.GET)
    public void unionLogin(HttpServletRequest request, HttpServletResponse response) {
        loanProductService.unionLogin(request, response);
    }

}

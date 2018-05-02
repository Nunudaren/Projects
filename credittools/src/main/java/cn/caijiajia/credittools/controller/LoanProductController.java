package cn.caijiajia.credittools.controller;

import cn.caijiajia.credittools.form.ProductForm;
import cn.caijiajia.credittools.service.LoanProductService;
import cn.caijiajia.credittools.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ProductVo getProduct(@PathVariable Integer id){
        return loanProductService.getProductVoById(id);
    }

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public String uploadIcon(@RequestParam MultipartFile file) throws Exception{
        return loanProductService.uploadImg(file);
    }

}

package cn.caijiajia.credittools.controller;

import cn.caijiajia.credittools.form.ProductForm;
import cn.caijiajia.credittools.service.LoanProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        productForm.valid();
        loanProductService.addOrUpdateProduct(productForm);
    }

}

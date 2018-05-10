/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.credittools.controller;

import cn.caijiajia.credittools.form.ProductListClientForm;
import cn.caijiajia.credittools.service.LoanProductService;
import cn.caijiajia.credittools.vo.LoanProductFilterVo;
import cn.caijiajia.credittools.vo.ProductListClientVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liujianyang on 2018/5/9.
 */
@RestController
public class LoanProductController {

    @Autowired
    private LoanProductService loanProductService;

    /**
     * 贷款产品聚合页，获取筛选选项接口
     *
     * @return
     */
    @RequestMapping(value = "/getLoanProductFilter", method = RequestMethod.GET)
    public LoanProductFilterVo getLoanProductFilter() {
        return loanProductService.getLoanProductFilter();
    }

    /**
     * 贷款产品聚合页，获取贷款产品列表
     */
    @RequestMapping(value = "/getLoanProductList", method = RequestMethod.GET)
    public ProductListClientVo getLoanProductListClient(ProductListClientForm productListClientForm) {
        return loanProductService.getProductListClient(productListClientForm);
    }

    /**
     * 贷款产品联合登陆
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/union/login", method = RequestMethod.GET)
    public void unionLogin(HttpServletRequest request, HttpServletResponse response) {
        loanProductService.redirectUrl(request, response);
    }
}

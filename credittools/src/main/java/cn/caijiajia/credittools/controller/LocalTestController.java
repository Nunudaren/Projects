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

import cn.caijiajia.credittools.common.req.ProductListClientReq;
import cn.caijiajia.credittools.common.resp.ProductListClientResp;
import cn.caijiajia.credittools.form.RankForm;
import cn.caijiajia.credittools.rpc.service.CredittoolsService;
import cn.caijiajia.credittools.service.LoanProductMgrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by liujianyang on 2018/4/25.
 */
@RestController
public class LocalTestController {

    @Autowired
    private LoanProductMgrService loanProductMgrService;

    @Autowired
    private CredittoolsService credittoolsService;

    @RequestMapping(value = "/test", method = GET)
    public void test(){
        System.out.println("进入test");
    }

    @RequestMapping(value = "/test/changeProductRank",method = GET)
    public void ChangeRankByProductId(){
        RankForm rankForm = RankForm.builder()
                .productId("003")
                .currentRank(3)
                .changedRank(5)
                .build();
        loanProductMgrService.upateRankByProductId(rankForm);
    }


    public static void main(String[] args) {

    }

    @RequestMapping(value = "/test/getLoanProductList", method = GET)
    public Object testGetLoanProductList(){
        return credittoolsService.getLoanProductList(ProductListClientReq.builder().build());
    }
}

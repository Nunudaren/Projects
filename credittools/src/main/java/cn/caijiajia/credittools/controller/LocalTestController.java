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

import cn.caijiajia.credittools.form.RankForm;
import cn.caijiajia.credittools.service.LoanProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liujianyang on 2018/4/25.
 */
@RestController
public class LocalTestController {

    @Autowired
    private LoanProductService loanProductService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(){
        System.out.println("进入test");
    }

    @RequestMapping(value = "/test/changeProductRank",method = RequestMethod.GET)
    public void ChangeRankByProductId(){
        RankForm rankForm = RankForm.builder()
                .productId("003")
                .currentRank(3)
                .changedRank(5)
                .build();
        loanProductService.upateRankByProductId(rankForm);
    }


    public static void main(String[] args) {

    }

}

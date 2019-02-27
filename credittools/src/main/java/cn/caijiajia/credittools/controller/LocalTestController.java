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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.util.Json;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
        Date now = new Date();
        DateTime startTimeOfDay = new DateTime(now).withTimeAtStartOfDay();
        System.out.println(startTimeOfDay);
        System.out.println(startTimeOfDay.plusHours(12).toDate());
        boolean isMorning = now.before(startTimeOfDay.plusHours(12).toDate());
        boolean isNight = now.after(startTimeOfDay.plusHours(12).toDate());
        System.out.println(isMorning + " " + isNight);


        byte[] b = "ni hao".getBytes();
        System.out.println(b.length);

        String birth = "{\"month\":\"11\",\"year\":\"1985\",\"day\":\"27\"}";
        JSONObject birth1 = JSON.parseObject(birth);
        System.out.println(birth1.get("year") + "-" + birth1.get("month") + "-" + birth1.get("day"));
    }

    @RequestMapping(value = "/test/getLoanProductList", method = GET)
    public Object testGetLoanProductList(){
        return credittoolsService.getLoanProductList(ProductListClientReq.builder().build());
    }
}

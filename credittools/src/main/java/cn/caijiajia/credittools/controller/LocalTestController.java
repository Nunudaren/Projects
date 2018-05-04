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
        String str = "000001";
        int lastZero = str.lastIndexOf("0");
        System.out.println(lastZero);

        Integer rank = Integer.parseInt(str.substring(str.lastIndexOf("0") + 1,str.length())) + 99;
        String newStr = str.substring(0, str.length() - String.valueOf(rank).length()) + String.valueOf(rank);
        System.out.println(newStr);

        String str2 = "00001400020";
        Integer b = Integer.valueOf(str2.replaceAll("^(0+)", "")) + 123;
        String newStr2 = str.substring(0, str2.length() - String.valueOf(b).length()) + String.valueOf(b);
        System.out.println(newStr2);

    }

    private static void demo1(){
        Date date1 = new Date(Long.valueOf("1524828100946"));
        System.out.println(date1);
        System.out.println(String.valueOf(new Date().getTime() /1000));
        List<String> list = new ArrayList<>();
        list.add("nihjao");
        list.add("好我");
        list.add("cde");
        String[] strArray = list.toString().split(",");
        String listToStr = StringUtils.join(list.toArray(), ",");
        Object[] objList =  list.toArray();
        System.out.println(strArray);
        System.out.println(listToStr);
        List<String> newList = new ArrayList<>();
        for(String listStr : strArray){
            newList.add(listStr);
        }
        System.out.println(newList.toString());
        String str = "";
        for(Object stri : objList){
            str = str + stri;
        }
        System.out.println(str);
    }
}

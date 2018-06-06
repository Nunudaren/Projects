/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.credittools.rpc.service;

import cn.caijiajia.credittools.common.req.ProductListClientReq;
import cn.caijiajia.credittools.common.resp.ProductClientResp;
import cn.caijiajia.credittools.common.resp.ProductListClientResp;
import cn.caijiajia.framework.rpc.BaseRpc;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by liujianyang on 2018/5/9.
 */
@Service
public class CredittoolsService extends BaseRpc{

    @Value("${url.credittools}")
    private String credittoolsUrl;

    private static final String GET_LOAN_PRODUCT_LIST = "/getLoanProductList";

    private static final String GET_LOAN_PRODUCT_DETAIL = "/getLoanProduct/%s";

    public ProductListClientResp getLoanProductList(ProductListClientReq productListClientReq){
        Map<String, String> param = Maps.newHashMap();
        param.put("filterType", productListClientReq.getFilterType().toString());
        param.put("filterValue", productListClientReq.getFilterValue());
        param.put("sortValue", productListClientReq.getSortValue());

        return JSON.parseObject(getHttpClient().doGet(credittoolsUrl + GET_LOAN_PRODUCT_LIST, param), ProductListClientResp.class);
    }

    public ProductClientResp getProductDetail(Integer id){
        String product = getHttpClient().doGet(credittoolsUrl + String.format(GET_LOAN_PRODUCT_DETAIL, id));
        if(StringUtils.isEmpty(product)){
            return null;
        }
        return JSON.parseObject(product, ProductClientResp.class);
    }

}

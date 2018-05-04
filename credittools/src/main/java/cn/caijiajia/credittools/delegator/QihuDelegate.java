/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.loanmarket.delegate;

import cn.caijiajia.framework.exceptions.CjjServerException;
import cn.caijiajia.framework.httpclient.HttpClientTemplate;
import cn.caijiajia.loanmarket.common.resp.QihuResp;
import cn.caijiajia.loanmarket.util.Base64Utils;
import cn.caijiajia.loanmarket.util.CommonUtil;
import cn.caijiajia.loanmarket.util.RSAUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Created by liujianyang on 2018/4/24.
 */
@Service
@Slf4j
public class QihuDelegate {
    @Autowired
    private HttpClientTemplate httpClientTemplate;

    @Value("${qihu360.private.key}")
    private String privateKey;

    @Value("${qihu360.httpHost}")
    private String host;

    private static final String FORMAT = "json";

    private static final String SIGN_TYPE = "RSA";

    private static final String VERSION = "1.0";

    private static final String CHANNEL = "huanbei";

    public QihuResp invoke(Object request, String method) {
        try {
            //1.将request转换为map
            Map<String,String> paramMap = Maps.newHashMap();

            paramMap.put("biz_data", JSON.toJSONString(request));
            paramMap.put("channel", CHANNEL);
            paramMap.put("method", method);
            paramMap.put("sign_type", SIGN_TYPE);
            paramMap.put("timestamp", String.valueOf(new Date().getTime()/1000));
            paramMap.put("version", VERSION);
            paramMap.put("format", FORMAT);


            //2.对map键进行排序并连接为字符串
            String paramsStr = CommonUtil.getSortParams(paramMap);

            //3.对参数进行加密
            byte[] bytes = RSAUtils.generateSHA1withRSASigature(paramsStr, privateKey);

            //4.生成签名
            String sign = Base64Utils.encode(bytes);
            paramMap.put("sign", sign);
            httpClientTemplate.omitAttachedStatisticsParamOnce();

            //5.发送请求
            String result = httpClientTemplate.doPost(host, paramMap);

            return JSON.parseObject(result, QihuResp.class);
        } catch (Exception e) {
            log.error("qihu360 invoke error:",e);
            throw new CjjServerException(e);
        }
    }
}

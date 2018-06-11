/*
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.credittools.service;

import cn.caijiajia.credittools.bo.UnionJumpBo;
import cn.caijiajia.credittools.common.constant.ErrorResponseConstants;
import cn.caijiajia.credittools.common.req.DianrongRegisterReq;
import cn.caijiajia.credittools.common.resp.DianrongGetTokenResp;
import cn.caijiajia.credittools.common.resp.DianrongRegisterResp;
import cn.caijiajia.credittools.constant.UnionLoginChannelEnum;
import cn.caijiajia.credittools.utils.CommonUtil;
import cn.caijiajia.framework.exceptions.CjjClientException;
import cn.caijiajia.framework.exceptions.CjjServerException;
import cn.caijiajia.framework.httpclient.HttpClientTemplate;
import cn.caijiajia.user.common.resp.UserVo;
import cn.caijiajia.user.rpc.UserRpc;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @Author:chendong
 * @Date:2018/6/5
 */
@Service
@Slf4j
public class DianrongUnionLoginService implements IProductsService {

    private static final String clientSourceType = "BD";
    private static final String type = "SPEEDLOAN";

    @Value("${dianrong.channel.appId}")
    private String appId;
    @Value("${dianrong.channel.appSecret}")
    private String appSecret;
    @Value("${dianrong.channel.channelId}")
    private String channelId;
    @Value("${dianrong.channel.token.req.url}")
    private String channelTokenReqUrl;
    @Value("${dianrong.channel.registration.url}")
    private String registerUrl;
    @Value("${dianrong.union.login.success.url}")
    private String unionLoginSuccessUrl;
    @Autowired
    private UserRpc userRpc;
    @Autowired
    private LoanProductMgrService loanProductMgrService;
    @Autowired
    private HttpClientTemplate httpClient;

    /**
     * 获取授权token -> 用户注册
     * @param uid
     * @param key
     * @return
     */
    @Override
    public UnionJumpBo unionLogin(String uid, String key) {

        UnionJumpBo jumpBo = UnionJumpBo.builder().jumpUrl(loanProductMgrService.getUnionLoginUrl(key)).build();

        DianrongGetTokenResp dianrongGetTokenResp;
        try {
            dianrongGetTokenResp = getTokenContent();
        } catch (Exception e) {
           return jumpBo;
        }
        String token = null;
        if ("success".equals(dianrongGetTokenResp.getResult()) && dianrongGetTokenResp.getContent() != null) {
            token = dianrongGetTokenResp.getContent().getResult();
        }
        if (StringUtils.isEmpty(token)) {
            log.warn("request channel authorization token failed !");
            return jumpBo;
        }

        String mobile = getMobile(uid);
        DianrongRegisterReq dianrongRegisterReq = DianrongRegisterReq.builder()
                .mobile(mobile)
                .clientSourceType(clientSourceType)
                .type(type)
                .build();

        Map<String, String> httpHeaders = Maps.newHashMap();
        httpHeaders.put("X-Channel-Authorization", token);

        String registerReqUrl = registerUrl.replace("{channelId}", channelId);
        registerReqUrl = registerReqUrl + "?mobile=" + mobile + "&clientSourceType=" + clientSourceType + "&type=" + type;

        String resultParam = null;
        try {
            resultParam = httpClient.doPost(registerReqUrl, dianrongRegisterReq, httpHeaders);
        } catch (Exception e) {
            log.warn("register failed! resultParam: " + resultParam);
            return jumpBo;
        }

        DianrongRegisterResp dianrongRegisterResp = JSON.parseObject(resultParam, DianrongRegisterResp.class);
        if (dianrongRegisterResp.getBorrowerId() == null) {
            log.warn("register failed! user borrowerId is null");
            return jumpBo;
        }
        if (!dianrongRegisterResp.getIsNewUser()) {
            jumpBo.setIsOldUser(true);
            return UnionJumpBo.builder().jumpUrl(unionLoginSuccessUrl).isOldUser(true).build();
        }
        //新用户在点融注册成功
        return UnionJumpBo.builder().jumpUrl(unionLoginSuccessUrl).isOldUser(false).build();
    }

    /**
     * 获取授权token
     * @return
     */
    public DianrongGetTokenResp getTokenContent() {
        Map<String, String> reqParm = Maps.newHashMap();
        reqParm.put("appId", appId);
        reqParm.put("appSecret", appSecret);
        String resultParam = null;
        try {
            resultParam = httpClient.doGet(channelTokenReqUrl, reqParm);
        } catch (Exception e) {
            log.warn("request channel authorization token failed! " + resultParam);
            throw new CjjServerException(ErrorResponseConstants.REQUEST_TOKEN_FAIL_CODE, ErrorResponseConstants.REQUEST_TOKEN_FAIL_MSG);
        }
        DianrongGetTokenResp dianrongGetTokenResp = JSON.parseObject(resultParam, DianrongGetTokenResp.class);
        return dianrongGetTokenResp;
    }

    private String getMobile(String uid) {
        UserVo userInfo;
        try {
            userInfo = userRpc.getUserInfo(uid);
        } catch (Exception e) {
            log.error("没有找到该用户,uid:{}", uid);
            throw new CjjClientException(ErrorResponseConstants.USER_NOT_EXISTS_CODE, ErrorResponseConstants.USER_NOT_EXISTS_MESSAGE);
        }
        if (userInfo == null && !StringUtils.isEmpty(userInfo.getMobile())) {
            log.error("没有找到该用户,uid:{}", uid);
            throw new CjjClientException(ErrorResponseConstants.USER_NOT_EXISTS_CODE, ErrorResponseConstants.USER_NOT_EXISTS_MESSAGE);
        }
        return userInfo.getMobile();
    }

    @Override
    public String getChannelName() {
        return UnionLoginChannelEnum.DIANRONGMOJIE.toString();
    }
}

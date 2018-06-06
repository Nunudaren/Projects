/**
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
import cn.caijiajia.credittools.constant.UnionLoginChannelEnum;
import cn.caijiajia.credittools.utils.CommonUtil;
import cn.caijiajia.credittools.utils.MD5Utils;
import cn.caijiajia.framework.exceptions.CjjClientException;
import cn.caijiajia.framework.httpclient.HttpClientTemplate;
import cn.caijiajia.user.common.resp.UserVo;
import cn.caijiajia.user.rpc.UserRpc;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by liujianyang on 2018/6/5.
 */
@Service
@Slf4j
public class JinniuService implements IProductsService {

    @Autowired
    private HttpClientTemplate httpClientTemplate;

    @Autowired
    private UserRpc userRpc;

    @Autowired
    private LoanProductMgrService loanProductMgrService;

    @Value("${jinniu.apiurl}")
    private String jinniuUrl;

    @Value("${jinniu.appid}")
    private String jinniuAppId;

    @Value("${jinniu.secret}")
    private String secret;

    private static final String METHOD = "login.quick";

    @Override
    public UnionJumpBo unionLogin(String uid, String key) {
        String mobile = getMobile(uid);
        UnionJumpBo jumpBo = UnionJumpBo.builder().jumpUrl(loanProductMgrService.getUnionLoginUrl(key)).build();
        Map<String, String> params = Maps.newHashMap();

        params.put("mobile", mobile);
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));

        String paramsStr = CommonUtil.getSortParams(params);

        try {
            String sign = HmacUtils.hmacSha256Hex(secret, MD5Utils.getMD5String(paramsStr));
            URIBuilder uriBuilder = new URIBuilder(jinniuUrl);
            uriBuilder.addParameter("api", METHOD);
            uriBuilder.addParameter("version", "1.0");
            uriBuilder.addParameter("app_id", jinniuAppId);
            uriBuilder.addParameter("params", paramsStr);
            uriBuilder.addParameter("sign", MD5Utils.getMD5String(sign));
            jumpBo.setJumpUrl(uriBuilder.toString());

            return jumpBo;
        } catch (Exception e) {
            log.warn("生成连接失败", e);
            return jumpBo;
        }
    }

    private String getMobile(String uid) {
        UserVo userInfo = userRpc.getUserInfo(uid);
        if (userInfo == null) {
            log.warn("没有找到该用户,uid:{}", uid);
            throw new CjjClientException(ErrorResponseConstants.USER_NOT_EXISTS_CODE, ErrorResponseConstants.USER_NOT_EXISTS_MESSAGE);
        }
        return userInfo.getMobile();
    }

    @Override
    public String getChannelName() {
        return UnionLoginChannelEnum.JINNIU.toString();
    }
}

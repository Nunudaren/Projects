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

import cn.caijiajia.credittools.common.constant.ErrorResponseConstants;
import cn.caijiajia.credittools.common.req.UnionLoginReq;
import cn.caijiajia.credittools.common.resp.QihuResp;
import cn.caijiajia.credittools.configuration.Configs;
import cn.caijiajia.credittools.constant.UnionLoginChannelEnum;
import cn.caijiajia.credittools.delegator.QihuDelegate;
import cn.caijiajia.framework.exceptions.CjjClientException;
import cn.caijiajia.user.common.resp.UserVo;
import cn.caijiajia.user.rpc.UserRpc;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by liujianyang on 2018/4/24.
 */
@Service
@Slf4j
public class QihuService implements IProductsService {

    @Autowired
    private QihuDelegate qihuDelegate;

    @Autowired
    private Configs configs;

    @Autowired
    private UserRpc userRpc;

    @Autowired
    private LoanProductService loanProductService;

    private static final String METHOD = "user.unionlogin";

    @Override
    public String unionLogin(String uid, String key) {
        String mobile = getMobile(uid);
        UnionLoginReq unionLoginReq = UnionLoginReq.builder().mobile(mobile).build();
        QihuResp qihuResp = qihuDelegate.invoke(unionLoginReq, METHOD);
        if (qihuResp.getCode() == 200) {
            if (qihuResp.getData() == null || StringUtils.isEmpty(qihuResp.getData().getLoginUrl())) {
                return loanProductService.getUnionLoginUrl(key);
            }
            return qihuResp.getData().getLoginUrl();
        } else {
            return loanProductService.getUnionLoginUrl(key);
        }
    }

    private String getMobile(String uid){
        UserVo userInfo = userRpc.getUserInfo(uid);
        if(userInfo == null){
            log.warn("没有找到该用户,uid:{}", uid);
            throw new CjjClientException(ErrorResponseConstants.USER_NOT_EXISTS_CODE, ErrorResponseConstants.USER_NOT_EXISTS_MESSAGE);
        }
        return userInfo.getMobile();
    }

    @Override
    public String getChannelName() {
        return UnionLoginChannelEnum.QIHU360.toString();
    }
}

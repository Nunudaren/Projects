/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.credittools.delegator;

import cn.caijiajia.framework.httpclient.HttpClientTemplate;
import cn.caijiajia.user.common.resp.UserVo;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Harry on 2018/5/22.
 */
@Component
@Slf4j
public class UserDelegator {

    /** 无密码注册URL */
    private static final String NO_PASSWORD_REGISTER_URL = "/openLogin";

    @Autowired
    private HttpClientTemplate httpClient;

    @Autowired
    private String userUrl;

    private String getUrl(String url) {
        return userUrl + url;
    }

    /**
     * 无密码注册
     *
     * @param mobile
     * @return
     */
    public UserVo noPasswordRegister(String mobile) {
        return JSON.parseObject(httpClient.doPost(getUrl(NO_PASSWORD_REGISTER_URL) + "/" + mobile, Maps.newHashMap()), UserVo.class);
    }
}
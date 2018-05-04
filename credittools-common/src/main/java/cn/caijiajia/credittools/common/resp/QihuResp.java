/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.loanmarket.common.resp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by liujianyang on 2018/4/24.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QihuResp {
    private Integer code;
    private String msg;
    private LoginInfo data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginInfo{

        @JSONField(name = "is_new")
        private Integer isNew;

        @JSONField(name = "from_channel")
        private Integer fromChannel;

        @JSONField(name = "login_url")
        private String loginUrl;
    }
}

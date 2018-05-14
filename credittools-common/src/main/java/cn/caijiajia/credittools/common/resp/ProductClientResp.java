/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.credittools.common.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by liujianyang on 2018/5/3.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductClientResp {
    private String id;

    private String name;

    private String iconUrl;

    private String mark;

    private String feeRate;

    private String promotion;

    private String jumpUrl;

    private String clickNum;

    private List<OptionalInfo> optionalInfo;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptionalInfo{
        private String type;
        private String value;
    }
}

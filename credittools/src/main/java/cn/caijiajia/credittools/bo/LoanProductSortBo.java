/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.credittools.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by liujianyang on 2018/5/4.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanProductSortBo {
    private String name;
    private List<LoanProductFilterBo.Option> options;

    @Data
    public static class Option {
        private String desp;
        private String value;
    }
}
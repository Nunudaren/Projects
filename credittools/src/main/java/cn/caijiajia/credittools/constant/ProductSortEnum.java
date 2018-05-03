/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.credittools.constant;

/**
 * Created by liujianyang on 2018/5/3.
 */
public enum ProductSortEnum {
    LEND_FAST("lendFast"), FEE_RATE_LOW("feeRateLow"), PASS_RATE_HIGH("passRateHigh"), DEFAULT("default");

    private String value;

    ProductSortEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

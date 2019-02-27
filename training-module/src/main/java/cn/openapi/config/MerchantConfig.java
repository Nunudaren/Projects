/**
 * Shuhe confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.openapi.config;

/**
 * Created by gogogo on 17/11/3.
 */
public class MerchantConfig {

    private boolean sign;

    private String merchantCode;

    private String shuheHost;

    public MerchantConfig(boolean sign, String merchantCode, String shuheHost) {
        this.sign = sign;
        this.merchantCode = merchantCode;
        this.shuheHost = shuheHost;
    }

    /**
     * @return true - sign request by merchant; false - do not sign
     */
    public boolean getSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    /**
     * @return merchant code, provided by shuhe
     */
    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    /**
     * @return the host address of shuhe
     */
    public String getShuheHost() {
        return shuheHost;
    }

    public void setShuheHost(String shuheHost) {
        this.shuheHost = shuheHost;
    }
}

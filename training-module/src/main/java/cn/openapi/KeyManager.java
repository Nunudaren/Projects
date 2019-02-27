/**
 * Shuhe confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.openapi;

/**
 * Created by gogogo on 17/11/3.
 */
public interface KeyManager {

    /**
     * @return shuhe encrypt key version
     */
    public String getShuheEncryptKeyVersion();

    /**
     * @return shuhe encrypt public key
     */
    public String getShuheEncryptKey();

    /**
     * @return merchant sign & encrypt key version
     */
    public String getMerchantKeyVersion();

    /**
     * @return merchant private key, RSA private key encoded in Base64
     */
    public String getMerchantPrivateKey();

}

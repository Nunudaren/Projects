/**
 * Shuhe confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.openapi.util;

import com.google.common.collect.Maps;
import org.apache.commons.codec.binary.Base64;

import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * Created by gogogo on 17/11/3.
 */
public class EncryptUtil {

    /**
     * @param parameters
     * @param requestBody
     * @param shuheEncryptKeyVersion
     * @param shuheEncryptKey
     * @param merchantCode
     * @return encrypted parameters & requestBody
     * @throws GeneralSecurityException
     */
    public static Object[] encrypt(Map<String, String> parameters, String requestBody,
                                   String shuheEncryptKeyVersion, String shuheEncryptKey, String merchantCode)
            throws GeneralSecurityException {

        byte[] desedeKey = TripleDesUtil.genDESedeKey();

        StringBuilder sb = new StringBuilder();
        if (parameters == null) {
            parameters = Maps.newHashMap();
        }
        for (String key : parameters.keySet()) {
            sb.append(key).append("=").append(parameters.get(key)).append("&");
        }
        String parameterString = sb.deleteCharAt(sb.length() - 1).toString();
        String encryptedParameterString = Base64.encodeBase64String(TripleDesUtil.encrypt(parameterString.getBytes(Constant.DEFAULT_CHARSET), desedeKey));

        String encryptedRequestBody = null;
        if (requestBody != null) {
            encryptedRequestBody = Base64.encodeBase64String(TripleDesUtil.encrypt(requestBody.getBytes(Constant.DEFAULT_CHARSET), desedeKey));
        }

        String encryptedDesedeKey = Base64.encodeBase64String(RsaUtil.encryptByPublicKey(desedeKey, Base64.decodeBase64(shuheEncryptKey)));

        Map<String, String> newParameters = Maps.newHashMap();
        newParameters.put("k", encryptedDesedeKey);
        newParameters.put("sh_cpv", shuheEncryptKeyVersion);
        newParameters.put("cp", encryptedParameterString);
        newParameters.put("r_c", merchantCode);

        return new Object[]{newParameters, encryptedRequestBody};
    }
}

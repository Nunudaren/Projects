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

import cn.openapi.exception.VerifySignException;
import com.google.common.collect.Maps;
import org.apache.commons.codec.binary.Base64;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by gogogo on 17/11/3.
 */
public class SignUtil {

    /**
     * add sign related parameters to parameters map.
     *
     * @param uri
     * @param parameters
     * @param requestBody
     * @param keyVersion
     * @param privateKey
     * @return parameters with sign
     * @throws GeneralSecurityException
     */
    public static Map<String, String> sign(String uri, Map<String, String> parameters, String requestBody, String keyVersion, String privateKey)
            throws GeneralSecurityException {
        if (parameters == null) {
            parameters = Maps.newHashMap();
        }
        String stringToBeSigned = buildStringToBeSigned(uri, parameters, requestBody, keyVersion);

        String sign = Base64.encodeBase64String(RsaUtil.rsaSign(stringToBeSigned.getBytes(Constant.DEFAULT_CHARSET), Base64.decodeBase64(privateKey)));
        parameters.put("r_s", sign);

        return parameters;
    }

    /**
     * verify sign, throws VerifySignException when failed
     *
     * @param responseBody
     * @param shuheSignKey
     * @param sign
     * @throws GeneralSecurityException
     * @throws VerifySignException
     */
    public static void verifySign(String responseBody, String shuheSignKey, String sign)
            throws GeneralSecurityException, VerifySignException {
        boolean result = RsaUtil.rsaVerify(responseBody.getBytes(Constant.DEFAULT_CHARSET), Base64.decodeBase64(shuheSignKey), Base64.decodeBase64(sign));
        if (!result) {
            throw new VerifySignException();
        }
    }

    private static String buildStringToBeSigned(String uri, Map<String, String> parameters, String requestBody, String keyVersion) {
        parameters.put("r_t", generateTimestamp());
        parameters.put("pv", keyVersion);
        String orderedParameters = orderParameters(parameters);
        return uri + "?" + orderedParameters + (requestBody == null ? "" : requestBody);
    }

    private static String orderParameters(Map<String, String> parameters) {
        List<String> sortedKeys = new ArrayList<String>(parameters.keySet());
        Collections.sort(sortedKeys);

        StringBuilder sb = new StringBuilder();
        for (String key : sortedKeys) {
            sb.append(key).append("=").append(parameters.get(key)).append("&");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    private static String generateTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }
}

/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.credittools.service.pengyuan.test;

import cn.caijiajia.credittools.service.pengyuan.core.RsaKeyManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author:chendongdong
 * @Date:2018/5/9
 */
@Component
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class PyRsaKeyManagerTest implements RsaKeyManager {

    @Value("${pengyuan.publicKey}")
    private String pyPublicKey;
    @Value("${pengyuan.selfPrivateKey}")
    private String selfPrivateKey;

    /**
     * 鹏元RSA公钥(加密AES随机密钥)
     */
//    private String pyPublicKey;

    /**
     * 自有RSA私钥
     */
//    private String selfPrivateKey;

//    public PyRsaKeyManagerTest() {
//        //生成自有RSA私钥
//        Map<String, Object> rsaMap;
//        try {
//            rsaMap = OpenApiEncryptUtil.generateRSAKeyPairs();
//            //提供给鹏元的自有RSA公钥
//            String publicKey = (String) rsaMap.get("publicKey");
//            System.out.println("publicKey: " + publicKey);
//            selfPrivateKey = (String) rsaMap.get("privateKey");
//            System.out.println(selfPrivateKey);
//        } catch (NoSuchAlgorithmException e) {
//            log.error(e.getMessage());
//        }
//    }

    @Override
    public String getPyPublicKey() {
        return pyPublicKey;
    }

    @Override
    public String getSelfPrivateKey() {
        return selfPrivateKey;
    }

    @Test
    public void test() {

        System.out.println(selfPrivateKey);
        System.out.println(pyPublicKey);
    }
}

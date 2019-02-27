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

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;


public class TripleDesUtil {

    private static final String KEY_ALGORITHM_3DES = "DESede";

    private static final int KEY_SIZE_3DES = 168;

    private static final String CIPHER_ALGORITHM_3DES = "DESede/ECB/PKCS5Padding";

    private static final String PROVIDER_BOUNCY_CASTAL = "BC";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * Generates a 3DES key in byte[] representation.
     *
     * @return The byte[] representation of the 3DES key
     * @throws GeneralSecurityException
     */
    public static byte[] genDESedeKey() throws GeneralSecurityException {
        final KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM_3DES, PROVIDER_BOUNCY_CASTAL);
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(secureRandom.generateSeed(16));
        kg.init(KEY_SIZE_3DES, secureRandom);
        Key secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * Encrypts the data with the passed in 3DES key, using the passed in 3DES algorithm
     *
     * @param data      The data to be encrypted
     * @param keybyte   The 3DES key
     * @param iv        The {@code IvParameterSpec} in bytes, note that for some modes like ECB, it does not require this parameter,
     *                  pass in null simply in this case
     * @param algorithm The algorithm to use, such as "DESede/ECB/PKCS5Padding"
     * @return The encrypted data
     * @throws GeneralSecurityException
     */
    private static byte[] encrypt(byte[] data, byte[] keybyte, byte[] iv, String algorithm) throws GeneralSecurityException {
        final Key key = byte2DESedeKey(keybyte);
        final Cipher cipher = Cipher.getInstance(algorithm, PROVIDER_BOUNCY_CASTAL);
        if (null == iv) {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } else {
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
        }
        return cipher.doFinal(data);
    }


    /**
     * Encrypts the data with the passed in 3DES key, using the algorithm "DESede/ECB/PKCS5Padding".
     *
     * @param data    The data to be encrypted
     * @param keybyte The 3DES key
     * @return The encrypted data
     * @throws GeneralSecurityException
     */
    public static byte[] encrypt(byte[] data, byte[] keybyte) throws GeneralSecurityException {
        return encrypt(data, keybyte, null, CIPHER_ALGORITHM_3DES);
    }

    /**
     * Converts the byte[] representation key into a {@code Key} instance.
     *
     * @param keybyte The byte[] representation of the key
     * @throws GeneralSecurityException
     * @return A {@code Key} instance
     */
    private static Key byte2DESedeKey(final byte[] keybyte) throws GeneralSecurityException {
        final DESedeKeySpec dks = new DESedeKeySpec(keybyte);
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM_3DES);
        return keyFactory.generateSecret(dks);
    }

}

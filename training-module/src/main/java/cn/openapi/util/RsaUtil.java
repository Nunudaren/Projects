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
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class RsaUtil {

    private static final String KEY_ALGORITHM_RSA = "RSA";

    private static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";

    private static final String CIPHER_ALGORITHM_RSA = "RSA/ECB/PKCS1Padding";

    private static final String PROVIDER_BOUNCY_CASTAL = "BC";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * Converts the byte[] representation key into a {@code RSAPrivateKey} instance in PKCS8EncodedKeySpec.
     *
     * @param keybyte The byte[] representation key
     * @return The corresponding {@code RSAPrivateKey} instance
     * @throws GeneralSecurityException
     */
    private static PrivateKey byte2PrivateKey(byte[] keybyte) throws GeneralSecurityException {
        final PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keybyte);
        final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
        return keyFactory.generatePrivate(pkcs8KeySpec);
    }

    /**
     * Converts the byte[] representation key into a {@code RSAPublicKey} instance in X509EncodedKeySpec.
     *
     * @param keybyte The byte[] representation
     * @return The corresponding {@code RSAPublicKey} instance
     * @throws GeneralSecurityException
     */
    private static PublicKey byte2PublicKey(byte[] keybyte) throws GeneralSecurityException {
        final X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keybyte);
        final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
        return keyFactory.generatePublic(x509KeySpec);
    }

    /**
     * Signs the data using the given RSA private key.
     * <p>
     * This with delegate the signing to the {@code java.security.Signature} with the passed in algorithm.
     * </p>
     *
     * @param data               The data to be signed
     * @param privateKey         The key used to init the signing
     * @param signatureAlgorithm The signature algorithm, such as SHA1WithRSA
     * @return The generated signature
     * @throws GeneralSecurityException
     * @see {@code rsaVerify(byte[], byte[], byte[], String)} method
     */
    public static byte[] rsaSign(byte[] data, byte[] privateKey, String signatureAlgorithm) throws GeneralSecurityException {
        PrivateKey priKey = byte2PrivateKey(privateKey);
        Signature signature = Signature.getInstance(signatureAlgorithm);
        signature.initSign(priKey);
        signature.update(data);
        return signature.sign();
    }

    /**
     * Verifies the data signature using the given RSA public key.
     * <p>
     * This with delegate the verification to the {@code java.security.Signature} with the passed in algorithm.
     * </p>
     *
     * @param data               The data to be verified
     * @param publicKey          The key used to init the verification
     * @param sign               The data signature
     * @param signatureAlgorithm The signature algorithm, such as SHA1WithRSA
     * @return {@code True} if the verification passes, otherwise {@code False}
     * @throws GeneralSecurityException
     * @see {@code rsaSign(byte[], byte[], String)} method
     */
    public static boolean rsaVerify(byte[] data, byte[] publicKey, byte[] sign, String signatureAlgorithm)
            throws GeneralSecurityException {
        PublicKey pubKey = byte2PublicKey(publicKey);
        Signature signature = Signature.getInstance(signatureAlgorithm);
        signature.initVerify(pubKey);
        signature.update(data);
        return signature.verify(sign);
    }

    /**
     * Signs the data using the given RSA private key.
     * <p>
     * This with delegate the signing to the {@code java.security.Signature} with the algorithm SHA1WithRSA.
     * </p>
     *
     * @param data       The data to be signed
     * @param privateKey The key used to init the signing
     * @return The generated signature
     * @throws GeneralSecurityException
     * @see {@code rsaVerify(byte[], byte[], byte[]} method
     */
    public static byte[] rsaSign(byte[] data, byte[] privateKey) throws GeneralSecurityException {
        return rsaSign(data, privateKey, SIGNATURE_ALGORITHM);
    }

    /**
     * Verifies the data signature using the given RSA public key.
     * <p>
     * This with delegate the verification to the {@code java.security.Signature} with the algorithm SHA1WithRSA.
     * </p>
     *
     * @param data      The data to be verified
     * @param publicKey The key used to init the verification
     * @param sign      The data signature
     * @return {@code True} if the verification passes, otherwise {@code False}
     * @throws GeneralSecurityException
     * @see {@code rsaSign(byte[], byte[]} method
     */
    public static boolean rsaVerify(byte[] data, byte[] publicKey, byte[] sign)
            throws GeneralSecurityException {
        return rsaVerify(data, publicKey, sign, SIGNATURE_ALGORITHM);
    }

    /**
     * Encrypts the data with the public key.
     *
     * @param data      The data to be encrypted
     * @param keybyte   The public key used to encrypt the data
     * @param algorithm The algorithm to use, such as "RSA/ECB/PKCS1Padding"
     * @return The encrypted data
     * @throws GeneralSecurityException
     */
    private static byte[] encryptByPublicKey(byte[] data, byte[] keybyte, String algorithm) throws GeneralSecurityException {
        final Cipher cipher = Cipher.getInstance(algorithm, PROVIDER_BOUNCY_CASTAL);

        final RSAPublicKey publicKey = (RSAPublicKey) byte2PublicKey(keybyte);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * Encrypts the data with the public key, using the algorithm "RSA/ECB/PKCS1Padding".
     *
     * @param data    The data to be encrypted
     * @param keybyte The public key used to encrypt the data
     * @return The encrypted data
     * @throws GeneralSecurityException
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] keybyte) throws GeneralSecurityException {
        return encryptByPublicKey(data, keybyte, CIPHER_ALGORITHM_RSA);
    }

}

package cn.caijiajia.loanmarket.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;


public class RSAUtils {

	 public static final String KEY_ALGORITHM = "RSA";
	
	 
	 
	 public static byte[] encrypt(byte[] content,PublicKey publicKey) throws Exception {
		 Cipher cipher= Cipher.getInstance("RSA");//java默认"RSA"="RSA/ECB/PKCS1Padding"
		 cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		 return cipher.doFinal(Base64Utils.encode(content).getBytes());  
		 
	 }
	 
	 
    /**
     * 私钥签名
     * <功能描述>
     * @param src
     * @param priKey
     * @return
     */
    public static byte[] generateSHA1withRSASigature(String src, String priKey)
    {
        try
        {
            Signature sigEng = Signature.getInstance("SHA1withRSA");
            byte[] pribyte = Base64Utils.decode(priKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);
            sigEng.initSign(privateKey);
            sigEng.update(src.getBytes("UTF-8"));
            byte[] signature = sigEng.sign();
            return signature;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    //将base64编码后的公钥字符串转成PublicKey实例  
    public static RSAPublicKey getPublicKey(String publicKey) throws Exception {
        byte[ ] keyBytes=Base64.getDecoder().decode(publicKey.getBytes());  
        X509EncodedKeySpec keySpec=new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory= KeyFactory.getInstance("RSA");
        return (RSAPublicKey)keyFactory.generatePublic(keySpec);
    }  
    

    public static byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData)
            throws Exception {
        if (publicKey == null) {
            throw new Exception("解密公钥为空, 请设置");
        }  
        Cipher cipher = null;
        try {  
            // 使用默认RSA  
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(cipherData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {
            throw new Exception("解密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }  
    }  
    
    public static boolean doCheck(String content, byte[] sign, RSAPublicKey pubKey)
            throws SignatureException {
        try {
        	
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes("utf-8"));
            return signature.verify((sign));
        } catch (Exception e) {
            throw new SignatureException("RSA验证签名[content = " + content
                    + "; charset = " + "; signature = " + sign + "]发生异常!", e);
        }
    }

    public static boolean checkSignature(Map<String, String> params, String publicKey) {
        String sign  = params.get("sign");
        if (null == sign || "".equals(sign)) {
            return false;
        }

        params.remove("sign");
        String sortParam = CommonUtil.getSortParams(params);
        return rsaCheck(sortParam, sign, publicKey, "UTF-8");
    }

    public static boolean rsaCheck(String content, String sign, String publicKey, String encode){
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = new org.apache.commons.codec.binary.Base64().decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(pubKey);
            signature.update( content.getBytes(encode) );
            return signature.verify( new org.apache.commons.codec.binary.Base64().decode(sign) );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
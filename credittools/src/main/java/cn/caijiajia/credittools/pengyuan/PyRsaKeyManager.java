package cn.caijiajia.credittools.pengyuan;

import cn.caijiajia.credittools.pengyuan.core.RsaKeyManager;
import cn.caijiajia.credittools.pengyuan.util.OpenApiEncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @Author:chendongdong
 * @Date:2018/5/9
 */
@Component
@Slf4j
public class PyRsaKeyManager implements RsaKeyManager {

    /**
     * 鹏元RSA公钥(加密AES随机密钥)
     */
    private String pyPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJc7ZOsBJlfb6/etCf2Md2sqddjtw/XuQMOXE2B5O4sBFCjka5i/nkBDRcibZoH1wHJIyXne+OH5D0pA6Bga1K91P3em2QPi+2lh0gMaz3JxMnO3etxj3by9KrezK915Ls1h6ahaQkXRCbga4UcSTtF88zounnqe1bXYazrrEtmQIDAQAB";

    /**
     * 自有RSA私钥
     */
    private String selfPrivateKey;

    public PyRsaKeyManager() {
        //生成自有RSA私钥
        Map<String,Object> rsaMap;
        try {
            rsaMap = OpenApiEncryptUtil.generateRSAKeyPairs();
            //提供给鹏元的自有RSA公钥
            String publicKey = (String) rsaMap.get("publicKey");
//            System.out.println("publicKey: " + publicKey);
            selfPrivateKey = (String) rsaMap.get("privateKey");
        } catch (NoSuchAlgorithmException e){
            log.error(e.getMessage());
        }
    }

    @Override
    public String getPyPublicKey() {
        return pyPublicKey;
    }

    @Override
    public String getSelfPrivateKey() {
        return selfPrivateKey;
    }

    public static void main(String[] args) {
        System.out.println(new PyRsaKeyManager().getPyPublicKey());
    }
}

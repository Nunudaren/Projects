package cn.caijiajia.credittools.service.pengyuan.core;

import cn.caijiajia.credittools.service.pengyuan.exception.OpenApiException;
import cn.caijiajia.credittools.service.pengyuan.util.DateUtil;
import cn.caijiajia.credittools.service.pengyuan.util.JsonUtil;
import cn.caijiajia.credittools.service.pengyuan.util.OpenApiEncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:chendongdong
 * @Date:2018/5/10
 */
@Component
public class OpenApiClient {
    private static final Logger log = LoggerFactory.getLogger(OpenApiClient.class);
    private RsaKeyManager rsaKeyManager;
    private OpenApiConfig config;

    public OpenApiClient() {
    }

    public OpenApiClient(RsaKeyManager rsaKeyManager, OpenApiConfig config) {
        this.rsaKeyManager = rsaKeyManager;
        this.config = config;
    }

    public String buildGetUri(Map<String, Object> bizContent) throws OpenApiException {
        HashMap paramMap = new HashMap();

        try {
            String randomKey = OpenApiEncryptUtil.getAESRandomKey();
            String encryptRandomKey = OpenApiEncryptUtil.encryptByRSA(randomKey, this.rsaKeyManager.getPyPublicKey());
            String dataJson = JsonUtil.write(bizContent);
            String encryptBizContent = OpenApiEncryptUtil.encryptByAES(dataJson, randomKey);
            String timestamp = DateUtil.currentTimeStr();
            String signData = this.buildRequestSignData(encryptBizContent, encryptRandomKey, timestamp);
            String sign = OpenApiEncryptUtil.signByPrivateKey(signData, this.rsaKeyManager.getSelfPrivateKey());
            paramMap.put("version", this.config.getVersion());
            paramMap.put("bizContent", URLEncoder.encode(encryptBizContent, "utf-8"));
            paramMap.put("randomKey", URLEncoder.encode(encryptRandomKey, "utf-8"));
            paramMap.put("appId", this.config.getAppId());
            paramMap.put("signType", "RSA");
            paramMap.put("sign", URLEncoder.encode(sign, "utf-8"));
            paramMap.put("timestamp", timestamp);
            paramMap.put("charset", "utf-8");
            paramMap.put("format", "json");
            return OpenApiEncryptUtil.createLinkString(paramMap);
        } catch (Exception var10) {
            log.error("buildGetUri error", var10);
            throw new OpenApiException("buildGetUri error");
        }
    }

    private String buildRequestSignData(String encryptBizContent, String encryptRandomKey, String timestamp) {
        Map<String, String> signMap = new HashMap();
        signMap.put("charset", "utf-8");
        signMap.put("format", "json");
        signMap.put("signType", "RSA");
        signMap.put("bizContent", encryptBizContent);
        signMap.put("randomKey", encryptRandomKey);
        signMap.put("timestamp", timestamp);
        return OpenApiEncryptUtil.createLinkString(signMap);
    }
}

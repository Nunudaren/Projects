package cn.caijiajia.credittools.service.pengyuan;

import cn.caijiajia.credittools.service.pengyuan.core.OpenApiClient;
import cn.caijiajia.credittools.service.pengyuan.core.OpenApiConfig;
import cn.caijiajia.credittools.service.pengyuan.exception.OpenApiException;
import cn.caijiajia.framework.httpclient.HttpClientTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author:chendongdong
 * @Date:2018/5/9
 */
@Service
@Slf4j
public class PyBuildReqUrlService {

    @Autowired
    private OpenApiClient openApiClient;

    @Autowired
    private HttpClientTemplate httpClient;

    @Value("${pengyuan.hostName}")
    private String hostUrl;

    private static final String VERSION = "2.0";
    private static final String APPID = "300301010";

    public String bulidReqUrl(Map<String, Object> requestParam , String jumpUrl) {
        try {
            PyRsaKeyManager pyRsaKeyManager = new PyRsaKeyManager();

            OpenApiConfig config = new OpenApiConfig();
            config.setAppId(APPID);
            config.setVersion(VERSION);
            config.setHostName(hostUrl);

            openApiClient = new OpenApiClient(pyRsaKeyManager, config);
            String reqUrl = doGetUrl(requestParam);
            jumpUrl = hostUrl + reqUrl;
        } catch (Exception e) {
            log.error("请求url构建失败：" + e.getMessage());
        }
        return jumpUrl;
    }

    public String doGetUrl(Map<String, Object> bizContent) throws OpenApiException {
        return openApiClient.buildGetUri(bizContent);
    }

}
package cn.caijiajia.credittools.delegator;


import cn.caijiajia.framework.httpclient.HttpClientTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouyangbo
 * @description
 * @Date：Created in 16:43 2018/5/10
 */
@Component
public class UserDelegator {

    private static final String URL_VERIFY_SESSION = "/verifySession";

    @Autowired
    private HttpClientTemplate httpClient;

    @Value("${url.user}")
    private String userUrl;

    public void verifySession(String uid, String sessionId) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("sessionId", sessionId);
        httpClient.doPost(userUrl + URL_VERIFY_SESSION, params);
    }
}

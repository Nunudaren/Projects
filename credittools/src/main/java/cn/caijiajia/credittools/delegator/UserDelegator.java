package cn.caijiajia.credittools.delegator;

import cn.caijiajia.credittools.bo.UserInfo;
import cn.caijiajia.credittools.common.constant.ErrorResponseConstants;
import cn.caijiajia.framework.exceptions.CjjClientException;
import cn.caijiajia.framework.httpclient.HttpClientTemplate;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author:chendongdong
 * @Date:2018/5/10
 */

@Component
public class UserDelegator {

    private static final String URL_USER_INFO = "/users/%s";

    @Autowired
    private HttpClientTemplate httpClient;
    @Autowired
    private String userLoanUrl;

    public UserInfo getUser(String uid) {
        UserInfo userInfo = JSONObject.parseObject(httpClient.doGet(userLoanUrl + String.format(URL_USER_INFO, uid)), UserInfo.class);
        if (userInfo == null) {
            throw new CjjClientException(ErrorResponseConstants.USER_NOT_EXISTS_CODE, ErrorResponseConstants.USER_NOT_EXISTS_MESSAGE);
        }
        return userInfo;
    }

}

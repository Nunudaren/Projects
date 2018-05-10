package cn.caijiajia.credittools.service;

import cn.caijiajia.card.common.resp.CardVo;
import cn.caijiajia.card.rpc.CardRpc;
import cn.caijiajia.credittools.bo.UnionJumpBo;
import cn.caijiajia.credittools.common.constant.ErrorResponseConstants;
import cn.caijiajia.credittools.constant.UnionLoginChannelEnum;
import cn.caijiajia.credittools.utils.CommonUtil;
import cn.caijiajia.credittools.utils.MD5Utils;
import cn.caijiajia.framework.exceptions.CjjClientException;
import cn.caijiajia.framework.httpclient.HttpClientTemplate;
import cn.caijiajia.user.common.resp.UserVo;
import cn.caijiajia.user.rpc.UserRpc;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.amazonaws.util.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zhouyangbo
 * @description
 * @Date：Created in 14:12 2018/5/9
 */
@Service
@Slf4j
public class Lottery9188UnionLoginService implements IProductsService {
    @Autowired
    private LoanProductService loanProductService;
    @Autowired
    private HttpClientTemplate httpClientTemplate;

    private static final String SIGN_TYPE = "MD5";
    private static final String _INPUT_CHARSET = "UTF-8";
    @Value("${lottery9188.partner}")
    private String partner;
    @Value("${lottery9188.url}")
    private String lottery9188Url;
    @Value("${lottery9188.MD5Key}")
    private String lottery9188MD5Key;

    @Override
    public UnionJumpBo unionLogin(String uid, String key) {
//        String jumpUrl = loanProductService.getUnionLoginUrl(key);

        Map<String, String> signMap = Maps.newHashMap();
        signMap.put("time_key", System.currentTimeMillis() + 3 * 60 * 60 * 1000 + "");
        signMap.put("user_id", uid);
        String signStr = MD5Utils.getFormDataParamMD5(signMap);
        String sign = MD5Utils.sign(signStr, lottery9188MD5Key, _INPUT_CHARSET);

        Map<String, String> reqParam = Maps.newHashMap();
        reqParam.put("sign", sign);
        reqParam.put("charset", _INPUT_CHARSET);
        reqParam.put("sign_type", SIGN_TYPE);
        reqParam.put("partner", partner);
        reqParam.put("paramData", JSON.toJSONString(signMap));

        String reqStr = MD5Utils.getFormDataParamMD5(reqParam);
        reqStr = reqStr + "&" + lottery9188MD5Key;
        String result = httpClientTemplate.doPost(lottery9188Url, reqStr);
        System.out.println(result);

        return null;
    }

    @Override
    public String getChannelName() {
        return UnionLoginChannelEnum.LOTTERY9188.toString();
    }
}

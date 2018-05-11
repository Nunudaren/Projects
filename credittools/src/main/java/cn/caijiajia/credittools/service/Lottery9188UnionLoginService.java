package cn.caijiajia.credittools.service;

import cn.caijiajia.credittools.bo.UnionJumpBo;
import cn.caijiajia.credittools.constant.UnionLoginChannelEnum;
import cn.caijiajia.credittools.utils.MD5Utils;
import cn.caijiajia.framework.httpclient.HttpClientTemplate;
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
    private LoanProductMgrService loanProductMgrService;

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
        String url;
        try {
            Map<String, String> signMap = Maps.newHashMap();
            signMap.put("time_key", System.currentTimeMillis() + 3 * 60 * 60 * 1000 + "");
            signMap.put("user_id", uid);
            signMap.put("partner", partner);
            signMap.put("_input_charset", _INPUT_CHARSET);

            String signStr = MD5Utils.getFormDataParamMD5(signMap);
            String sign = MD5Utils.sign(signStr, lottery9188MD5Key, _INPUT_CHARSET);

            StringBuilder reqStr = new StringBuilder();
            reqStr.append(signStr).append("&sign=").append(sign).append("&sign_type=").append(SIGN_TYPE);

            url = lottery9188Url + "?" + reqStr;
        }catch (Exception e) {
            log.error("9188彩票联合登录异常：", e);
            return UnionJumpBo.builder().jumpUrl(loanProductMgrService.getUnionLoginUrl(key)).build();
        }
        return UnionJumpBo.builder().jumpUrl(url).build();
    }

    @Override
    public String getChannelName() {
        return UnionLoginChannelEnum.LOTTERY9188.toString();
    }
}

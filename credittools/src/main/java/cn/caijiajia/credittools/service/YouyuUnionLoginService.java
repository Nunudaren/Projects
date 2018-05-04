package cn.caijiajia.credittools.service;

import cn.caijiajia.creditdata.common.req.operator.CityCodeReq;
import cn.caijiajia.credittools.common.constant.ErrorResponseConstants;
import cn.caijiajia.credittools.common.resp.YouyuUnionLoginResp;
import cn.caijiajia.credittools.configuration.Configs;
import cn.caijiajia.credittools.constant.UnionLoginChannelEnum;
import cn.caijiajia.credittools.delegator.CreditDataDelegate;
import cn.caijiajia.credittools.utils.MD5Utils;
import cn.caijiajia.framework.exceptions.CjjClientException;
import cn.caijiajia.framework.httpclient.HttpClientTemplate;
import cn.caijiajia.user.common.resp.UserVo;
import cn.caijiajia.user.rpc.UserRpc;
import cn.caijiajia.userloan.common.resp.DeviceEventDetailResp;
import cn.caijiajia.userloan.common.resp.DeviceInfoResp;
import cn.caijiajia.userloan.rpc.DeviceInfoRpc;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @Author:chendongdong
 * @Date:2018/4/24
 */
@Service
@Slf4j
public class YouyuUnionLoginService implements IProductsService {
    public static final String KEY = "b07c50861c163ba03c2079c2eaf112f1";
    public static final String COMFROM = "huanbei";

    @Value("${youyu.hostUrl}")
    private String youyuHostUrl;
    @Resource
    private Configs configs;
    @Autowired
    private UserRpc userRpc;
    @Autowired
    private DeviceInfoRpc deviceInfoRpc;
    @Autowired
    private CreditDataDelegate creditDataDelegate;
    @Autowired
    private HttpClientTemplate httpClientTemplate;
    @Autowired
    private LoanProductService loanProductService;

    @Override
    public String unionLogin(String uid, String key) {

        String jumpUrl = loanProductService.getUnionLoginUrl(key);
        String mobile = getMobile(uid);
        DeviceInfoResp deviceInfoResp;
        DeviceEventDetailResp deviceEventDetailResp;
        String cityCode = "";
        try {
            deviceInfoResp = deviceInfoRpc.getUserDeviceInfo(uid);
            deviceEventDetailResp = deviceInfoResp.getDeviceEventDetailResp();
            String result = "";
            if (StringUtils.isNotEmpty(deviceEventDetailResp.getLongitude()) && StringUtils.isNotEmpty(deviceEventDetailResp.getLatitude())) {
                result = creditDataDelegate.getAddressInfo(deviceEventDetailResp.getLongitude() + "," + deviceEventDetailResp.getLatitude());
            }
            String province = "";
            String city = "";
            if (StringUtils.isNotEmpty(result)) {
                String[] location = result.split("-");
                province = location[0];
                city = location[1];
            }
            CityCodeReq cityCodeReq = CityCodeReq.builder().province(province).city(city).build();
            cityCode = creditDataDelegate.getCityCode(cityCodeReq);
        } catch (Exception e) {
            log.warn("联合登陆所需请求参数获取失败：" + e);
            return jumpUrl;
        }
        if (StringUtils.isEmpty(cityCode) || StringUtils.isEmpty(deviceEventDetailResp.getLongitude())
                || StringUtils.isEmpty(deviceEventDetailResp.getLatitude()) || StringUtils.isEmpty(deviceEventDetailResp.getClientIp()) || StringUtils.isEmpty(mobile)) {
            return jumpUrl;
        }
        Map<String, String> param = bulidUnionLoginReqParam(deviceEventDetailResp, cityCode, mobile);
        YouyuUnionLoginResp youyuUnionLoginResp;
        try {
            log.info("有鱼联合登陆请求参数：" + param.toString());
            String resultParam = httpClientTemplate.doPost(youyuHostUrl, param);
            youyuUnionLoginResp = JSON.parseObject(resultParam, YouyuUnionLoginResp.class);
            if ("1".equals(youyuUnionLoginResp.getCode())) {
                jumpUrl = jumpUrl + "&" + "appId=" + youyuUnionLoginResp.getAppId() + "&" + "accessToken=" + youyuUnionLoginResp.getAccessToken();
            }
        } catch (Exception e) {
            log.warn("youyu union login invoke error: ", e);
        }
        return jumpUrl;
    }

    private String getMobile(String uid) {
        UserVo userInfo = userRpc.getUserInfo(uid);
        if (userInfo == null) {
            log.error("没有找到该用户,uid:{}", uid);
            throw new CjjClientException(ErrorResponseConstants.USER_NOT_EXISTS_CODE, ErrorResponseConstants.USER_NOT_EXISTS_MESSAGE);
        }
        return userInfo.getMobile();
    }

    private Map<String, String> bulidUnionLoginReqParam(DeviceEventDetailResp deviceEventDetailResp, String cityCode, String mobile) {
        String timeStamp = String.valueOf(new Date().getTime() / 1000);//当前时间戳（10）位
        StringBuilder toEncryptStr = new StringBuilder();
        toEncryptStr.append("cphone").append("=").append(mobile).append("&");
        toEncryptStr.append("clng").append("=").append(deviceEventDetailResp.getLongitude()).append("&");
        toEncryptStr.append("clat").append("=").append(deviceEventDetailResp.getLatitude()).append("&");
        toEncryptStr.append("adcode").append("=").append(cityCode).append("&");
        toEncryptStr.append("ip").append("=").append(deviceEventDetailResp.getClientIp()).append("&");
        toEncryptStr.append("imei").append("=").append(deviceEventDetailResp.getImei()).append("&");
        toEncryptStr.append("timeStamp").append("=").append(timeStamp).append("&");
        toEncryptStr.append("comeFrom").append("=").append(COMFROM).append("&");
        toEncryptStr.append("key").append("=").append(KEY);

        String signStr = MD5Utils.getMD5String(toEncryptStr.toString());

        Map<String, String> param = Maps.newHashMap();
        param.put("cphone", mobile);
        param.put("clng", deviceEventDetailResp.getLongitude());
        param.put("clat", deviceEventDetailResp.getLatitude());
        param.put("adcode", cityCode);
        param.put("ip", deviceEventDetailResp.getClientIp());
        param.put("imei", deviceEventDetailResp.getImei());
        param.put("timeStamp", timeStamp);
        param.put("comeFrom", COMFROM);
        param.put("key", KEY);
        param.put("sign", signStr);
        return param;
    }

    @Override
    public String getChannelName() {
        return UnionLoginChannelEnum.YOUYU.toString();
    }
}

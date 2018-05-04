package cn.caijiajia.credittools.delegator;

import cn.caijiajia.creditdata.common.constant.LoanmgrSearchEnum;
import cn.caijiajia.creditdata.common.req.operator.CityCodeReq;
import cn.caijiajia.framework.httpclient.HttpClientTemplate;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by huchaofeng on 2017/10/12.
 */
@Service
public class CreditDataDelegate {

    @Autowired
    private String creditDataUrl;

    private static final String URI_OUTER_SUPPLIER_DATA = "/outerSupplier/data";
    private static final String GET_ADDRESS_INFO = "/getAddressInfo";
    private static final String GET_CITY_CODE = "/getCityCode/";

    @Autowired
    private HttpClientTemplate httpClient;

    private String getUrl(String url) {
        return creditDataUrl + url;
    }

    public <T> List<T> fetchOuterSupplierData(Class<T> domainClazz, String uid, String lawId) {
        Map<String, Object> queryItem = Maps.newHashMap();
        queryItem.put("searchType", LoanmgrSearchEnum.ALL.toString());
        queryItem.put("uid", uid);
        queryItem.put("lawId", lawId);
        queryItem.put("clazzName", domainClazz.getSimpleName());
        String url = getUrl(URI_OUTER_SUPPLIER_DATA);
        String result = httpClient.doPost(url, queryItem);
        return JSONArray.parseArray(result, domainClazz);
    }


    public String getAddressInfo(String lbs) {
        Map<String, String> map = Maps.newHashMap();
        map.put("lbs", lbs);
        return httpClient.doGet(getUrl(GET_ADDRESS_INFO), map);
    }


    public String getCityCode(CityCodeReq cityCodeReq) {
        String result = httpClient.doPost(getUrl(GET_CITY_CODE), cityCodeReq);
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        return result;
    }


}

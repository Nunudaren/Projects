package cn.openapi.demo;

import cn.RandomGeneratePhoneNumberUtils;
import cn.openapi.KeyManager;
import cn.openapi.OpenApiClient;
import cn.openapi.ShuheResponse;
import cn.openapi.ZhiTianNameImportReq;
import cn.openapi.config.DefaultHttpClientConfig;
import cn.openapi.config.MerchantConfig;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author winter
 * @date 2019-01-28
 */
public class OpenApiClientUtils {
    private static OpenApiClient openApiClient = null;

    private static final boolean SIGN = false;

    private static final String MERCHANT_CODE = "ZHITIAN";

    private static final String SHUHE_HOST = "http://localhost:8080";
//    private static final String SHUHE_HOST = "http://openapi.dev.lattebank.com";
//    private static final String SHUHE_HOST = "http://openapi.sit.lattebank.com";

//    @BeforeClass
    public static void beforeClass() {
        DefaultHttpClientConfig httpClientConfig = new DefaultHttpClientConfig();
        MerchantConfig merchantConfig = new MerchantConfig(SIGN, MERCHANT_CODE, SHUHE_HOST);
        KeyManager keyManager = new KeyManagerConfig();

        openApiClient = new OpenApiClient(httpClientConfig, merchantConfig, keyManager);
    }

    @AfterClass
    public static void afterClass() throws IOException {
        openApiClient.close();
        System.out.println("Client closed");
    }

    @Test
    public void doGet() throws Exception {
        Map<String, String> parameters = Maps.newHashMap();
        parameters.put("idfa", "demo");
        ShuheResponse shuheResponse = openApiClient.doGet("/user/check_idfa", parameters, true);
        System.out.println(JSON.toJSONString(shuheResponse));
    }

//    @Test
//    public void doPost() throws Exception {
//        Map<String, Object> requestMap = Maps.newHashMap();
//        requestMap.put("url", "http://www.baidu.com");
//        requestMap.put("recycleTime", 120);
//        ShuheResponse shuheResponse = openApiClient.doPost("/urlconverter/tinyurls", requestMap, true);
//        System.out.println(JSON.toJSONString(shuheResponse));
//    }

    @Test
    public void zhiTianUtils() throws Exception {
        DefaultHttpClientConfig httpClientConfig = new DefaultHttpClientConfig();
        MerchantConfig merchantConfig = new MerchantConfig(SIGN, MERCHANT_CODE, SHUHE_HOST);
        KeyManager keyManager = new KeyManagerConfig();

        openApiClient = new OpenApiClient(httpClientConfig, merchantConfig, keyManager);

        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("channel", "zhitian");
        requestMap.put("dataLength", 1000);
        requestMap.put("dataList", mockData());
        long start = System.currentTimeMillis();
        ShuheResponse shuheResponse = openApiClient.doPost("/callbox/zhitian/pushNameList", requestMap, false);
        long end = System.currentTimeMillis();
        System.out.println("耗时：");
        System.out.println(end - start);
        System.out.println(JSON.toJSONString(shuheResponse));
    }

    private List mockData() {
        List<ZhiTianNameImportReq.DataInfo> dataInfoList = Lists.newArrayList();
//        dataInfoList.add(ZhiTianNameImportReq.DataInfo.builder()
//                .baseInfo(ZhiTianNameImportReq.DataInfo.BaseInfo.builder()
//                        .custSrc("12653")
//                        .name("qingtian")
//                        .teleNum(RandomGeneratePhoneNumberUtils.getTel())
//                        .gender(1)
//                        .birthDate("19980915")
//                        .education(2)
//                        .province("河南")
//                        .city("郑州")
//                        .hasCreditCard(1)
//                        .build())
//                .additionalInfo(ZhiTianNameImportReq.DataInfo.AdditionalInfo.builder()
//                        .credit(1000)
//                        .hasCarLoan(1)
//                        .hasLoaned(1)
//                        .profession(3)
//                        .income(10000L)
//                        .payoffType(1)
//                        .build())
//                .build());
//
//        dataInfoList.add(ZhiTianNameImportReq.DataInfo.builder()
//                .baseInfo(ZhiTianNameImportReq.DataInfo.BaseInfo.builder()
//                        .custSrc("12653")
//                        .name("liuyun")
//                        .teleNum(RandomGeneratePhoneNumberUtils.getTel())
//                        .gender(1)
//                        .birthDate("19970915")
//                        .province("安徽")
//                        .city("合肥")
//                        .hasCreditCard(1)
//                        .build())
//                .additionalInfo(ZhiTianNameImportReq.DataInfo.AdditionalInfo.builder()
//                        .credit(2000)
//                        .hasCarLoan(1)
//                        .hasLoaned(1)
//                        .profession(3)
//                        .income(12000L)
//                        .payoffType(1)
//                        .build())
//                .build());

        for (int i = 0; i < 1000; i++) {
            dataInfoList.add(ZhiTianNameImportReq.DataInfo.builder()
                    .baseInfo(ZhiTianNameImportReq.DataInfo.BaseInfo.builder()
                            .custSrc("12653")
                            .name("liuyun")
                            .teleNum(RandomGeneratePhoneNumberUtils.getTel())
                            .gender(new Random().nextBoolean() ? 1 : 0)
                            .birthDate("19970915")
                            .province("安徽")
                            .city("合肥")
                            .hasCreditCard(1)
                            .build())
                    .additionalInfo(ZhiTianNameImportReq.DataInfo.AdditionalInfo.builder()
                            .credit(2000)
                            .hasCarLoan(1)
                            .hasLoaned(1)
                            .profession(3)
                            .income(12000L)
                            .payoffType(1)
                            .build())
                    .build());
        }

        return dataInfoList;
    }

}

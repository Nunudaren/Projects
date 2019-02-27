package cn.net.io.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @Author:chendong
 * @Date:2018/9/5
 */
public class HttpDemo {
    public static void main(String[] args) throws Exception {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault(); //1、创建实例

        HttpGet httpGet = new HttpGet("http://www.baidu.com"); //2、创建实例

        HttpHost proxy = new HttpHost("115.239.210.27");
//
        RequestConfig config = setRequsetConfig1(proxy);
        httpGet.setConfig(config);

        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet); //3、执行
        HttpEntity httpEntity = closeableHttpResponse.getEntity(); //4、获取实体

        //System.out.println(httpEntity.toString());
        System.out.println(EntityUtils.toString(httpEntity, "utf-8")); //获取网页内容

        closeableHttpResponse.close();
        closeableHttpClient.close();
    }

    /**
     *  read timed out !
     * @param proxy
     * @return
     */
    private static RequestConfig setRequsetConfig1(HttpHost proxy) {
        RequestConfig config = RequestConfig.custom().setProxy(proxy)
                .setConnectTimeout(1000) // 设置连接超时时间 3秒钟
                .setSocketTimeout(6) // 设置读取超时时间0.01秒钟
                .build();
        return config;
    }

    /**
     * connect timed out !
     * @param proxy
     * @return
     */
    private static RequestConfig setRequsetConfig2(HttpHost proxy) {
        RequestConfig config = RequestConfig.custom().setProxy(proxy)
                .setConnectTimeout(10) // 设置连接超时时间0.01秒钟
                .setSocketTimeout(1000) // 设置读取超时时间3秒钟
                .build();
        return config;
    }
}

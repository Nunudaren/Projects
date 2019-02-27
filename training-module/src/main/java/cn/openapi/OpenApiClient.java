/**
 * Shuhe confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.openapi;

import cn.openapi.config.HttpClientConfig;
import cn.openapi.config.MerchantConfig;
import cn.openapi.util.Constant;
import cn.openapi.util.EncryptUtil;
import cn.openapi.util.SignUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by gogogo on 17/11/2.
 */
public class OpenApiClient {
    private static Logger logger = LoggerFactory.getLogger(OpenApiClient.class);

    private static final String CONTENT_TYPE = "Content-Type";

    private static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";

    private static final ContentType DEFAULT_CONTENT_TYPE_APPLICATION_FORM_ENCODED = ContentType.create(APPLICATION_X_WWW_FORM_URLENCODED, Constant.DEFAULT_CHARSET);

    private static final ContentType DEFAULT_CONTENT_TYPE_APPLICATION_JSON = ContentType.APPLICATION_JSON;

    private ThreadLocal<Integer> connectTimeoutThreadLocal = new ThreadLocal<Integer>();

    private ThreadLocal<Integer> socketTimeoutThreadLocal = new ThreadLocal<Integer>();

    private CloseableHttpClient httpClient;

    private PoolingHttpClientConnectionManager connectionManager;

    private IdleConnectionMonitorThread monitorThread;

    private HttpClientConfig httpClientConfig;

    private MerchantConfig merchantConfig;

    private KeyManager keyManager;

    /**
     * @param httpClientConfig
     * @param merchantConfig
     */
    public OpenApiClient(HttpClientConfig httpClientConfig, MerchantConfig merchantConfig, KeyManager keyManager) {
        this.httpClientConfig = httpClientConfig;
        this.merchantConfig = merchantConfig;
        this.keyManager = keyManager;

        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(httpClientConfig.getMaxTotal());
        connectionManager.setDefaultMaxPerRoute(httpClientConfig.getDefaultMaxPerRoute());

        httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();

        monitorThread = new IdleConnectionMonitorThread();
        monitorThread.start();
    }

    /**
     * close OpenApiClient, clear resources
     *
     * @throws IOException
     */
    public void close() throws IOException {
        httpClient.close();
        connectionManager.close();
        monitorThread.setShutdown(true);
    }

    /**
     * HTTP GET, application/x-www-form-urlencoded, UTF-8
     *
     * @param uri request uri with no parameters
     * @param encryptFlag true - request encrypted by merchant; false - not encrypted
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws GeneralSecurityException
     */
    public ShuheResponse doGet(String uri, boolean encryptFlag)
            throws IOException, URISyntaxException, GeneralSecurityException {
        return doGet(uri, null, encryptFlag);
    }

    /**
     * HTTP GET, application/x-www-form-urlencoded, UTF-8
     *
     * @param uri         request uri with no parameters
     * @param parameters
     * @param encryptFlag true - request encrypted by merchant; false - not encrypted
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws GeneralSecurityException
     */
    public ShuheResponse doGet(String uri, Map<String, String> parameters, boolean encryptFlag)
            throws IOException, URISyntaxException, GeneralSecurityException {
        parameters = addStatisticParameters(parameters);
        if (merchantConfig.getSign()) {
            parameters = sign(uri, parameters, null);
        }
        if (encryptFlag) {
            Object[] encrypted = encrypt(parameters, null);
            parameters = (Map<String, String>) encrypted[0];
        }
        HttpGet httpGet = new HttpGet(buildUri(uri, parameters));
        return execute(DEFAULT_CONTENT_TYPE_APPLICATION_FORM_ENCODED, httpGet);
    }

    /**
     * HTTP POST, application/json, UTF-8
     *
     * @param uri
     * @param requestBodyObject the object will be converted to json string
     * @param encryptFlag true - request encrypted by merchant; false - not encrypted
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws GeneralSecurityException
     */
    public ShuheResponse doPost(String uri, Object requestBodyObject, boolean encryptFlag)
            throws IOException, URISyntaxException, GeneralSecurityException {
        return doPost(uri, JSON.toJSONString(requestBodyObject), encryptFlag);
    }

    /**
     * HTTP POST, application/json, UTF-8
     *
     * @param uri
     * @param requestBody json string
     * @param encryptFlag true - request encrypted by merchant; false - not encrypted
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws GeneralSecurityException
     */
    public ShuheResponse doPost(String uri, String requestBody, boolean encryptFlag)
            throws IOException, URISyntaxException, GeneralSecurityException {
        Map<String, String> parameters = Maps.newHashMap();
        parameters = addStatisticParameters(parameters);
        if (merchantConfig.getSign()) {
            parameters = sign(uri, parameters, requestBody);
        }
        if (encryptFlag) {
            Object[] encrypted = encrypt(parameters, requestBody);
            parameters = (Map<String, String>) encrypted[0];
            requestBody = (String) encrypted[1];
        }
        HttpPost httpPost = new HttpPost(buildUri(uri, parameters));
        httpPost.setEntity(new StringEntity(requestBody, Constant.DEFAULT_CHARSET));
        return execute(DEFAULT_CONTENT_TYPE_APPLICATION_JSON, httpPost);
    }


    /**
     * HTTP PUT, application/json, UTF-8
     *
     * @param uri
     * @param requestBodyObject the object will be converted to json string
     * @param encryptFlag true - request encrypted by merchant; false - not encrypted
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws GeneralSecurityException
     */
    public ShuheResponse doPut(String uri, Object requestBodyObject, boolean encryptFlag)
            throws IOException, URISyntaxException, GeneralSecurityException {
        return doPut(uri, JSON.toJSONString(requestBodyObject), encryptFlag);
    }

    /**
     * HTTP PUT, application/json, UTF-8
     *
     * @param uri
     * @param requestBody json string
     * @param encryptFlag true - request encrypted by merchant; false - not encrypted
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws GeneralSecurityException
     */
    public ShuheResponse doPut(String uri, String requestBody, boolean encryptFlag)
            throws IOException, URISyntaxException, GeneralSecurityException {
        Map<String, String> parameters = Maps.newHashMap();
        parameters = addStatisticParameters(parameters);
        if (merchantConfig.getSign()) {
            parameters = sign(uri, parameters, requestBody);
        }
        if (encryptFlag) {
            Object[] encrypted = encrypt(parameters, requestBody);
            parameters = (Map<String, String>) encrypted[0];
            requestBody = (String) encrypted[1];
        }
        HttpPut httpPut = new HttpPut(buildUri(uri, parameters));
        httpPut.setEntity(new StringEntity(requestBody, Constant.DEFAULT_CHARSET));
        return execute(DEFAULT_CONTENT_TYPE_APPLICATION_JSON, httpPut);
    }

    /**
     * HTTP DELETE, application/x-www-form-urlencoded, UTF-8
     *
     * @param uri request uri with no parameters
     * @param encryptFlag true - request encrypted by merchant; false - not encrypted
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws GeneralSecurityException
     */
    public ShuheResponse doDelete(String uri, boolean encryptFlag)
            throws IOException, URISyntaxException, GeneralSecurityException {
        return doDelete(uri, null, encryptFlag);
    }

    /**
     * HTTP DELETE, application/x-www-form-urlencoded, UTF-8
     *
     * @param uri         request uri with no parameters
     * @param parameters
     * @param encryptFlag true - request encrypted by merchant; false - not encrypted
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws GeneralSecurityException
     */
    public ShuheResponse doDelete(String uri, Map<String, String> parameters, boolean encryptFlag)
            throws IOException, URISyntaxException, GeneralSecurityException {
        parameters = addStatisticParameters(parameters);
        if (merchantConfig.getSign()) {
            parameters = sign(uri, parameters, null);
        }
        if (encryptFlag) {
            Object[] encrypted = encrypt(parameters, null);
            parameters = (Map<String, String>) encrypted[0];
        }
        HttpDelete httpDelete = new HttpDelete(buildUri(uri, parameters));
        return execute(DEFAULT_CONTENT_TYPE_APPLICATION_FORM_ENCODED, httpDelete);
    }

    /**
     * Use this method before get/post/put/delete methods.
     * The timeout config will take effect on the next http invocation.
     *
     * @param connectTimeout
     * @param socketTimeout
     */
    public void setTimeoutOnce(int connectTimeout, int socketTimeout) {
        connectTimeoutThreadLocal.set(connectTimeout);
        socketTimeoutThreadLocal.set(socketTimeout);
    }

    private Map<String, String> addStatisticParameters(Map<String, String> parameters) {
        if (parameters == null) {
            parameters = Maps.newHashMap();
        }
        parameters.put("r_i", generateRi());
        parameters.put("r_c", merchantConfig.getMerchantCode());
        return parameters;
    }

    private static String generateRi() {
        return UUID.randomUUID().toString().concat(".O");
    }

    private URI buildUri(String uri, Map<String, String> parameters) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(merchantConfig.getShuheHost() + uri);

        if (parameters != null && parameters.size() > 0) {
            for (Map.Entry<String, String> parameter : parameters.entrySet()) {
                uriBuilder.addParameter(parameter.getKey(), parameter.getValue());
            }
        }

        return uriBuilder.build();
    }

    private Map<String, String> sign(String uri, Map<String, String> parameters, String requestBody) throws GeneralSecurityException {
        return SignUtil.sign(uri, parameters, requestBody,
                keyManager.getMerchantKeyVersion(), keyManager.getMerchantPrivateKey());
    }

    private Object[] encrypt(Map<String, String> parameters, String requestBody) throws GeneralSecurityException {
        return EncryptUtil.encrypt(parameters, requestBody, keyManager.getShuheEncryptKeyVersion(), keyManager.getShuheEncryptKey(),
                merchantConfig.getMerchantCode());
    }

    private ShuheResponse execute(ContentType contentType, HttpRequestBase httpRequest) throws IOException {
        CloseableHttpResponse httpResponse = null;
        int curConnectTimeout = httpClientConfig.getConnectTimeout();
        int curSocketTimeout = httpClientConfig.getSocketTimeout();

        try {
            // set content type
            httpRequest.setHeader(CONTENT_TYPE, contentType.toString());

            // set timeout
            Integer threadConnectTimeout = connectTimeoutThreadLocal.get();
            if (threadConnectTimeout != null) {
                curConnectTimeout = threadConnectTimeout;
            }
            Integer threadSocketTimeout = socketTimeoutThreadLocal.get();
            if (threadSocketTimeout != null) {
                curSocketTimeout = threadSocketTimeout;
            }
            RequestConfig.Builder requestConfigBuilder = RequestConfig.custom().setConnectTimeout(curConnectTimeout)
                    .setSocketTimeout(curSocketTimeout).setConnectionRequestTimeout(httpClientConfig.getConnectionRequestTimeout());
            httpRequest.setConfig(requestConfigBuilder.build());

            // http invocation
            httpResponse = httpClient.execute(httpRequest);

            // convert response
            return getResponse(httpResponse);
        } finally {
            connectTimeoutThreadLocal.set(null);
            socketTimeoutThreadLocal.set(null);
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    logger.error("Close httpResponse error.", e);
                }
            }

            httpRequest.releaseConnection();
        }
    }

    private ShuheResponse getResponse(CloseableHttpResponse httpResponse) throws IOException {
        StatusLine statusLine = httpResponse.getStatusLine();

        String entityString = null;
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            entityString = EntityUtils.toString(entity);
        }

        return new ShuheResponse(statusLine.getStatusCode(), entityString);
    }

    private class IdleConnectionMonitorThread extends Thread {

        private boolean shutdown = false;

        @Override
        public void run() {
            while (!shutdown) {
                try {
                    synchronized (this) {
                        wait(5000);
                        // Close expired connections
                        connectionManager.closeExpiredConnections();
                        // Optionally, close connections
                        // that have been idle longer than 30 sec
                        connectionManager.closeIdleConnections(30, TimeUnit.SECONDS);
                    }
                } catch (InterruptedException ex) {
                    // terminate
                } catch (Exception e) {
                    logger.error("something wrong.", e);
                }
            }
        }

        public void setShutdown(boolean shutdown) {
            this.shutdown = shutdown;
        }

    }
}

package com.sungcor.exam.utils;

import com.sungcor.exam.utils.exception.ProcessFailException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HttpRestUtil {

    private static Logger log = LoggerFactory.getLogger(HttpRestUtil.class);
    public static String get(String url) throws Exception {
        return get(url, createHeader());
    }

    public static String get(String url, Map<String, String> headers) throws Exception {
        return get(url, null, headers);
    }

    public static String get(String url, String cookie, Map<String, String> headers)
            throws Exception {

        log.warn("GET方法请求地址：" + url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if (url.startsWith("https")) {
            httpClient = createSSLInsecureClient();
        }
        String result = null;
        HttpGet request = new HttpGet(url);

        if (headers != null) {
            for (Entry<String, String> entry : headers.entrySet()) {
                request.setHeader(entry.getKey(), entry.getValue());
            }
        }

        if (cookie != null) {
            request.setHeader("Cookie", cookie);
        }
        if (log.isDebugEnabled()) {
            //log.debug("executing request to " + url);
        }
        HttpResponse httpResponse = httpClient.execute(request);
        HttpEntity entity = httpResponse.getEntity();

        if (entity != null) {
            result = EntityUtils.toString(entity, "utf-8");
        }


        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (log.isDebugEnabled()) {
            //log.debug("executing request result :{}", result);
        }
        if (200 != statusCode) {
            log.warn("请求返回CODE：" + statusCode + "返回数据：" + result);
            throw new ProcessFailException(result);
        }

        return result;
    }

    public static String post(String url, Map<String, String> headers, String jsonString)
            throws Exception {
        return post(url, null, headers, jsonString);
    }

    public static String post(String url, String jsonString)
            throws Exception {
        return post(url, createHeader(), jsonString);
    }

    private static Map<String, String> createHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json;charset=utf-8");
        headers.put("Content-Type", "application/json;charset=utf-8");
        return headers;
    }

    public static String post(String url, String cookie, Map<String, String> headers, String jsonString)
            throws Exception {

        log.warn("POST方法请求地址：" + url);
        log.warn("POST方法传入参数：" + jsonString);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = null;

        if (url.startsWith("https")) {
            httpClient = createSSLInsecureClient();
        }

        HttpPost postRequest = new HttpPost(url);
        if (headers != null) {
            for (Entry<String, String> entry : headers.entrySet()) {
                postRequest.setHeader(entry.getKey(), entry.getValue());
            }
        }
        if (cookie != null) {
            postRequest.setHeader("Cookie", "");
        }
        if (!StringUtils.isBlank(jsonString)) {
            StringEntity entity = new StringEntity(jsonString, "utf-8");
            postRequest.setEntity(entity);
        }

        log.debug("executing request to " + url);

        HttpResponse httpResponse = httpClient.execute(postRequest);
        HttpEntity entity = httpResponse.getEntity();

        //if (entity != null) {
            //result = EntityUtils.toString(entity);
        //}
        if (entity != null) {
            InputStream instreams = entity.getContent();
            String str = convertStreamToString(instreams);
            //httpgets.abort();
            return str;
        }

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (200 != statusCode) {
            log.warn("请求返回CODE：" + statusCode + "返回数据：" + result);
            throw new ProcessFailException(result);
        }

        return result;
    }
    public static String convertStreamToString(InputStream is) {
        StringBuilder sb1 = new StringBuilder();
        byte[] bytes = new byte[4096];
        int size = 0;
        try {
            while ((size = is.read(bytes)) > 0) {
                String str = new String(bytes, 0, size, "UTF-8");
                sb1.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb1.toString();
    }

    private static CloseableHttpClient createSSLInsecureClient() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> {
                return true; //信任所有
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (Exception e) {
            log.warn("CertificateException", e);
        }
        return HttpClients.createDefault();
    }
}

package com.uptang.cloud.score.httpclient;

import com.uptang.cloud.score.util.ApplicationContextHolder;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-07-28 12:58
 * @mailto: webb.lee.cn@gmail.com
 * @Summary : FIXME
 */
class HttpClientOps {

    /**
     * 无参get请求
     *
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doGet(String url, Header[] headers) throws ClientProtocolException, IOException {
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeaders(headers);

        //设置请求参数
        final RequestConfig requestConfig = ApplicationContextHolder.getBean(RequestConfig.class);
        httpGet.setConfig(requestConfig);
        final CloseableHttpClient httpClient = ApplicationContextHolder.getBean(CloseableHttpClient.class);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }
        return null;
    }

    /**
     * 有参get请求
     *
     * @param url
     * @param params
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doGet(String url, Map<String, String> params, Header[] headers)
            throws URISyntaxException, ClientProtocolException, IOException {
        URIBuilder uriBuilder = new URIBuilder(url);

        if (params != null) {
            for (String key : params.keySet()) {
                uriBuilder.setParameter(key, params.get(key));
            }
        }
        return doGet(uriBuilder.build().toString(), headers);
    }

    /**
     * 有参post请求
     *
     * @param url
     * @param params
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static HttpResponseEvent doPost(String url, Map<String, String> params, Header[] headers)
            throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(url);

        if (params != null) {
            List<NameValuePair> parameters = new ArrayList<>(0);
            for (String key : params.keySet()) {
                parameters.add(new BasicNameValuePair(key, params.get(key)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(parameters));
        }

        return handleResponse(httpPost, headers);
    }

    /**
     * POst xml
     *
     * @param url
     * @param xml
     * @return
     * @throws IOException
     */
    public static HttpResponseEvent doPostXml(String url, String xml, Header[] headers)
            throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "text/html;charset=UTF-8");

        //解决中文乱码问题
        StringEntity stringEntity = new StringEntity(xml, StandardCharsets.UTF_8);
        stringEntity.setContentEncoding(StandardCharsets.UTF_8.name());
        httpPost.setEntity(stringEntity);
        return handleResponse(httpPost, headers);
    }

    /**
     * JSON
     *
     * @param url
     * @param json
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static HttpResponseEvent doPost(String url, String json, Header[] headers)
            throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(url);
        if (!StringUtils.isEmpty(json)) {
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
        }
        return handleResponse(httpPost, headers);
    }

    /**
     * 无参post请求
     *
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static HttpResponseEvent doPost(String url, Header[] headers)
            throws ClientProtocolException, IOException {
        return doPost(url, "", headers);
    }

    /**
     * 处理响应报文
     *
     * @param httpPost
     * @return
     * @throws IOException
     */
    private static HttpResponseEvent handleResponse(HttpPost httpPost, Header[] headers) throws IOException {
        final CloseableHttpClient httpClient = ApplicationContextHolder.getBean(CloseableHttpClient.class);
        RequestConfig requestConfig = ApplicationContextHolder.getBean(RequestConfig.class);
        httpPost.setConfig(requestConfig);
        httpPost.setHeaders(headers);
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpResponseEvent httpResponse = new HttpResponseEvent();
            httpResponse.setCode(response.getStatusLine().getStatusCode());
            httpResponse.setMessage(response.getStatusLine().getReasonPhrase());
            httpResponse.setPayload(EntityUtils.toString(response.getEntity(), "UTF-8"));
            return httpResponse;
        }
    }
}
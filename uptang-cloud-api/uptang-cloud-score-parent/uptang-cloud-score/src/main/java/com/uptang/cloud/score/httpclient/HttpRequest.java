package com.uptang.cloud.score.httpclient;

import org.apache.http.Header;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @Author : Lee
 * @CreateTime : 2018/11/25 8:46 PM
 * @Mailto: webb.lee.cn@gmail.com / lmyboblee@gmail.com
 * @Summary : FIXME
 */
public interface HttpRequest {

    /**
     * @author : Lee.m.yin
     * @createtime : 2019-07-28 13:07
     * @mailto: webb.lee.cn@gmail.com
     * @Summary : HttpClient
     */
    interface HttpClient {
        static String get(String url, Header[] headers) throws IOException {
            return HttpClientOps.doGet(url, headers);
        }

        static String get(String url, Map<String, String> params, Header[] headers) throws URISyntaxException, IOException {
            return HttpClientOps.doGet(url, params, headers);
        }

        static HttpResponseEvent postForm(String url, Map<String, String> params, Header[] headers) throws IOException {
            return HttpClientOps.doPost(url, params, headers);
        }

        static HttpResponseEvent postJson(String url, String json, Header[] headers) throws IOException {
            return HttpClientOps.doPost(url, json, headers);
        }

        static HttpResponseEvent post(String url, Header[] headers) throws IOException {
            return HttpClientOps.doPost(url, headers);
        }

        static HttpResponseEvent postXml(String url, String xml, Header[] headers) throws IOException {
            return HttpClientOps.doPostXml(url, xml, headers);
        }
    }
}
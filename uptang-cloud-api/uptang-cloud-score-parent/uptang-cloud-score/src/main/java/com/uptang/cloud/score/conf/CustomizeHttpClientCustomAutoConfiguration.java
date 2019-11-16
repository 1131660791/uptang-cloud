package com.uptang.cloud.score.conf;

import com.uptang.cloud.score.exception.HttpClientException;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * @Author : Lee
 * @CreateTime : 2017年9月28日 下午6:49:52
 * @Mailto: webb.lee.cn@gmail.com / lmyboblee@gmail.com
 * @Summary : FIXME
 */
@Configuration
@ConditionalOnClass(PoolingHttpClientConnectionManager.class)
@ConditionalOnMissingBean(PoolingHttpClientConnectionManager.class)
public class CustomizeHttpClientCustomAutoConfiguration {

    @Bean
    public PoolingHttpClientConnectionManager getHttpClientConnectionManager() {
        LayeredConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            throw new HttpClientException(e);
        }
        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("https", sslsf)
                        .register("http", new PlainConnectionSocketFactory())
                        .build();

        PoolingHttpClientConnectionManager httpClientConnectionManager
                = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        httpClientConnectionManager.setMaxTotal(50);
        httpClientConnectionManager.setDefaultMaxPerRoute(15);
        httpClientConnectionManager.setValidateAfterInactivity(1000);
        return httpClientConnectionManager;
    }


    @Bean
    public HttpClientBuilder httpClientBuilder() {
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig())
                .setRedirectStrategy(new DefaultRedirectStrategy())
                .setConnectionManager(getHttpClientConnectionManager());
    }


    /**
     * 后加的 setKeepAliveStrategy 没测试
     *
     * @return
     */
    @Bean
    public CloseableHttpClient httpClient() {
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig())
                .setRedirectStrategy(new DefaultRedirectStrategy())
                .setKeepAliveStrategy(keepAliveStrategy())
                .setRetryHandler(new DefaultHttpRequestRetryHandler(3, false))
                .build();
    }

    @Bean
    public RequestConfig requestConfig() {
        return RequestConfig.custom()
                .setSocketTimeout(10000)
                .setConnectTimeout(1000)
                .setConnectionRequestTimeout(500)
                .build();
    }

    @Bean
    public ConnectionKeepAliveStrategy keepAliveStrategy() {
        return (response, context) -> {
            HeaderIterator headerIterator = response.headerIterator(HTTP.CONN_KEEP_ALIVE);
            HeaderElementIterator it = new BasicHeaderElementIterator(headerIterator);
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    return Long.parseLong(value) * 1000;
                }
            }
            return 5 * 1000;
        };
    }

    @Bean
    public IdleConnectionEvictor idleConnectionEvictor() {
        return new IdleConnectionEvictor(getHttpClientConnectionManager(), 10, TimeUnit.SECONDS);
    }
}
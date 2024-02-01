/*
package com.seres.base.config.extend;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Slf4j
@Configuration
public class HttpConfig {
    */
/**
     * restTemplate
     *//*

    @Bean
    public RestTemplate restTemplate(HttpClient httpClient) {
        // 使用httpClient实现通信
        ClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        // string
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        // json
//        restTemplate.getMessageConverters().set(6, mappingJackson2HttpMessageConverter);
        return restTemplate;
    }

    */
/**
     * httpClient
     *//*

    @Bean
    public HttpClient httpClient() {
        // 默认的请求参数配置
        RequestConfig config = RequestConfig.custom()
                // 获取连接超时
                .setConnectionRequestTimeout(5000)
                // 连接超时
                .setConnectTimeout(5000)
                // 读超时
                .setSocketTimeout(120000)
                .build();
        HttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                .setMaxConnTotal(400)
                .setMaxConnPerRoute(100)
                .setConnectionManager(httpClientConnectionManager(trustManagers()))
                .build();
        return client;
    }

    private HttpClientConnectionManager httpClientConnectionManager(TrustManager[] trustManagers) {
        // 忽略证书问题
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, trustManagers, new SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            log.error(e.getMessage(), e);
        }
        // 忽略hostname验证
        HostnameVerifier verifier = (String hostname, SSLSession session) -> {
            return true;
        };
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sc, verifier);

        // 连接管路器配置
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                // 设置个性化的 sslSocketFactory
                .register("https", sslSocketFactory)
                .build();
        // connectionManager 连接管理器
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        // MaxTotal
        connectionManager.setMaxTotal(3000);
        // MaxPerRoute
        connectionManager.setDefaultMaxPerRoute(1000);
        return connectionManager;
    }

    */
/**
     * 创建多个信任管理器数组
     *
     * @return 多个信任管理器数组
     *//*

    public TrustManager[] trustManagers() {
        TrustManager trustManager = new X509TrustManager() {
            */
/**
             * 检查客户端的可靠性，
             * 不通过则抛出异常CertificateException
             * @param arg0
             * @param arg1
             *//*

            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
                // 不验证客户端的可靠性
            }

            */
/**
             * 检查服务端的可靠性，
             * 不通过则抛出异常CertificateException
             * @param arg0
             * @param arg1
             *//*

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
                // 不验证服务端的可靠性
            }

            */
/**
             * 获取可接受的证书
             * @return
             *//*

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        return new TrustManager[]{trustManager};
    }

}
*/

package com.seres.base.config.extend;

import com.seres.base.filter.InterfaceAuthFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * InterfaceAuthConfig配置
 * @author lyly
 * @Description
 * @date 2022年10月28日 上午9:06:49
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "interface.auth", name = "enable", havingValue = "true", matchIfMissing = true)
public class InterfaceAuthConfig {

    @Value("${interface.auth.urls:/ext/*}")
    private List<String> urls;
    @Value("${interface.auth.token:}")
    private String token;
    @Value("${interface.auth.intervalSeconds:0}")
    private long intervalSeconds;

    @Bean
    public FilterRegistrationBean interfaceAuthFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new InterfaceAuthFilter(token, intervalSeconds));
        urls.forEach(u -> {
            registration.addUrlPatterns(u); // 拦截路径
        });
        registration.setName("interfaceAuthFilter");    // 拦截器名称
        registration.setOrder(11);                       // 顺序
        return registration;
    }

}

package com.seres.base.config;

import com.seres.base.filter.TraceIdFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TraceIdLog配置
 */
@Slf4j
@Configuration
public class TraceIdConfig {

    @Bean
    public FilterRegistrationBean traceIdFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TraceIdFilter());
        registration.addUrlPatterns("/*");              // 拦截路径
        registration.setName("traceIdFilter");          // 拦截器名称
        registration.setOrder(1);                       // 顺序
        return registration;
    }

}

package com.seres.base.config.extend;

import com.seres.base.filter.IPAuthFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * IPAuthConfig配置
 * @author lyly
 * @Description
 * @date 2023年8月25日 上午16:06:49
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "ip.auth", name = "enable", havingValue = "true", matchIfMissing = true)
public class IPAuthConfig {

    @Value("${ip.auth.whites:192[.]168[.]0[.].*}")
    private List<String> whites;
    @Value("${ip.auth.ignores:}")
    private List<String> ignores;

    @Bean
    public FilterRegistrationBean ipAuthFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new IPAuthFilter(whites, ignores));
        registration.addUrlPatterns("/*"); // 拦截路径
        registration.setName("ipAuthFilter");    // 拦截器名称
        registration.setOrder(31);                       // 顺序
        return registration;
    }

}

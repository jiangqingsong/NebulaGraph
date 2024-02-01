package com.seres.base.config;

import com.seres.base.Constants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * FeignRequestTransfer配置
 */
@Configuration
public class FeignRequestTransferConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //add by tang bisheng 2023-01-17
        if (attributes == null){
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames == null) {
            return;
        }
        //处理上游请求头信息，传递时继续携带
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            if(Constants.HTTP_HEADER_CURRENT_USER_ID.equals(name)
                    || Constants.HTTP_HEADER_TRACE_ID.equals(name)){
                String value = request.getHeader(name);
                template.header(name, value);
            }
        }
    }
}
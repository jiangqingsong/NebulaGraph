package com.seres.base.filter;

import com.seres.base.Constants;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class TraceIdFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(req);
        String traceId = requestWrapper.getHeader(Constants.HTTP_HEADER_TRACE_ID);
        if(StringUtils.isEmpty(traceId)){
            traceId = UUID.randomUUID().toString().replace("-", "");
            requestWrapper.addHeader(Constants.HTTP_HEADER_TRACE_ID, traceId);
        }
        MDC.put(Constants.HTTP_HEADER_TRACE_ID, traceId);
        chain.doFilter(requestWrapper, resp);
        MDC.remove(Constants.HTTP_HEADER_TRACE_ID);
    }

    class HeaderMapRequestWrapper extends HttpServletRequestWrapper {

        public HeaderMapRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        private Map<String, String> headerMap = new HashMap<>();

        public void addHeader(String name, String value) {
            headerMap.put(name, value);
        }

        @Override
        public String getHeader(String name) {
            String headerValue = super.getHeader(name);
            if (headerMap.containsKey(name)) {
                headerValue = headerMap.get(name);
            }
            return headerValue;
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            List<String> names = Collections.list(super.getHeaderNames());
            for (String name : headerMap.keySet()) {
                if(!names.contains(name)){
                    names.add(name);
                }
            }
            return Collections.enumeration(names);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            List<String> values = Collections.list(super.getHeaders(name));
            if (headerMap.containsKey(name)) {
                values = Arrays.asList(headerMap.get(name));
            }
            return Collections.enumeration(values);
        }

    }


}

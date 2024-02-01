package com.seres.base.filter;

import com.seres.base.BaseErrCode;
import com.seres.base.exception.AppException;
import com.seres.base.util.AuthenticationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class InterfaceAuthFilter extends OncePerRequestFilter {

    private static final String BASIC_PREFIX = "Basic ";

    private String token;
    private long intervalSeconds;

    public InterfaceAuthFilter(String token, long intervalSeconds){
        this.token = token;
        this.intervalSeconds = intervalSeconds;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String authorization = Optional.ofNullable(req.getHeader("Authorization")).orElse("");
        authorization = authorization.replace(BASIC_PREFIX, "");
        if (!AuthenticationUtil.checkByRule1(authorization, token, intervalSeconds)){
            log.error("Authorization验证失败，Authorization={}，uri={}", authorization, uri);
            throw new AppException(BaseErrCode.AUTHORIZATION_CHECK_FAIL);
        }
        chain.doFilter(req, resp);
    }

}

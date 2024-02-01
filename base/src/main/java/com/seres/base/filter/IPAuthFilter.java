package com.seres.base.filter;

import com.seres.base.BaseErrCode;
import com.seres.base.exception.AppException;
import com.seres.base.util.IPUtil;
import com.seres.base.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class IPAuthFilter extends OncePerRequestFilter {

    private static final List<String> LOOP_IPS = Stream.of("0:0:0:0:0:0:0:1", "127.0.0.1").collect(Collectors.toList());
    private static final List<String> LOCAL_IPS = IPUtil.getLocalIps();

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * IP白名单正则表达式列表，如：192[.]168[.]1[.].*, 192[.]168[.]2[.].*
     */
    private List<String> whites;
    /**
     * 忽略的请求地址列表，支持AntPathMatcher匹配
     */
    private List<String> ignores;


    public IPAuthFilter(List<String> whites, List<String> ignores){
        this.whites = whites;
        this.ignores = ignores;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String rip = IPUtil.getRemoteAddr(req);
        if(LOOP_IPS.contains(rip) || LOCAL_IPS.contains(rip)){ // 回环IP或本机IP放过
            chain.doFilter(req, resp);
            return;
        }

        String uri = req.getRequestURI();
        if(Objects.nonNull(ignores) && ignores.size() > 0){
            for (String ignore : ignores){
                if(antPathMatcher.match(ignore, uri)){ // 忽略中的请求地址
                    chain.doFilter(req, resp);
                    return;
                }
            }
        }

        if(StringUtil.isNotEmpty(rip)){
            for(String white : whites){
                if(rip.matches(white)){ // 白名单中的IP
                    chain.doFilter(req, resp);
                    return;
                }
            }
        }
        log.error("IP验证失败，ip={}，uri={}", rip, uri);
        throw new AppException(BaseErrCode.IP_CHECK_FAIL);
    }

}

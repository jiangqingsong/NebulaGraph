package com.seres.base.util;

import com.seres.base.Constants;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

public class HttpHeaderHolder {

    public static Integer getUserId(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String userId = requestAttributes.getRequest().getHeader(Constants.HTTP_HEADER_CURRENT_USER_ID);
        return Objects.isNull(userId) ? null : Integer.valueOf(userId);
    }
}

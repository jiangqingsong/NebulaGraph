package com.seres.base.exception;

import com.seres.base.BaseErrCode;
import com.seres.base.ErrResp;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理器
 */
@Slf4j
@Profile({"!dev"})
@RestControllerAdvice
public class OtherBaseGlobalExceptionHandler extends BaseGlobalExceptionHandler {

    /**
     * 处理其他异常，非dev环境不能抛出未知异常的message信息
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ErrResp exceptionHandlerElse(HttpServletRequest req, Exception e){
        ErrResp resp = new ErrResp(e);
        log.error(JSON.toJSONString(resp));
        if (!(e instanceof AppException)){
            resp = new ErrResp(BaseErrCode.UNKNOWN_ERR);
            log.error(e.getMessage(), e);
        }
        return resp;
    }

}

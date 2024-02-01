package com.seres.base.exception;

import com.seres.base.BaseErrCode;
import com.seres.base.ErrResp;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class NoCatchException implements ErrorController {
 
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = {"/error"})
    public ErrResp exceptionHandler(HttpServletRequest req, Exception e){
        Integer code = (Integer) req.getAttribute("javax.servlet.error.status_code");
        if(Objects.nonNull(code) && code == 404){ // 404
            return new ErrResp(BaseErrCode.NO_FOUND);
        }
        String msg = String.format("%s: ", code);
        Exception exception = (Exception) req.getAttribute("javax.servlet.error.exception");
        if (Objects.nonNull(exception)) {
            if(exception instanceof AppException){ // 自定义异常
                return new ErrResp(exception);
            }
            msg += exception.getClass().getSimpleName() + ": " + exception.getMessage();
        }
        return new ErrResp(BaseErrCode.UNKNOWN_ERR.getCode(), msg);
    }
}

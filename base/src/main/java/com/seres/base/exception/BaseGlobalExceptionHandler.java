package com.seres.base.exception;

import com.seres.base.BaseErrCode;
import com.seres.base.ErrResp;
import com.seres.base.Error;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 异常处理器
 */
@Slf4j
public abstract class BaseGlobalExceptionHandler {

//    /**
//     * 配置下面配置实现404，但高版本spring.resources.add-mappings已过时
//     * spring.mvc.throw-exception-if-no-handler-found=true
//     * spring.resources.add-mappings=false
//     * @param req
//     * @param e
//     * @return
//     */
//    @ExceptionHandler(value = NoHandlerFoundException.class)
//    public ErrResp exception404Handler(HttpServletRequest req, Exception e){
//        return new ErrResp(BaseErrCode.NO_FOUND);
//    }

    /**
     * 请求参数验证失败，处理方法的参数上注解约束验证失败
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ErrResp constraintViolationException(HttpServletRequest req, ConstraintViolationException e){
        String message = e.getMessage();
        if(Objects.isNull(message)){
            message = "请求参数不合法";
        }
        ErrResp resp = new ErrResp(BaseErrCode.PARAM_ERR.getCode(), message);
        log.error(JSON.toJSONString(resp));
        return resp;
    }

    /**
     * 反序列化错误（body内容不存在），如json的body内容不存在
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ErrResp httpMessageNotReadableException(HttpServletRequest req, HttpMessageNotReadableException e){
        ErrResp resp = new ErrResp(BaseErrCode.BODY_ERR.getCode(), BaseErrCode.BODY_ERR.getMsg() +": "+ e.getMessage());
        log.error(JSON.toJSONString(resp));
        return resp;
    }

    /**
     * 请求体参数验证失败，如json中的某字段验证失败
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ErrResp methodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e){
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = errors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
        if(Objects.isNull(message)){
            message = "请求体参数不合法";
        }
        ErrResp resp = new ErrResp(BaseErrCode.BODY_PARAM_ERR.getCode(), message);
        log.error(JSON.toJSONString(resp));
        return resp;
    }

    /**
     * 文件超大
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ErrResp maxUploadSizeExceededException(HttpServletRequest req, MaxUploadSizeExceededException e){
        Error err = BaseErrCode.MAX_UPLOAD_SIZE_ERR;
        Optional.ofNullable(e.getCause()).ifPresent(c->{
            Optional.ofNullable(c.getCause()).ifPresent(cause->{
                if(cause instanceof FileSizeLimitExceededException){
                    FileSizeLimitExceededException ca = (FileSizeLimitExceededException)cause;
                    err.setMsg(String.format("%s，%s 超过最大限制%dKB", err.getMsg(), ca.getFileName(),ca.getPermittedSize()/1024));
                }else if(cause instanceof SizeLimitExceededException){
                    SizeLimitExceededException ca = (SizeLimitExceededException)cause;
                    err.setMsg(String.format("%s，最大%dKB，当前%dKB", err.getMsg(), ca.getPermittedSize()/1024, ca.getActualSize()/1024));
                }
            });
        });
        ErrResp resp = new ErrResp(err);
        log.error(JSON.toJSONString(resp));
        return resp;
    }

}

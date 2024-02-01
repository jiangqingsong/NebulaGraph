package com.seres.base.exception;

import com.seres.base.BaseErrCode;
import com.seres.base.Error;
import lombok.Data;

import java.util.Objects;

@Data
public class AppException extends RuntimeException{
    private int code = BaseErrCode.UNKNOWN_ERR.getCode();

    public AppException(String msg){
        super(msg);
    }

    public AppException(int code, String msg){
        super(msg);
        this.code = code;
    }

    public AppException(Error err){
        this(err.getCode(), err.getMsg());
    }

    public AppException(Exception e){
        super(e.getMessage(), e);
        if(e instanceof AppException){
            this.code = ((AppException) e).getCode();
        }
    }

    /**
     * 构造异常对象，msg = err.getMsg() + delimiter + appendMsg
     * @param err
     * @param appendMsg 追加的msg信息，使用delimiter连接到err.msg后面
     * @param delimiter 分隔符
     */
    public AppException(Error err, String appendMsg, String delimiter){
        this(err.getCode(), err.getMsg() + delimiter + appendMsg);
    }

    /**
     * 支持Error对象的msg属性中占位符{}的替换
     * @param err
     * @param params 替换占位符的参数列表
     */
    public static AppException build(Error err, Object... params){
        String msg = err.getMsg();
        if(Objects.nonNull(params) && params.length > 0){
            for (Object param: params){
                msg = msg.replaceFirst("\\{}", param.toString()); // 替换占位符：{}
            }
        }
        return new AppException(err.getCode(), msg);
    }

}

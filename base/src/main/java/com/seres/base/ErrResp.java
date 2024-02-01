package com.seres.base;

import com.seres.base.exception.AppException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

@Data
@ApiModel(description = "失败响应")
public class ErrResp extends Resp{
    private int code = BaseErrCode.UNKNOWN_ERR.getCode();
    @ApiModelProperty(value = "错误信息")
    private String msg;

    public ErrResp(String msg){
        this.msg = msg;
    }

    public ErrResp(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public ErrResp(Error err){
        this(err.getCode(), err.getMsg());
    }

    /**
     * 支持Error对象msg属性中的占位符{}的替换
     * @param err
     * @param params
     */
    public ErrResp(Error err, Object... params){
        String msg = err.getMsg();
        if(Objects.nonNull(params) && params.length > 0){
            for (Object param: params){
                msg = msg.replaceFirst("\\{}", param.toString()); // 替换占位符：{}
            }
        }
        this.code = err.getCode();
        this.msg = msg;
    }

    public ErrResp(Exception e){
        AppException ae = new AppException(e);
        this.code = ae.getCode();
        this.msg = ae.getMessage();
    }

    /**
     * 失败响应
     * @param msg
     * @return
     */
    public static ErrResp fail(String msg){
        return new ErrResp(msg);
    }

    /**
     * 失败响应
     * @param code
     * @param msg
     * @return
     */
    public static ErrResp fail(int code, String msg){
        return new ErrResp(code, msg);
    }

}

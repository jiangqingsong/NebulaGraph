package com.seres.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "标准响应")
public class Resp<T> {
    @ApiModelProperty(value = "响应码，200成功，否则失败")
    private int code = 200;
    @ApiModelProperty(value = "响应数据")
    private T data;

    public Resp(){
    }

    public Resp(T data){
        this.data = data;
    }

    /**
     * 成功响应
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Resp<T> success(T data){
        return new Resp<>(data);
    }

    /**
     * 成功响应
     * @return
     */
    public static Resp<Boolean> success(){
        return new Resp<>(true);
    }
}

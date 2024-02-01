package com.seres.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@ApiModel(description = "分页请求")
public class PageReq {

    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    @ApiModelProperty(value = "页码")
    private int pageNum = 1;
    @ApiModelProperty(value = "页大小")
    private int pageSize = 10;

    @ApiModelProperty(value = "排序字段")
    private String sortField;
    @ApiModelProperty(value = "排序类型：ASC|ascending|DESC|descending")
    @Pattern(regexp = "ASC|ascending|DESC|descending", message = "排序类型不正确")
    private String sortType;

    public String getSortType() {
        if("ascending".equals(sortType)){
            return ASC;
        }
        if("descending".equals(sortType)){
            return DESC;
        }
        return sortType;
    }
}

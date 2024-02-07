package com.seres.ngcommonserver.model;

import com.seres.ngcommonserver.annotation.ClassAutoMapping;
import com.seres.ngcommonserver.annotation.FieldAutoMapping;
import lombok.Data;

import java.io.Serializable;
/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/5 17:16
 */
@Data
@ClassAutoMapping("modelandclass")
public class ModelAndClass implements Serializable {
    /**
     * id vid
     */
    @FieldAutoMapping(method = "getId", type = "Long")
    private Object id;

    /**
     * 父级ID
     */
    @FieldAutoMapping(method = "getPid", type = "Long")
    private Long pid;/**
     * 名称
     */
    @FieldAutoMapping(method = "getName", type = "String")
    private String name;

    /**
     * 描述
     */
    @FieldAutoMapping(method = "getDescription", type = "String")
    private String description;

}

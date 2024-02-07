package com.seres.ngcommonserver.model.player;

import com.seres.ngcommonserver.annotation.ClassAutoMapping;
import com.seres.ngcommonserver.annotation.FieldAutoMapping;
import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/6 8:43
 */

@Data
@ClassAutoMapping("player")
public class Player implements Serializable {
    @FieldAutoMapping(method = "getId", type = "String")
    private String id;


    @FieldAutoMapping(method = "getName", type = "String")
    private String name;

    @FieldAutoMapping(method = "getAge", type = "Integer")
    private Integer age;
}

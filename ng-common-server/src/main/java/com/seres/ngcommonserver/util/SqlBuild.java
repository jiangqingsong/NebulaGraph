package com.seres.ngcommonserver.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/5 17:12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SqlBuild {
    private Object id;

    private String name;

    private String field;

    private String values;
}

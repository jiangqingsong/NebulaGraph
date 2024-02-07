package com.seres.ngcommonserver.result;

import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/5 17:10
 */
@Data
public class NebulaResult<T> {
    private Integer code;
    private String message;
    private List<T> data;

    public boolean isSuccessed(){
        return code == 0;
    }
}

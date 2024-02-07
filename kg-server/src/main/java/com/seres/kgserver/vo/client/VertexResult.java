package com.seres.kgserver.vo.client;

import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/7 11:04
 */
@Data
public class VertexResult {
}

@Data
class Error{
    private Integer code;
}
@Data
class Meta{
    private String type;
    private String id;
}
@Data
class Row{
    private String type;
    private String id;
}
@Data
class Data1{
    private String type;
    private String id;
}

@Data
class Result{
    private String spaceName;
    private List<String> columns;
    private Error errors;
    private Integer latencyInUs;
}

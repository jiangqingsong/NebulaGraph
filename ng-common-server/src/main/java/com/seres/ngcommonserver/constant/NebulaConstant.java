package com.seres.ngcommonserver.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/5 17:08
 */
public class NebulaConstant {
    public static final String USE = "USE ";
    public static final String SEMICOLON = "; ";
    public static final String ERROR_CODE = "-1";

    @Getter
    @AllArgsConstructor
    public enum NebulaJson{
        ERRORS("errors"),
        CODE("code"),
        MESSAGE("message"),
        RESULTS("results"),
        COLUMNS("columns"),
        DATA("data"),
        ROW("row");
        private String key;
    }
}

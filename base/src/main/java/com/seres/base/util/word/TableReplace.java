package com.seres.base.util.word;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class TableReplace {
    /**
     * 某个表格中的占位符及数据
     */
    private Map<String, String> table = new HashMap<>();
}

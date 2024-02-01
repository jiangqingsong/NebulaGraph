package com.seres.base.util.word;

import lombok.Data;

import java.util.LinkedHashMap;

/**
 * 列1  列2  列3
 * 行1  4.3  2.4   2
 * 行2  2.5  4.4   2
 * 行3  3.5  1.8   3
 * 行4  4.5  2.8   5
 */
@Data
public class DataSource {
    /**
     * 系列名/列名，从第二列数据列开始，如：new String[] {"男", "女"}
     */
    private String[] seriesName = {};
    /**
     * 类别/行 及其数据，如：category.put("2022-01", new String[] {"32" ,"21"}); category.put("2022-02", new String[] {"36" ,"26"});
     */
    private LinkedHashMap<String, Number[]> category = new LinkedHashMap<>();  // LinkedHashMap 可以保证有序

    public DataSource addCategory(String name, Number[] values) {
        category.put(name, values);
        return this;
    }
}

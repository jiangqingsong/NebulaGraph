package com.seres.base.util.word;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class WordVo {

    /**
     * 段落中的占位符及数据
     */
    private Map<String, String> textReplaces = new HashMap<>();

    /**
     * 图表数据
     */
    private Map<String, DataSource> chartDatas = new HashMap<>();

    /**
     * 表格替换数据
     */
    private Map<String, TableReplace> tableReplaces = new HashMap<>();

    /**
     * 表格插入数据
     */
    private Map<String, TableInsert> tableInserts = new HashMap<>();

    /**
     * 添加段落文本替换数据
     * @param key
     * @param val
     */
    public Map<String, String> addTextReplace(String key, String val){
        this.textReplaces.put(key, val);
        return textReplaces;
    }

    /**
     * 添加图表数据
     * @param key
     * @param dataSource
     */
    public Map<String, DataSource> addChartData(String key, DataSource dataSource){
        this.chartDatas.put(key, dataSource);
        return this.chartDatas;
    }

    /**
     * 获取图表数据
     * @param key
     * @return
     */
    public DataSource getChartDatas(String key){
        return this.chartDatas.get(key);
    }

    /**
     * 添加表格替换数据
     * @param key
     * @param replace
     */
    public Map<String, TableReplace> addTableReplace(String key, TableReplace replace){
        this.tableReplaces.put(key, replace);
        return this.tableReplaces;
    }

    /**
     * 获取表格替换数据
     * @param key
     * @return
     */
    public TableReplace getTableReplaces(String key){
        return this.tableReplaces.get(key);
    }

    /**
     * 添加表格插入数据
     * @param key
     * @param insert
     */
    public Map<String, TableInsert> addTableInsert(String key, TableInsert insert){
        this.tableInserts.put(key, insert);
        return this.tableInserts;
    }

    /**
     * 获取表格插入数据
     * @param key
     * @return
     */
    public TableInsert getTableInserts(String key){
        return this.tableInserts.get(key);
    }


}


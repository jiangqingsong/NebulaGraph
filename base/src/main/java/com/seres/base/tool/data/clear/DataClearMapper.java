package com.seres.base.tool.data.clear;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface DataClearMapper {
    /**
     * 删除数据
     * @param table
     * @param field
     * @param countDate
     * @return
     */
    @Delete("DELETE FROM ${table} WHERE DATE(${field}) < DATE(#{countDate})")
    long delete(@Param("table") String table, @Param("field") String field, @Param("countDate") String countDate);

    /**
     * 执行sql更新语句
     * @param sql
     */
    @Update("${sql}")
    long sql(@Param("sql") String sql);

}

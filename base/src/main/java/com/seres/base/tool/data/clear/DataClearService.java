package com.seres.base.tool.data.clear;

import com.seres.base.util.DateTimeUtil;
import com.seres.base.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class DataClearService {
    @Autowired
    private DataClearMapper dataClearMapper;

    /**
     * 删除表历史数据-日
     * @param ds
     */
    public void execDelByDay(ClearCfg ds) {
        if(null != ds.getCfg() && !ds.getCfg().isEmpty()){
            log.info("数据库表历史数据清除触发...");
            ds.getCfg().forEach(d->{
                Integer day = Integer.parseInt(d.split(":")[0]);
                String[] tables = d.split(":")[1].split("[|]");
                //过去第day-1个天（当天算第一天）
                String countDate = DateTimeUtil.getBeforeDay(day-1);
                for (int i = 0; i < tables.length; i++) {
                    String[] params = tables[i].split("[.]");
                    long count = deleteDataByTable(params[0], params[1], countDate);
                    log.info("清除表【{}】【{}】之前的数据【{}】条", params[0], countDate, count);
                }
            });
        }
    }

    /**
     * 删除表历史数据-月
     * @param ms
     */
    public void execDelByMonth(ClearCfg ms) {
        if(null != ms.getCfg() && !ms.getCfg().isEmpty()){
            log.info("数据库表历史数据清除触发...");
            ms.getCfg().forEach(m->{
                Integer month = Integer.parseInt(m.split(":")[0]);
                String[] tables = m.split(":")[1].split("[|]");
                //过去第month-1个月（当月算第一个月），第1天
                String countDate = DateTimeUtil.getBeforeMonthFirstDay(month-1);
                for (int i = 0; i < tables.length; i++) {
                    String[] params = tables[i].split("[.]");
                    long count = deleteDataByTable(params[0], params[1], countDate);
                    log.info("清除表【{}】【{}】之前的数据【{}】条", params[0], countDate, count);
                }
            });
        }
    }

    /**
     * 执行sql
     * @param cc
     */
    @Transactional(rollbackFor = Exception.class)
    public void execSql(ClearCfg cc) {
        String currentDate = DateTimeUtil.currentDate();
        if(null != cc.getSql() && !cc.getSql().isEmpty()){
            log.info("数据库SQL语句执行触发...");
            String[] ss = cc.getSql().split(";");
            for(String s : ss){
                if(null == s || StringUtil.isEmpty(s.trim())){
                    continue;
                }
                String sql = s.replace("#{currentDate}", currentDate);
                long count = updateDataBySql(sql);
                log.info("SQL语句【{}】执行完成，更新数据【{}】条", sql, count);
            }
        }
    }

    public long deleteDataByTable(String table, String field, String countDate){
        return dataClearMapper.delete(table, field, countDate);
    }

    public long updateDataBySql(String sql){
        return dataClearMapper.sql(sql);
    }
}

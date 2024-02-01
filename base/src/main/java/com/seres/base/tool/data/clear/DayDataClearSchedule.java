package com.seres.base.tool.data.clear;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 数据（数据库表）清理定时器
 */
@Slf4j
@Async
@Component
@ConditionalOnProperty(prefix = "data.clear.day", name = "enable", havingValue = "true", matchIfMissing = false)
public class DayDataClearSchedule {

    /**
     * 注入配置属性
     */
    @Resource
    private DataClearProperties dataClearProperties;

    @Autowired
    private DataClearService dataClearService;

    @Scheduled(cron = "${data.clear.day.cron:0 20 0 * * ?}") //缺省每天凌晨0点20执行， # "0 * * * * ?"
    public void clearDataByDay(){
        ClearCfg ds = dataClearProperties.getDay();
        if (null == ds){
            return;
        }
        log.info("日定时任务触发...");
        dataClearService.execDelByDay(ds);
        dataClearService.execSql(ds);
    }
}

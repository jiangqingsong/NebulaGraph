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
@ConditionalOnProperty(prefix = "data.clear.month", name = "enable", havingValue = "true", matchIfMissing = false)
public class MonthDataClearSchedule {

    /**
     * 注入配置属性
     */
    @Resource
    private DataClearProperties dataClearProperties;

    @Autowired
    private DataClearService dataClearService;

    /**
     * 按月清理趋势数据
     */
    @Scheduled(cron = "${data.clear.month.cron:0 30 0 1 * ?}") //缺省每个月1号凌晨0点30执行
    public void clearDataByMonth(){
        ClearCfg ms = dataClearProperties.getMonth();
        if (null == ms){
            return;
        }
        log.info("月定时任务触发...");
        dataClearService.execDelByMonth(ms);
        dataClearService.execSql(ms);
    }

}

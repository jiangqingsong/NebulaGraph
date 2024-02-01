package com.seres.base.tool.data.clear;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 数据（数据库表）清理配置属性
 * prefix 配置前缀
 * @author lyly
 * @Description
 * @date 2022年10月25日 上午9:08:07
 */
@Data
@ConfigurationProperties(prefix = "data.clear")
public class DataClearProperties {
    /**
     * 月定时配置
     */
    private ClearCfg month;
    /**
     * 日定时配置
     */
    private ClearCfg day;
}

@Data
class ClearCfg{
    /**
     * 是否启用
     */
    private Boolean enable = false;
    /**
     * 任务调度配置
     */
    private String cron;
    /**
     * 需要执行的sql语句
     */
    private String sql;
    /**
     * 表数据配置
     */
    private List<String> cfg;


}


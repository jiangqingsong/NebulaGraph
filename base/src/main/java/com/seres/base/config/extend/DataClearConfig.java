package com.seres.base.config.extend;

import com.seres.base.tool.data.clear.DataClearProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 数据（数据库表）清理配置
 * @author lyly
 * @Description
 * @date 2022年10月25日 上午9:08:07
 */
@Configuration
@EnableConfigurationProperties(DataClearProperties.class)
@EnableScheduling   // 开启定时任务
@EnableAsync    // 开启异步支持
@ComponentScan({"com.seres"})
@MapperScan("cn.com.broadtech.base.tool.data.clear")
public class DataClearConfig{

}
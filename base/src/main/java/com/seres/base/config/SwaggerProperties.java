package com.seres.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SwaggerProperties 配置类
 * prefix 配置前缀
 */
@Data
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    /**
     * 是否启用
     */
    private Boolean enable = false;
    /**
     * 扫描跟路径
     */
    private String basePackage;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 版本
     */
    private String version;


}
package com.seres.base.config.extend;

import com.seres.base.tool.sftp.SftpTemplate;
import com.seres.base.tool.sftp.SftpFactory;
import com.seres.base.tool.sftp.SftpPool;
import com.seres.base.tool.sftp.SftpProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Sftp配置
 * @author lyly
 * @ClassName SftpConfig
 * @Description
 * @date 2021年8月26日 上午9:08:07
 */
@Slf4j
@Configuration
public class SftpConfig {

    @Primary
    @Bean(name = "sftpProperties")
    @ConfigurationProperties(prefix = "sftp")
    public SftpProperties sftpProperties() {
        return new SftpProperties();
    }

    @Primary
    @Bean(name = "sftpTemplate")
    public SftpTemplate sftpTemplate(SftpProperties sftpProperties) {
        return new SftpTemplate(new SftpPool(new SftpFactory(sftpProperties)));
    }

}

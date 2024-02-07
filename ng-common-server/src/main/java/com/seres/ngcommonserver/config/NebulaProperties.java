package com.seres.ngcommonserver.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/5 17:06
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "nebula")
public class NebulaProperties {
    private List<NebulaAddress> address;
    private String username;
    private String password;
    private boolean reconnect;
    private String space;
}

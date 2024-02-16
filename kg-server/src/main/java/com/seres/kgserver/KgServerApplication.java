package com.seres.kgserver;

import com.seres.base.enable.EnableBaseConfig;
import com.seres.base.enable.extend.EnableMinio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
},
        scanBasePackages = {"org.nebula.contrib", "com.seres.kgserver"})
@EnableBaseConfig
@EnableMinio
public class KgServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KgServerApplication.class, args);
    }

}

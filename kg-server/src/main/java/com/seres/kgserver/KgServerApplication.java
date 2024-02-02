package com.seres.kgserver;

import com.seres.base.enable.EnableBaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
},
        scanBasePackages = {"org.nebula.contrib", "com.seres.kgserver"})
@EnableBaseConfig
public class KgServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KgServerApplication.class, args);
    }

}

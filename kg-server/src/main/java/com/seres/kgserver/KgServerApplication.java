package com.seres.kgserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class},
        scanBasePackages = {"org.nebula.contrib", "com.seres.kgserver"})
public class KgServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KgServerApplication.class, args);
    }

}

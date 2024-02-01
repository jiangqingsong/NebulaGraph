package com.seres.datapreprocess;

import com.seres.base.enable.EnableBaseConfig;
import com.seres.base.enable.extend.EnableMinio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        MongoAutoConfiguration.class
})
@EnableBaseConfig
@EnableMinio
public class DataProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataProcessApplication.class, args);
    }

}

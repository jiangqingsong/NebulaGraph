package com.seres.dataprocess;

import com.seres.datapreprocess.util.DBConnection;
import com.seres.datapreprocess.vo.MongoDataTransVO;
import com.seres.datapreprocess.vo.MysqlDataTransVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DataProcessApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testMysqlConnect(){

        MysqlDataTransVO mysql = new MysqlDataTransVO();
        mysql.setIp("10.36.191.30");
        mysql.setPort("23306");
        mysql.setDatabase("kg");
        mysql.setUsername("root");
        mysql.setPwd("dvx_A8k4");

        boolean result = DBConnection.testMySQLConnection(mysql);
        log.info("is connect ： {}", result);
        System.out.println(result);
    }

    @Test
    void testMongoConnect(){
        MongoDataTransVO mongo = new MongoDataTransVO();
        mongo.setIp("10.0.25.150");
        mongo.setPort(8017);
        mongo.setUsername("erdp");
        mongo.setPwd("erdp_seres@2022");
        mongo.setDatabase("admin");
        mongo.setTargetDB("erdp");
        //testMySQLConnection(mongo);
        boolean result = DBConnection.testMongoDBConnection(mongo);
        log.info("is connect ： {}", result);
    }
}

package com.seres.datapreprocess.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.seres.datapreprocess.trans.mongo.MongoConnector;
import com.seres.datapreprocess.vo.MongoDataTransVO;
import com.seres.datapreprocess.vo.MysqlDataTransVO;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Description: 测试mysql和mongodb连接情况
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/4 17:23
 */
@Slf4j
public class DBConnection {


    /**
     * @Description: 测试mysql数据库连接
     * @param
     * @return
     * @version v1.0
     * @author jiangqs
     * @date 2024/2/5 9:29
     */
    public static boolean testMongoDBConnection(MongoDataTransVO mongo) {
        //String host, Integer port, String username, String pwd, String database,
        //                                            String targetD
        MongoDatabase database = MongoConnector.getDatabase(mongo.getIp(), mongo.getPort(), mongo.getUsername(), mongo.getPwd(), mongo.getDatabase(), mongo.getTargetDB());
        if(database == null){
            log.error("连接mongodb失败！");
            return false;
        }else{
            log.info("连接mongodb成功");
            return true;
        }
    }

    /**
     * @Description: 测试mysql数据库连接
     * @param
     * @return
     * @version v1.0
     * @author jiangqs
     * @date 2024/2/5 9:29
     */
    public static boolean testMySQLConnection(MysqlDataTransVO mysql) {

        String url = "jdbc:mysql://" + mysql.getIp() + ":" + mysql.getPort() + "/" + mysql.getDatabase() + "?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false";

        try (Connection connection = DriverManager.getConnection(url, mysql.getUsername(), mysql.getPwd())) {
            System.out.println("MySQL连接成功");
            return true;
        } catch (SQLException e) {
            System.err.println("MySQL连接失败，错误信息：" + e.getMessage());
            return false;
        }
    }


    public static boolean testMongoDBConnection(String connectionString) {
        try (MongoClient mongoClient = new MongoClient(new MongoClientURI(connectionString))) {
            MongoDatabase database = mongoClient.getDatabase("admin");
            System.out.println("MongoDB连接成功，当前数据库名称为：" + database.getName());
            return true;
        } catch (Exception e) {
            System.err.println("MongoDB连接失败，错误信息：" + e.getMessage());
            return false;
        }
    }
}

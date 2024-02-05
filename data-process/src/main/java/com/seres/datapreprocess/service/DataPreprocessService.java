package com.seres.datapreprocess.service;

import com.seres.datapreprocess.trans.mongo.Mongo2csv;
import com.seres.datapreprocess.trans.mysql.DataExporter;
import com.seres.datapreprocess.trans.mysql.DatabaseConnector;
import com.seres.datapreprocess.util.DBConnection;
import com.seres.datapreprocess.vo.MongoDataTransVO;
import com.seres.datapreprocess.vo.MysqlDataTransVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description: 数据预处理服务类
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/1/24 17:48
 */
@Service
@Slf4j
public class DataPreprocessService {

    /**
     * @Description: mysql连接测试
     * @param
     * @return
     * @version v1.0
     * @author jiangqs
     * @date 2024/2/5 14:47
     */
    public boolean mysqlConnectTest(MysqlDataTransVO mysql){
        return DBConnection.testMySQLConnection(mysql);
    }


    /**
     * @Description: mongodb连接测试
     * @param
     * @return
     * @version v1.0
     * @author jiangqs
     * @date 2024/2/5 14:47
     */
    public boolean mongodbConnectTest(MongoDataTransVO mongo){
        return DBConnection.testMongoDBConnection(mongo);
    }

    /**
     * @param
     * @return
     * @Description: mysql转csv文件
     * @version v1.0
     * @author jiangqs
     * @date 2024/1/25 9:56
     */
    public boolean mysql2csv(MysqlDataTransVO mysqlDataTransVO) {
        try {
            DatabaseConnector connector = new DatabaseConnector();
            Connection connection = connector.getConnection(mysqlDataTransVO.getIp(), mysqlDataTransVO.getPort(),
                    mysqlDataTransVO.getDatabase(), mysqlDataTransVO.getUsername(), mysqlDataTransVO.getPwd());
            DataExporter.exportDataToCSV(connection, mysqlDataTransVO.getTargetFileName());
            return true;
        } catch (SQLException e) {
            log.error("连接mysql异常:" + e.getMessage());
            return false;
        } catch (IOException e) {
            log.error("csv文件写入异常: " + e.getMessage());
            return false;
        }
    }

    public boolean mongo2csv(MongoDataTransVO vo) {
        /*MongoDatabase database = MongoConnector.getDatabase(vo.getIp(), vo.getPort(), vo.getUsername(), vo.getPwd(),
                vo.getDatabase(), vo.getTargetDB());

        MongoCollection<Document> collection = database.getCollection(vo.getTargetCollection());*/
        //todo collection to csv
        //MongoDatabase database = MongoConnector.getDatabase("10.0.25.150", 8017, "erdp", "erdp_seres@2022",
        //                "admin", "erdp");
        //tire_gb
        //"D:\\seres\\知识图谱\\nebula\\mongo2csv\\erdp_tire_gb.csv"

        try {
            Mongo2csv.exportDataToCSV(vo);
            return true;
        } catch (IOException e) {
            log.error("mongo转csv失败： " + e.getMessage());
            return false;
        }
    }
}

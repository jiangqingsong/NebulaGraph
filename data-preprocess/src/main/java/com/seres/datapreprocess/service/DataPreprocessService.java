package com.seres.datapreprocess.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.seres.datapreprocess.trans.mongo.Mongo2csv;
import com.seres.datapreprocess.trans.mongo.MongoConnector;
import com.seres.datapreprocess.trans.mysql.DataExporter;
import com.seres.datapreprocess.trans.mysql.DatabaseConnector;
import com.seres.datapreprocess.vo.MongoDataTransVO;
import com.seres.datapreprocess.vo.MysqlDataTransVO;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class DataPreprocessService {
    private static Logger log = LoggerFactory.getLogger(DataPreprocessService.class);

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

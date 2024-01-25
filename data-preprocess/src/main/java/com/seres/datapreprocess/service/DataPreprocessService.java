package com.seres.datapreprocess.service;

import com.seres.datapreprocess.mysql.DataExporter;
import com.seres.datapreprocess.mysql.DatabaseConnector;
import com.seres.datapreprocess.vo.MysqlDataTransVO;
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
}

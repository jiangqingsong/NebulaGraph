package com.seres.datapreprocess.trans.mysql;


import com.seres.datapreprocess.service.DataPreprocessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/1/24 17:59
 */
public class DatabaseConnector {
    private static Logger log = LoggerFactory.getLogger(DataPreprocessService.class);

    public Connection getConnection(String ip, String port, String database, String username, String pwd) throws SQLException {
        String url = "jdbc:mysql://" + ip + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false";
        return DriverManager.getConnection(url, username, pwd);
    }
}

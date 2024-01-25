package com.seres.datapreprocess.mysql;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
/**
 * Description: 数据转csv
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/1/24 17:59
 */
public class DataExporter {
    public static ResultSet exportDataToCSV(Connection connection, String targetFile) throws SQLException, IOException {
        String query = "SELECT * FROM player";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        // 处理查询结果...
        ResultSetMetaData metadata = resultSet.getMetaData();
        int columnCount = metadata.getColumnCount();

        try (FileWriter writer = new FileWriter(targetFile)) {
            // 写入CSV文件头部
            for (int i = 1; i <= columnCount; i++) {
                writer.append(metadata.getColumnName(i));
                if (i != columnCount) {
                    writer.append(',');
                }
            }
            writer.append(System.lineSeparator());

            // 写入CSV文件内容
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    writer.append(resultSet.getString(i));
                    if (i != columnCount) {
                        writer.append(',');
                    }
                }
                writer.append(System.lineSeparator());
            }
        }

        resultSet.close();
        statement.close();

        return resultSet;
    }
}

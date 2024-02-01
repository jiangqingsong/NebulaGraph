package com.seres.datapreprocess.vo;

import lombok.Data;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/1/25 9:34
 */
@Data
public class MysqlDataTransVO {

    //目标mysql数据库IP地址
    private String ip;
    //目标mysql数据库port
    private String port;
    //目标数据库
    private String database;
    private String username;
    private String pwd;
    //转换后csv数据存储的位置
    private String targetFileName;
}

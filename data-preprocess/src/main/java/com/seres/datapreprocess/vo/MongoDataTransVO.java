package com.seres.datapreprocess.vo;

import lombok.Data;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/1/26 15:23
 */
@Data
public class MongoDataTransVO {
    //目标mysql数据库IP地址
    private String ip;
    //目标mysql数据库port
    private Integer port;
    //登录目标数据库
    private String database;
    private String username;
    private String pwd;
    //访问的目标数据库名称
    private String targetDB;
    private String targetCollection;
    //转换后csv数据存储的位置
    private String targetFileName;
}

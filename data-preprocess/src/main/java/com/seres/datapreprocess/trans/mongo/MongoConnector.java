package com.seres.datapreprocess.trans.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Description: mongo工具类
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/1/26 15:26
 */
public class MongoConnector {
    private static Logger log = LoggerFactory.getLogger(MongoDatabase.class);

    public static MongoDatabase getDatabase(String host, Integer port, String username, String pwd, String database,
                                            String targetDb) {

        try {
            //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
            //ServerAddress()两个参数分别为 服务器地址 和 端口
            ServerAddress serverAddress = new ServerAddress(host, port);
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            addrs.add(serverAddress);

            //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
            MongoCredential credential = MongoCredential.createScramSha1Credential(username, database, pwd.toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);

            //通过连接认证获取MongoDB连接
            MongoClient mongoClient = new MongoClient(addrs, credentials);

            //连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase(targetDb);
            return mongoDatabase;
        } catch (Exception e) {
            log.error("连接mongo出错：" + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        /*MongoDatabase database = getDatabase("10.0.25.150", 8017, "erdp", "erdp_seres@2022",
                "admin", "erdp");
        MongoCollection<Document> c1 = database.getCollection("tire_gb");
        FindIterable<Document> documents = c1.find();
        MongoCursor<Document> iterator = documents.iterator();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
                new File("D:\\seres\\知识图谱\\nebula\\mongo2csv\\erdp_tire_gb.csv")));

        //属性栏
        ArrayList<String> title = new ArrayList();
        int i = 0;
        Set<String> keys;
        while (iterator.hasNext()) {
            Document next = iterator.next();
            if (i == 0) {
                //从最新一行提取title
                next.keySet().forEach(k -> {
                    title.add(k);
                    try {
                        bufferedWriter.write(k);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                bufferedWriter.newLine();
                i++;
            }

            keys = next.keySet();
            for (String key : keys) {
                for (int j = 0; j < title.size(); j++) {
                    if (key.equals(title.get(j))) {
                        bufferedWriter.write(next.get(key).toString() + ",");
                    }
                }
            }
            bufferedWriter.newLine();
        }

        bufferedWriter.flush();
        bufferedWriter.close();*/

    }
}

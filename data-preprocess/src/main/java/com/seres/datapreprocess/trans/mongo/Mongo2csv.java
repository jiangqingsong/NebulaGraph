package com.seres.datapreprocess.trans.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.seres.datapreprocess.vo.MongoDataTransVO;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/1/29 15:16
 */
public class Mongo2csv {
    private static Logger log = LoggerFactory.getLogger(Mongo2csv.class);
    /**
     * @Description:  mongodb's collection to csv
     * @param
     * @return
     * @version v1.0
     * @author jiangqs
     * @date 2024/1/29 15:24
     */
    public static void exportDataToCSV(MongoDataTransVO vo) throws IOException {
        MongoDatabase database = MongoConnector.getDatabase(vo.getIp(), vo.getPort(), vo.getUsername(), vo.getPwd(),
                vo.getDatabase(), vo.getTargetDB());

        if(database == null){
            return;
        }
        MongoCollection<Document> c1 = database.getCollection(vo.getTargetCollection());
        FindIterable<Document> documents = c1.find();
        MongoCursor<Document> iterator = documents.iterator();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
                new File(vo.getTargetFileName())));

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
        bufferedWriter.close();
    }
}

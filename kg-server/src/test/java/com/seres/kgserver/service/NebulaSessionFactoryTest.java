package com.seres.kgserver.service;

import com.seres.kgserver.config.NebulaSessionFactory;
import com.vesoft.nebula.client.graph.data.Node;
import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.data.ValueWrapper;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.net.Session;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 测试 NebulaSessionFactory
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/7 14:06
 */

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class NebulaSessionFactoryTest {
    @Autowired
    private NebulaSessionFactory sessionFactory;

    @Test
    void testShowTags(){
        Session session = sessionFactory.getSession();
        try {
            String sql = "use demo_basketballplayer; MATCH (a)-[b]-(c) RETURN a.player.name, b, c limit 10";
            String json = session.executeJson(sql);

            ResultSet rs = session.execute(sql);
            List<ValueWrapper> wrappedValueList = new ArrayList<>();
            for (int i = 0; i < rs.rowsSize(); i++) {
                ResultSet.Record record = rs.rowValues(i);
                for (ValueWrapper value : record.values()) {
                    wrappedValueList.add(value);
                    if (value.isLong()) {
                        System.out.printf("%15s |", value.asLong());
                    }
                    if (value.isBoolean()) {
                        System.out.printf("%15s |", value.asBoolean());
                    }
                    if (value.isDouble()) {
                        System.out.printf("%15s |", value.asDouble());
                    }
                    if (value.isString()) {
                        System.out.printf("%15s |", value.asString());
                    }
                    if (value.isTime()) {
                        System.out.printf("%15s |", value.asTime());
                    }
                    if (value.isDate()) {
                        System.out.printf("%15s |", value.asDate());
                    }
                    if (value.isDateTime()) {
                        System.out.printf("%15s |", value.asDateTime());
                    }
                    if (value.isVertex()) {
                        System.out.printf("%15s |", value.asNode());
                        Node node = value.asNode();
                        ValueWrapper id = node.getId();
                    }
                    if (value.isEdge()) {
                        System.out.printf("%15s |", value.asRelationship());
                    }
                    if (value.isPath()) {
                        System.out.printf("%15s |", value.asPath());
                    }
                    if (value.isList()) {
                        System.out.printf("%15s |", value.asList());
                    }
                    if (value.isSet()) {
                        System.out.printf("%15s |", value.asSet());
                    }
                    if (value.isMap()) {
                        System.out.printf("%15s |", value.asMap());
                    }
                }
                System.out.println();
            }

//            System.out.println(json);
        } catch (IOErrorException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}

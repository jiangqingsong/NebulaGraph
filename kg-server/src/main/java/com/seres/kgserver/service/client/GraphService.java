package com.seres.kgserver.service.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.seres.kgserver.config.NebulaSessionFactory;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.net.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/7 8:58
 */
@Slf4j
@Service
public class GraphService {

    @Autowired
    private NebulaSessionFactory sessionFactory;


    //获取所有节点
    public String getAllVertex(String spaceName, Integer limit) {
        Session session = sessionFactory.getSession();
        try {
            String json = session.executeJson("USE " + spaceName + ";match (a) return a limit " + limit);
            return json;
        } catch (IOErrorException e) {
            log.error("获取所有节点数据异常：{}", e.getMessage());
            return null;
        }
    }
    /*public <T> NebulaResult<T> query(String graphSpace, String gql) {
        Session session = null;
        try {
            log.info("GQL: {}", gql);
            session = sessionFactory.getSession();
            NebulaResult<Void> res = query(session, "USE " + graphSpace);
            if (!res.isSuccess() || res.getResults() == null || res.getResults().size() == 0) {
                log.error("Failed to use space:{}", graphSpace);
                return null;
            }
            if (!graphSpace.equals(res.getResults().get(0).getSpaceName())) {
                log.error("Failed to use space:{}, result:{}", graphSpace, res.getResults().get(0).getSpaceName());
                return null;
            }
            return query(session, gql);
        } catch (IOErrorException e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            if (session != null) {
                session.release();
            }
        }
    }
    private <T> NebulaResult<T> query(Session session, String gql) throws IOErrorException {
        String json = session.executeJson(gql);
        return JacksonUtil.extractByType(json, new TypeReference() );
    }*/


    public void test() {
        Session session = sessionFactory.getSession();
        try {
//            String result = session.executeJson("use demo_basketballplayer; match (a) return a");
            String result = session.executeJson("use demo_basketballplayer;MATCH (a)-[b]-(c) RETURN a.player.name, b, c limit 100");
            JSONObject jsonObject = JSON.parseObject(result);
            System.out.println(jsonObject.toJSONString());
        } catch (IOErrorException e) {
            throw new RuntimeException(e);
        }
    }
}

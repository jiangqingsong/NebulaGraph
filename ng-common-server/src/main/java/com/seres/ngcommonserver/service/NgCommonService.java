package com.seres.ngcommonserver.service;

import com.alibaba.fastjson.JSONObject;
import com.seres.ngcommonserver.result.NebulaResult;
import com.seres.ngcommonserver.template.NebulaTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/5 17:21
 */
@Service
@Slf4j
public class NgCommonService {
    @Autowired
    private NebulaTemplate nebulaTemplate;

    public Map<String, NebulaResult> test(){
        try {
            String tagSql = "insert vertex player(name, age) values \"0205-01\":(\"0205-01\", 19)";
            String edgeSql = "";
            NebulaResult vertex = nebulaTemplate.executeObject(tagSql);
            //NebulaResult edge = nebulaTemplate.executeObject(edgeSql);

            Map<String, NebulaResult> map = new HashMap<>();
            map.put("vertex", vertex);
            //map.put("edge", edge);
            return map;
        }catch (Exception e){

            log.error("构建图数据失败！");
            return null;
        }
    }
}

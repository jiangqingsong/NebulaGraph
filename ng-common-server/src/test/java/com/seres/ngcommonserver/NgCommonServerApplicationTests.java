package com.seres.ngcommonserver;

import com.seres.ngcommonserver.model.player.Player;
import com.seres.ngcommonserver.result.NebulaResult;
import com.seres.ngcommonserver.service.NgCommonService;
import com.seres.ngcommonserver.template.NebulaTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class NgCommonServerApplicationTests {

    @Autowired
    private NgCommonService service;
    @Autowired
    private NebulaTemplate nebulaTemplate;


    @Test
    void contextLoads() {
        Map<String, NebulaResult> result = service.test();

        log.info(result.get("vertex").toString());
    }

    @Test
    void queryPlayer(){
        NebulaResult<Player> result = nebulaTemplate.queryObject("MATCH (v: player) RETURN v", Player.class);

        System.out.println(result);
    }

}

package com.seres.kgserver.service;

import com.seres.kgserver.service.client.GraphService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/7 10:05
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class NgServiceTest {

    @Autowired
    private GraphService graphService;

    @Test
    void test(){
        graphService.test();
    }

    @Test
    void getAllVertex(){
        String result = graphService.getAllVertex("demo_basketballplayer", 100);
        System.out.println(result);
    }


}

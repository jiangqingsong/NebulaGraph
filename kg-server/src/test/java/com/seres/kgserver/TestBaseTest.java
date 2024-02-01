package com.seres.kgserver;

import com.seres.kgserver.domain.PlayerDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Description: 基础功能测试
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/1/24 16:20
 */
@SpringBootTest
public class TestBaseTest {
    @Autowired
    private PlayerDao playerDao;

    @Test
    public void selectById() {
        System.out.println(playerDao.selectById("player104"));
    }


}

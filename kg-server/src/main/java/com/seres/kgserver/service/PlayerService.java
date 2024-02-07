package com.seres.kgserver.service;

import com.alibaba.fastjson.JSON;
import com.seres.kgserver.config.CustomPkGenerator;
import com.seres.kgserver.dao.PlayerDao;
import com.seres.kgserver.pojo.demo_basketballplayer.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/2 9:09
 */
@Service
@Slf4j
public class PlayerService {

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private CustomPkGenerator customPkGenerator;
    public String testPlayerApi(){
        //通过主键获取节点
        playerDao.selectById("");

        //根据 ID 集合获取节点
        playerDao.selectByIds(new ArrayList<>());




        Player player = playerDao.selectById("import-player-003");
        log.info("find success!");
        return JSON.toJSONString(player);
    }
}

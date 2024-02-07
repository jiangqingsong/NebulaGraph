package com.seres.kgserver.dao;

import com.seres.kgserver.pojo.demo_basketballplayer.Player;
import org.nebula.contrib.ngbatis.proxy.NebulaDaoBasic;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface PlayerDao extends NebulaDaoBasic<Player, String> {
    List<Player> selectPlayer();

    List<Object> selectAllTags();
}

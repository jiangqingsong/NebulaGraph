package com.seres.kgserver.dao;

import com.seres.kgserver.pojo.Player;
import org.nebula.contrib.ngbatis.proxy.NebulaDaoBasic;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Resource
public interface PlayerDao extends NebulaDaoBasic<Player, String> {
}

package com.seres.kgserver.dao.isp;

import com.seres.kgserver.nebula.tag.isp.Project;
import com.seres.kgserver.pojo.demo_basketballplayer.Player;
import org.nebula.contrib.ngbatis.proxy.NebulaDaoBasic;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 项目DAO
 * @param
 * @return
 * @version v1.0
 * @author jiangqs
 * @date 2024/2/15 15:46
 */
@Resource
public interface ProjectDao extends NebulaDaoBasic<Project, String> {

}

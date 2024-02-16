package com.seres.kgserver.dao.isp;

import com.seres.kgserver.nebula.tag.isp.Phase;
import com.seres.kgserver.nebula.tag.isp.Project;
import org.nebula.contrib.ngbatis.proxy.NebulaDaoBasic;

import javax.annotation.Resource;

/**
 * @Description: 阶段DAO
 * @param
 * @return
 * @version v1.0
 * @author jiangqs
 * @date 2024/2/15 15:46
 */
@Resource
public interface PhaseDao extends NebulaDaoBasic<Phase, String> {

}

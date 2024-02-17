package com.seres.kgserver.dao.isp;

import com.seres.kgserver.nebula.tag.isp.Phase;
import com.seres.kgserver.nebula.tag.isp.Project;
import org.nebula.contrib.ngbatis.proxy.NebulaDaoBasic;
import org.springframework.data.repository.query.Param;

import javax.annotation.Resource;
import java.util.List;

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

    void insertAffiliatedProject(@Param("phaseVid") String phaseVid, @Param("projectVid") String projectVid);
    List<Phase> selectPhasesByProjectId(@Param("projectId") String projectId);

    List<Phase> selectAllPhases();

    void updateInfoById(@Param("phase") Phase phase);
}

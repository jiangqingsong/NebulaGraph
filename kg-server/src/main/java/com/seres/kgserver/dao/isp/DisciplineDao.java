package com.seres.kgserver.dao.isp;

import com.seres.kgserver.nebula.tag.isp.Discipline;
import com.seres.kgserver.nebula.tag.isp.File;
import org.nebula.contrib.ngbatis.proxy.NebulaDaoBasic;
import org.springframework.data.repository.query.Param;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 学科DAO
 * @param
 * @return
 * @version v1.0
 * @author jiangqs
 * @date 2024/2/15 15:46
 */
@Resource
public interface DisciplineDao extends NebulaDaoBasic<Discipline, String> {

    //插入边（学科->项目）
    void insertAffiliatedPhase(@Param("disciplineVid") String disciplineVid, @Param("phaseVid") String phaseVid);

    //插入边（学科->阶段）
    void insertAffiliatedProject(@Param("disciplineVid") String disciplineVid, @Param("projectVid") String projectVid);

    List<Discipline> selectDisciplineByProjectId(@Param("projectId") String projectId);
     List<Discipline> selectAllDiscipline();

     void updateInfoById(@Param("discipline") Discipline discipline);
}

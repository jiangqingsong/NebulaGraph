package com.seres.kgserver.service.isp;

import com.seres.base.util.DateTimeUtil;
import com.seres.base.util.IDUtil;
import com.seres.base.util.StringUtil;
import com.seres.kgserver.dao.isp.DisciplineDao;
import com.seres.kgserver.nebula.tag.isp.Discipline;
import com.seres.kgserver.nebula.tag.isp.Phase;
import com.seres.kgserver.nebula.tag.isp.Project;
import com.seres.kgserver.vo.isp.AddDisciplineVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: 学科服务类
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/16 16:03
 */
@Service
@Slf4j
public class DisciplineService {
    @Autowired
    private DisciplineDao disciplineDao;


    /**
    * @Description 获取所有学科信息
    * @Param
    * @Return
    */
    public List<Discipline> getDisciplines(){
        List<Discipline> disciplines = disciplineDao.selectAllDiscipline();
        return disciplines;
    }

    /**
     * @Description 通过项目ID获取学科信息
     * @Param
     * @Return
     */
    public List<Discipline> getDisciplinesByProjectId(String projectId){
        List<Discipline> disciplines = disciplineDao.selectDisciplineByProjectId(projectId);
        return disciplines;
    }

    /**
    * @Description 新增学科
    * @Param
    * @Return
    */

    public boolean addDiscipline(AddDisciplineVO vo){

        try {
            String projectId = vo.getProjectId();
            String phaseId = vo.getPhaseId();
            //插入节点学科
            Discipline discipline = new Discipline();
            String uuid = IDUtil.generatorUUID();
            discipline.setId(uuid);
            discipline.setVid(uuid);
            discipline.setProjectId(projectId);
            discipline.setPhaseId(phaseId);
            discipline.setDisciplineName(vo.getDisciplineName());
            discipline.setDescription(vo.getDescription());
            discipline.setUpdateTime(DateTimeUtil.currentDateTime());
            disciplineDao.insert(discipline);
            log.info("插入学科{}成功", vo.getDisciplineName());
            //判断是否有上游关系

            if(StringUtil.isNotEmpty(projectId)){
                //存在则插入对应的关系边
                Project project = new Project();
                project.setId(projectId);
                disciplineDao.insertAffiliatedProject(discipline.getVid(), projectId);
                log.info("插入关系成功{}-{}->{}", discipline.getVid(), "隶属项目", projectId);
            }
            if(StringUtil.isNotEmpty(phaseId)){
                //存在则插入对应的关系边

                Phase phase = new Phase();
                phase.setId(phaseId);
                disciplineDao.insertAffiliatedPhase(discipline.getVid(), phaseId);
                log.info("插入关系成功{}-{}->{}", discipline.getVid(), "隶属阶段", phaseId);
            }

            return true;
        }catch (Exception e){
            log.error("新增学科失败！ {}", e.getMessage());
            return false;
        }
    }

    /**
    * @Description 删除学科（todo 是否需要同时删除相关的边关系？）
    * @Param
    * @Return
    */
    public boolean deleteDiscipline(String id){
        try {
            disciplineDao.deleteById(id);
            return true;
        }catch (Exception e){
            log.error("删除学科失败！{}", e.getMessage());
            return false;
        }
    }

    /**
    * @Description 修改学科信息
    * @Param
    * @Return
    */
    public boolean updateById(Discipline discipline){
        try {
            discipline.setUpdateTime(DateTimeUtil.currentDateTime());
            disciplineDao.updateInfoById(discipline);
            return true;
        }catch (Exception e){
            log.error("修改学科失败！{}", e.getMessage());
            return false;
        }
    }

}

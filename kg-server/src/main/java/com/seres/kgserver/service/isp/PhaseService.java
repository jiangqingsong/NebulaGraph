package com.seres.kgserver.service.isp;

import com.seres.base.util.DateTimeUtil;
import com.seres.base.util.IDUtil;
import com.seres.base.util.StringUtil;
import com.seres.kgserver.dao.isp.PhaseDao;
import com.seres.kgserver.nebula.tag.isp.Phase;
import com.seres.kgserver.nebula.tag.isp.Project;
import com.seres.kgserver.vo.isp.AddPhaseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/17 11:23
 */
@Service
@Slf4j
public class PhaseService {
    @Autowired
    private PhaseDao phaseDao;


    /**
     * @Description 获取所有阶段信息
     * @Param
     * @Return
     */
    public List<Phase> getPhases() {
        List<Phase> disciplines = phaseDao.selectAllPhases();
        return disciplines;
    }

    /**
     * @Description 通过项目ID获取阶段信息
     * @Param
     * @Return
     */
    public List<Phase> getPhasesByProjectId(String projectId) {
        List<Phase> disciplines = phaseDao.selectPhasesByProjectId(projectId);
        return disciplines;
    }

    /**
    * @Description 新增阶段信息
    * @Param
    * @Return
    */

    public boolean add(AddPhaseVO vo) {
        try {
            String projectId = vo.getProjectId();
            String uuid = IDUtil.generatorUUID();
            //1 插入节点（阶段）
            Phase phase = new Phase();
            BeanUtils.copyProperties(vo, phase);
            phase.setVid(uuid);
            phase.setId(uuid);
            phase.setUpdateTime(DateTimeUtil.currentDateTime());
            phaseDao.insert(phase);
            //2 插入关系（阶段）->（项目）
            if (StringUtil.isNotEmpty(projectId)) {
                phaseDao.insertAffiliatedProject(uuid, projectId);
            }
            return true;
        } catch (Exception e) {
            log.error("插入阶段阶段或对应关系出错：{}", e.getMessage());
            return false;
        }
    }

    /**
     * @Description 删除阶段（todo 是否需要同时删除相关的边关系？）
     * @Param
     * @Return
     */
    public boolean deletePhase(String id){
        try {
            phaseDao.deleteById(id);
            return true;
        }catch (Exception e){
            log.error("删除阶段失败！{}", e.getMessage());
            return false;
        }
    }

    /**
     * @Description 修改项目信息
     * @Param
     * @Return
     */
    public boolean updateById(Phase phase){
        try {
            phase.setUpdateTime(DateTimeUtil.currentDateTime());
            phaseDao.updateInfoById(phase);
            return true;
        }catch (Exception e){
            log.error("修改项目失败！{}", e.getMessage());
            return false;
        }
    }
}

package com.seres.kgserver.service.isp;

import com.seres.base.util.DateTimeUtil;
import com.seres.base.util.IDUtil;
import com.seres.kgserver.dao.isp.ProjectDao;
import com.seres.kgserver.nebula.tag.isp.Discipline;
import com.seres.kgserver.nebula.tag.isp.Project;
import com.seres.kgserver.vo.isp.AddProjectVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description: 项目服务类
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/16 16:03
 */
@Service
@Slf4j
public class ProjectService {
    @Autowired
    private ProjectDao projectDao;

    /**
    * @Description 新增项目
    * @Param
    * @Return
    */

    public boolean addProject(AddProjectVO vo){

        try {
            //插入节点项目
            String uuid = IDUtil.generatorUUID();
            Project project = new Project();
            project.setVid(uuid);
            project.setId(uuid);
            project.setProjectName(vo.getProjectName());
            project.setDescription(vo.getDescription());
            project.setType(vo.getType());
            project.setUpdateTime(DateTimeUtil.currentDateTime());
            projectDao.insert(project);
            return true;
        }catch (Exception e){
            log.error("新增项目失败！ {}", e.getMessage());
            return false;
        }
    }

    /**
     * @Description 删除项目（todo 是否需要同时删除相关的边关系？）
     * @Param
     * @Return
     */
    public boolean deleteProject(String id){
        try {
            projectDao.deleteById(id);
            return true;
        }catch (Exception e){
            log.error("删除项目失败！{}", e.getMessage());
            return false;
        }
    }

    /**
     * @Description 修改项目信息
     * @Param
     * @Return
     */
    public boolean updateById(Project project){
        try {
            project.setUpdateTime(DateTimeUtil.currentDateTime());
            projectDao.updateInfoById(project);
            return true;
        }catch (Exception e){
            log.error("修改项目失败！{}", e.getMessage());
            return false;
        }
    }

}

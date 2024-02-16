package com.seres.kgserver.service.isp;

import com.seres.base.util.DateTimeUtil;
import com.seres.base.util.IDUtil;
import com.seres.kgserver.dao.isp.ProjectDao;
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

}

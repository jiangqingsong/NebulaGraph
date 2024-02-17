package com.seres.kgserver.dao.isp;

import com.seres.kgserver.nebula.tag.isp.Project;
import org.nebula.contrib.ngbatis.proxy.NebulaDaoBasic;
import org.springframework.data.repository.query.Param;

import javax.annotation.Resource;

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
    void updateInfoById(@Param("project") Project project);
}

package com.seres.kgserver.controller.isp;

import com.seres.base.ErrResp;
import com.seres.base.Resp;
import com.seres.kgserver.nebula.tag.isp.Project;
import com.seres.kgserver.service.isp.ProjectService;
import com.seres.kgserver.vo.isp.AddProjectVO;
import com.seres.kgserver.vo.isp.DeleteProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:项目controller类
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/16 16:19
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/add")
    public Resp<String> add(@RequestBody AddProjectVO vo) {
        boolean success = projectService.addProject(vo);
        if (success) {
            return new Resp<>("新增项目成功！");
        } else {
            return new ErrResp("新增项目失败！");
        }
    }

    @PostMapping("/deleteById")
    public Resp<String> deleteById(@RequestBody DeleteProjectVO vo) {
        boolean success = projectService.deleteProject(vo.getId());
        if (success) {
            return new Resp<>("删除学科成功！");
        } else {
            return new ErrResp("删除学科失败！");
        }
    }

    @PostMapping("/update")
    public Resp<String> update(@RequestBody Project vo) {
        boolean success = projectService.updateById(vo);
        if (success) {
            return new Resp<>("修改项目成功！");
        } else {
            return new ErrResp("修改项目失败！");
        }
    }
}

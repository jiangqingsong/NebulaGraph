package com.seres.kgserver.controller.isp;

import com.seres.base.Resp;
import com.seres.kgserver.service.isp.ProjectService;
import com.seres.kgserver.vo.isp.AddDisciplineVO;
import com.seres.kgserver.vo.isp.AddProjectVO;
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
            return new Resp<>("新增项目失败！");
        }
    }
}

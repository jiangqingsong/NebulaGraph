package com.seres.kgserver.controller.isp;

import com.seres.base.ErrResp;
import com.seres.base.Resp;
import com.seres.kgserver.nebula.tag.isp.Phase;
import com.seres.kgserver.service.isp.PhaseService;
import com.seres.kgserver.vo.isp.AddPhaseVO;
import com.seres.kgserver.vo.isp.DeletePhaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description: 阶段controller类
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/17 13:59
 */
@RestController
@RequestMapping("/isp/phase")
public class PhaseController {

    @Autowired
    private PhaseService phaseService;

    @PostMapping("/add")
    public Resp<String> add(@RequestBody AddPhaseVO vo) {
        boolean success = phaseService.add(vo);
        if (success) {
            return new Resp<>("新增阶段成功！");
        } else {
            return new ErrResp("新增阶段失败！");
        }
    }
    @PostMapping("/deleteById")
    public Resp<String> deleteById(@RequestBody DeletePhaseVO vo) {
        boolean success = phaseService.deletePhase(vo.getId());
        if (success) {
            return new Resp<>("删除阶段成功！");
        } else {
            return new ErrResp("删除阶段失败！");
        }
    }

    @GetMapping("/getPhases")
    public Resp<List<Phase>> getPhases() {
        return new Resp<>(phaseService.getPhases());
    }

    @GetMapping("/getPhasesByProjectId")
    public Resp<List<Phase>> getPhasesByProjectId(@RequestParam String projectId) {
        return new Resp<>(phaseService.getPhasesByProjectId(projectId));
    }

    @PostMapping("/update")
    public Resp<String> update(@RequestBody Phase vo) {
        boolean success = phaseService.updateById(vo);
        if (success) {
            return new Resp<>("修改阶段成功！");
        } else {
            return new ErrResp("修改阶段失败！");
        }
    }
}

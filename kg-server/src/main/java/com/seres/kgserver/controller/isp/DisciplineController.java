package com.seres.kgserver.controller.isp;

import com.seres.base.Resp;
import com.seres.kgserver.nebula.tag.isp.Discipline;
import com.seres.kgserver.service.isp.DisciplineService;
import com.seres.kgserver.vo.isp.AddDisciplineVO;
import com.seres.kgserver.vo.isp.DeleteDisciplineVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:学科controller类
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/16 16:19
 */
@RestController
@RequestMapping("/discipline")
public class DisciplineController {

    @Autowired
    private DisciplineService disciplineService;

    @PostMapping("/add")
    public Resp<String> add(@RequestBody AddDisciplineVO vo) {
        boolean success = disciplineService.addDiscipline(vo);
        if (success) {
            return new Resp<>("新增学科成功！");
        } else {
            return new Resp<>("新增学科失败！");
        }
    }

    @PostMapping("/deleteById")
    public Resp<String> deleteById(@RequestBody DeleteDisciplineVO vo) {
        boolean success = disciplineService.deleteDiscipline(vo.getId());
        if (success) {
            return new Resp<>("删除学科成功！");
        } else {
            return new Resp<>("删除学科失败！");
        }
    }

    @GetMapping("/getDisciplines")
    public Resp<List<Discipline>> getDisciplines() {
        return new Resp<>(disciplineService.getDisciplines());
    }
}
package com.seres.kgserver.nebula.tag.isp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nebula.contrib.ngbatis.annotations.Space;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description:阶段
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/15 11:13
 */
@Data
@Table(name = "phase")
@Space(name = "isp")
@NoArgsConstructor
@AllArgsConstructor
public class Phase {
    @Id
    private String vid;
    private String id;
    private String phaseName;
    private String beginTime;//阶段开始时间
    private String endTime;//阶段结束时间
    private String status;//loadcase当前状态
    private String type;//阶段类型
    private String timeType;
    private String description;//阶段描述
    private String bClear;//'SOP超期数据冗余数据是否被清理 0：未清理 1：已清理'
    private String updateTime;
}

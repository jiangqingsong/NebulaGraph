package com.seres.kgserver.nebula.tag.isp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nebula.contrib.ngbatis.annotations.Space;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description:项目
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/15 11:10
 */
@Data
@Table(name = "project")
@Space(name = "isp")
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    private String vid;
    private String id;
    private String projectName;
    private String type;//项目类型如：整车项目，模型项目，竞品车项目，其他
    private String description;
    private String updateTime;
}

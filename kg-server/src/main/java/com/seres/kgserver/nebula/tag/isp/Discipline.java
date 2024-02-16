package com.seres.kgserver.nebula.tag.isp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nebula.contrib.ngbatis.annotations.Space;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description:学科
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/15 11:14
 */
@Data
@Table(name = "discipline")
@Space(name = "isp")
@NoArgsConstructor
@AllArgsConstructor
public class Discipline {
    @Id
    private String vid;
    private String id;
    private String projectId;
    private String phaseId;
    private String disciplineName;//学科名称
    private String description;//学科描述
    private String updateTime;//修改时间
}

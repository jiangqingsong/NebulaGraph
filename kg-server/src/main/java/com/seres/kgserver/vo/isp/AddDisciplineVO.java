package com.seres.kgserver.vo.isp;

import lombok.Data;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/16 16:05
 */
@Data
public class AddDisciplineVO {
    private String projectId;//所属项目ID
    private String phaseId;//所属阶段ID
    private String disciplineName;//学科名称
    private String description;//学科描述
}

package com.seres.kgserver.nebula.edge.isp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nebula.contrib.ngbatis.annotations.Space;

import javax.persistence.Table;

/**
 * Description: 归属项目
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/15 15:03
 */
@Data
@Table(name = "affiliated_project")
@Space(name = "isp")
@NoArgsConstructor
public class AffiliatedProject {
}

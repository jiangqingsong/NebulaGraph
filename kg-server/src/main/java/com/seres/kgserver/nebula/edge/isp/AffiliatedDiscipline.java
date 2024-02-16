package com.seres.kgserver.nebula.edge.isp;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.nebula.contrib.ngbatis.annotations.Space;

import javax.persistence.Table;

/**
 * Description: 归属学科
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/15 15:03
 */
@Data
@Table(name = "affiliated_discipline")
@Space(name = "isp")
@NoArgsConstructor
public class AffiliatedDiscipline {
}

package com.seres.ngcommonserver.model;
import com.seres.ngcommonserver.annotation.ClassAutoMapping;
import com.seres.ngcommonserver.annotation.FieldAutoMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ClassAutoMapping("project_attributeandrelationship")
/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/5 17:17
 */
public class ProjectAttributeAndRelationship extends Edge implements Serializable{
    @FieldAutoMapping(method = "getStartId",type = "Long")
    private Long startId;

    @FieldAutoMapping(method = "getEndId",type = "Long")
    private Long endId;
}

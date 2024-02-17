package com.seres.kgserver.vo.isp;

import lombok.Data;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/17 11:24
 */
@Data
public class AddPhaseVO {
    private String projectId;
    private String phaseName;
    private String beginTime;
    private String endTime;
    private String status;
    private String type;
    private String timeType;
    private String description;
    private String bClear;
}

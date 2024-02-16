package com.seres.kgserver.vo.isp;

import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/16 15:40
 */
@Data
public class BatchDownloadVO {
    private List<String> fileNames;
    private String zipName;
}

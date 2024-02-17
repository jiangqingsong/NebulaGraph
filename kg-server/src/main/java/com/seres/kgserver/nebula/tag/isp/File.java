package com.seres.kgserver.nebula.tag.isp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nebula.contrib.ngbatis.annotations.Space;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description:文件
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/15 11:10
 */
@Data
@Table(name = "file")
@Space(name = "isp")
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    private String vid;
    private String id;
    private String fileName;
    private String updateTime;//yyyy-MM-dd hh:mm:ss
}

package com.seres.kgserver.dao.isp;

import com.seres.kgserver.nebula.tag.isp.File;
import com.seres.kgserver.nebula.tag.isp.Phase;
import org.nebula.contrib.ngbatis.proxy.NebulaDaoBasic;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @Description: 文件DAO
 * @param
 * @return
 * @version v1.0
 * @author jiangqs
 * @date 2024/2/15 15:46
 */
@Resource
public interface FileDao extends NebulaDaoBasic<File, String> {
    //插入边（文件->学科）
    void insertAffiliatedDiscipline(@Param("fileVid") String fileVid, @Param("disciplineVid") String disciplineVid);
}

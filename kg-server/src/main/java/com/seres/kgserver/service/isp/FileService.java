package com.seres.kgserver.service.isp;

import com.seres.base.tool.minio.MinioService;
import com.seres.base.util.DateTimeUtil;
import com.seres.base.util.IDUtil;
import com.seres.kgserver.dao.isp.FileDao;
import com.seres.kgserver.nebula.tag.isp.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Description:文件操作服务类
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/16 8:55
 */
@Service
@Slf4j
public class FileService {
    @Autowired
    private MinioService minioService;

    @Autowired
    private FileDao fileDao;



    /**
    * @Description 通过学科ID获取文件列表
    * @Param
    * @Return
    */

    public List<File> getFilesByDisciplineId(String disciplineId){
        return fileDao.selectFilesByDisciplineId(disciplineId);
    }
    /**
     * @Description 单个文件上传
     * @Param
     * @Return
     */
    public boolean upload(MultipartFile file, String disciplineId) {

        String filename = file.getOriginalFilename();
        //1 上传文件
        try (InputStream in = file.getInputStream()) {
            minioService.upload(filename, in);
        } catch (Exception e) {
            log.error("upload file to minIO system fail! " + e.getMessage());
            return false;
        }

        //2 插入文件节点
        String updateTime = DateTimeUtil.currentDateTime();
        String id = IDUtil.generatorUUID();
        File file1 = new File();
        file1.setVid(id);
        file1.setId(id);
        file1.setFileName(filename);
        file1.setUpdateTime(updateTime);
        try {
            fileDao.insert(file1);
            //3 插入边(（文件）-[隶属]->（学科）)
            fileDao.insertAffiliatedDiscipline(id, disciplineId);
            return true;
        } catch (Exception e) {
            log.error("插入文件节点或边关系出错：{}", e.getMessage());
            return false;
        }
    }

    /**
     * @param
     * @return
     * @Description: 单个文件下载
     */
    public void download(String fileName, HttpServletResponse response) {
        minioService.downLoad(fileName, response);
    }

    /**
     * @Description 批量文件下载
     * @Param
     * @Return
     */
    public void batchDownload(List<String> fileNames, String zip, HttpServletResponse res, HttpServletRequest req) {
        minioService.batchDownload(fileNames, zip, res, req);
    }

    /**
     * @Description 单个文件删除
     * @Param
     * @Return
     */

    public boolean delete(String fileName) {
        try {
            minioService.remove(fileName);
            return true;
        } catch (Exception e) {
            log.error("删除文件{}失败! {}", fileName, e.getMessage());
            return false;
        }
    }

    /**
     * @Description 单个文件删除
     * @Param
     * @Return
     */

    public boolean batchDelete(List<String> fileNames) {
        try {
            fileNames.forEach(fileName -> {

                minioService.remove(fileName);
            });
            return true;
        } catch (Exception e) {
            log.error("批量删除文件{}失败! {}", fileNames.toString(), e.getMessage());
            return false;
        }
    }


}

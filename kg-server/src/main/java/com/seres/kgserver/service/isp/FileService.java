package com.seres.kgserver.service.isp;

import com.seres.base.tool.minio.MinioProperties;
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
    private MinioProperties minioProperties;

    @Autowired
    private FileDao fileDao;

    public void uploadFile(String localFilePath, String fileName, String disciplineId) {
        //todo 上传文件到minIO
        //todo 插入节点（文件）
        String updateTime = DateTimeUtil.currentDateTime("yyyy-MM-dd hh:mm:ss");
        String id = IDUtil.generatorUUID();
        File file = new File();
        file.setId(id);
        file.setFileName(fileName);

//        file.setFilePath();
        fileDao.insert(file);
        //todo 插入边(（文件）-[隶属]->（学科）)
//        fileDao.insertEdge();

//        String fileName = "多模态知识图谱构建与应用1.pdf";
        try (FileInputStream in = new FileInputStream(localFilePath)) {
            minioService.upload(fileName, in);
        } catch (Exception e) {
            log.error("upload file to minIO system fail! " + e.getMessage());
        }
    }

    /**
     * @param
     * @return
     * @Description: 单个文件上传
     * @version v1.0
     * @author jiangqs
     * @date 2024/2/16 10:20
     */
    public void upload(MultipartFile file) {
        String filename = file.getOriginalFilename();

        try (InputStream in = file.getInputStream()) {
            minioService.upload(filename, in);
        } catch (Exception e) {
            log.error("upload file to minIO system fail! " + e.getMessage());
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


}

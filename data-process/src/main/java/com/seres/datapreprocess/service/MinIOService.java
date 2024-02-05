package com.seres.datapreprocess.service;

import com.seres.base.tool.minio.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileInputStream;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/2 8:38
 */
@Service
@Slf4j
public class MinIOService {
    @Resource
    private MinioService minioService;
    public void uploadFile(){
        try (FileInputStream in = new FileInputStream("D:\\seres\\知识图谱\\MinIO\\test\\多模态知识图谱构建与应用1.pdf")){
            minioService.upload("test.txt", in);
        }catch (Exception e){
            log.error("upload file to minIO system fail! " + e.getMessage());
        }
    }
}

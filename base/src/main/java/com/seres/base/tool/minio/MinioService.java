package com.seres.base.tool.minio;

import com.seres.base.BaseErrCode;
import com.seres.base.exception.AppException;
import com.seres.base.util.FileUtil;
import com.seres.base.util.IOUtil;
import com.seres.base.util.IDUtil;
import io.minio.*;
import io.minio.messages.Tags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Redis操作
 * @ClassName MinioService
 * @Description 操作minio API
 * @author lyly
 * @date 2023年1月29日 上午13:55:35
 */
@Slf4j
@Service
public class MinioService {

    @Autowired
    private MinioProperties minioProperties;

    private String bucketName;

    private MinioClient client;

    @PostConstruct
    private void init(){
        bucketName = minioProperties.getBucketName();
        client = MinioClient.builder().endpoint(minioProperties.getUrl())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
        checkBucket();
    }

    /**
     * 检查桶，不存在则创建
     */
    private void checkBucket() {
        try {
            boolean isExist = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if(isExist) {
                log.info("Bucket {} already exists.", bucketName);
            } else {
                client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("Bucket {} create success.", bucketName);
            }
        } catch (Exception e) {
            log.error("minio桶操作失败：{}", bucketName, e);
            throw new AppException(BaseErrCode.FILE_OPT_ERR);
        }
    }

    /**
     * 上传文件，使用uuid作为文件名
     * @param in 文件流，调用者自己处理流的关闭
     * @param tags 标签信息（额外信息）
     * @return uuid，上传至minio后的文件名
     */
    public String upload(InputStream in, Map<String, String> tags){
        String fileName = IDUtil.generatorUUID();
        return upload(fileName, in, tags);
    }

    /**
     * 上传文件，使用uuid作为文件名
     * @param in 文件流，调用者自己处理流的关闭
     * @return uuid，上传至minio后的文件名
     */
    public String upload(InputStream in){
        String fileName = IDUtil.generatorUUID();
        return upload(fileName, in);
    }

    /**
     * 上传文件，若远程存在同名文件则覆盖
     * @param fileName 上传后的文件名，如：test.png，支持目录结构，如：/log/test.txt
     * @param in 文件流，调用者自己处理流的关闭
     * @param tags 标签信息（扩展信息）
     * @return 上传至minio后的文件名，同参数fileName
     */
    public String upload(String fileName, InputStream in, Map<String, String> tags){
        log.debug("上传文件至minio：{}", fileName);
        try {
            PutObjectArgs.Builder args = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(in, in.available(), -1);
            if(null != tags && !tags.isEmpty()){
                args.tags(tags);
            }
            client.putObject(args.build());
            return fileName;
        }catch (Exception e){
            log.error("上传文件至minio失败：{}", fileName, e);
            throw new AppException(BaseErrCode.FILE_UPLOAD_ERR);
        }
    }

    /**
     * 上传文件，若远程存在同名文件则覆盖
     * @param fileName 上传后的文件名，如：test.png，支持目录结构，如：/log/test.txt
     * @param in 文件流，调用者自己处理流的关闭
     * @return 上传至minio后的文件名，同参数fileName
     */
    public String upload(String fileName, InputStream in){
        return upload(fileName, in, null);
    }

    /**
     * 上传文件，若远程存在同名文件则覆盖
     * @param minioName 上传后的文件名，如：test.png，支持目录结构，如：/log/test.txt
     * @param localFilePath 本地文件全路径
     * @param tags 标签信息（扩展信息）
     * @return 上传至minio后的文件名，同参数fileName
     */
    public String upload(String minioName, String localFilePath, Map<String, String> tags){
        log.debug("上传文件至minio：{}", minioName);
        try {
            UploadObjectArgs.Builder args = UploadObjectArgs.builder()
                    .bucket(bucketName)
                    .object(minioName)
                    .filename(localFilePath);
            if(null != tags && !tags.isEmpty()){
                args.tags(tags);
            }
            client.uploadObject(args.build());
            return minioName;
        }catch (Exception e){
            log.error("上传文件至minio失败：{}", minioName, e);
            throw new AppException(BaseErrCode.FILE_UPLOAD_ERR);
        }
    }

    /**
     * 上传文件，若远程存在同名文件则覆盖
     * @param minioName 上传后的文件名，如：test.png，支持目录结构，如：/log/test.txt
     * @param localFilePath 本地文件全路径
     * @return 上传至minio后的文件名，同参数fileName
     */
    public String upload(String minioName, String localFilePath){
        return upload(minioName, localFilePath, null);
    }

    /**
     * 设置minio文件标签信息（扩展信息），原始标签信息会被清空
     * @param fileName 远程minio文件名，如：test.png，支持目录结构，如：/log/test.txt
     * @param tags 标签信息（扩展信息）
     */
    public void setTags(String fileName, Map<String, String> tags){
        log.debug("设置minio文件标签信息：{}", fileName);
        try {
            client.setObjectTags(SetObjectTagsArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .tags(tags)
                    .build());
        }catch (Exception e){
            log.error("设置minio文件标签信息失败：{}", fileName, e);
            throw new AppException(BaseErrCode.FILE_OPT_ERR);
        }
    }

    /**
     * 获取minio文件流
     * @param fileName 远程minio文件名，如：test.png，支持目录结构，如：/log/test.txt
     * @return 文件流
     */
    public InputStream get(String fileName){
        log.debug("获取minio文件：{}", fileName);
        try {
            return client.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build());
        }catch (Exception e){
            log.error("获取minio文件流失败：{}", fileName, e);
            throw new AppException(BaseErrCode.FILE_DOWNLOAD_ERR);
        }
    }

    /**
     * 获取minio文件标签信息（扩展信息）
     * @param fileName 远程minio文件名，如：test.png，支持目录结构，如：/log/test.txt
     * @return 标签信息（扩展信息）
     */
    public Map<String, String> getTags(String fileName){
        log.debug("获取minio文件标签信息：{}", fileName);
        try {
            Tags tags = client.getObjectTags(GetObjectTagsArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build());
            return tags.get();
        }catch (Exception e){
            log.error("获取minio文件标签信息失败：{}", fileName, e);
            throw new AppException(BaseErrCode.FILE_OPT_ERR);
        }
    }

    /**
     * 获取minio文件contentType
     * @param fileName 远程minio文件名，如：test.png，支持目录结构，如：/log/test.txt
     * @return contentType
     */
    public String contentType(String fileName){
        log.debug("获取minio文件contentType：{}", fileName);
        return stat(fileName).contentType();
    }

    /**
     * 获取minio文件元数据metadata
     * @param fileName 远程minio文件名，如：test.png，支持目录结构，如：/log/test.txt
     * @return 元数据metadata
     */
    public Map<String, String> metadata(String fileName){
        log.debug("获取minio文件元数据metadata：{}", fileName);
        return stat(fileName).userMetadata();
    }

    /**
     * 获取minio文件Stat信息
     * @param fileName 远程minio文件名，如：test.png，支持目录结构，如：/log/test.txt
     * @return 标签信息（扩展信息）
     */
    private StatObjectResponse stat(String fileName){
        try {
            StatObjectResponse stat = client.statObject(StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build());
            return stat;
        }catch (Exception e){
            log.error("获取minio文件Stat信息失败：{}", fileName, e);
            throw new AppException(BaseErrCode.FILE_OPT_ERR);
        }
    }

    /**
     * 移除远程minio文件
     * @param fileName 远程minio文件名，如：test.png，支持目录结构，如：/log/test.txt
     */
    public void remove(String fileName){
        log.debug("删除远程minio文件：{}", fileName);
        try {
            client.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build());
        }catch (Exception e){
            log.error("删除远程minio文件失败：{}", fileName, e);
            throw new AppException(BaseErrCode.FILE_OPT_ERR);
        }
    }

    /**
     * 获取minio文件流并写入输出流
     * @param fileName 远程minio文件名，如：test.png，支持目录结构，如：/log/test.txt
     * @param out
     */
    public void write(String fileName, OutputStream out){
        try (InputStream in = get(fileName)) {
            IOUtil.copy(in, out);
        }catch (Exception e){
            log.error("获取minio文件流并写入输出流失败：{}", fileName, e);
            throw new AppException(BaseErrCode.FILE_OPT_ERR);
        }
    }

    /**
     * 获取minio文件流并保存至本地文件
     * @param minioFile 远程minio文件名，如：test.png，支持目录结构，如：/log/test.txt
     * @param localFile 保存至本地的文件名全路径
     */
    public void save(String minioFile, String localFile){
        try (InputStream in = get(minioFile)) {
            FileUtil.save(in, localFile);
        }catch (Exception e){
            log.error("获取minio文件流并写入本地文件失败：{}", minioFile, e);
            throw new AppException(BaseErrCode.FILE_DOWNLOAD_ERR);
        }
    }

    /**
     * 获取minio文件流并下载
     * @param filePath 远程minio文件名路径，如：test.png，支持目录结构，如：/log/test.txt
     * @param response
     */
    public void downLoad(String filePath, HttpServletResponse response){
        downLoad(filePath, filePath, response);
    }


    /**
     * 获取minio文件流并下载，自定义下载后的文件名
     * @param filePath 远程minio文件名路径，如：test.png，支持目录结构，如：/log/test.txt
     * @param saveName 下载保存时的文件名
     * @param response
     */
    public void downLoad(String filePath, String saveName, HttpServletResponse response){
        try (InputStream in = get(filePath)) {
            FileUtil.download(in, saveName, response);
        }catch (Exception e){
            log.error("获取minio文件流并下载失败：{}", filePath, e);
            throw new AppException(BaseErrCode.FILE_DOWNLOAD_ERR);
        }
    }
}

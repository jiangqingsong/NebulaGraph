package com.seres.base.util;

import com.seres.base.BaseErrCode;
import com.seres.base.Constants;
import com.seres.base.exception.AppException;
import feign.Response;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 文件操作工具类
 */
@Slf4j
public class FileUtil extends cn.hutool.core.io.FileUtil {

    // 更多工具参考
    // java.nio.file.Files
    // https://apidoc.gitee.com/dromara/hutool/

    /**
     * 获取文件字节
     * @param file
     * @return
     */
    public static byte[] getBytes(@NonNull File file){
        try {
            return readBytes(file);
        } catch (Exception e) {
            log.error("获取文件字节异常，file：{}", file.getName(), e);
            throw new AppException("获取文件字节异常");
        }
    }

    /**
     * 读取本地文件字节内容
     * @param filePath 本地文件的全路径
     * @return
     */
    public static byte[] readAllBytes(String filePath){
        try {
            return Files.readAllBytes(Paths.get(filePath));
        }catch (Exception e){
            log.error("读取本地文件字节内容失败：{}", filePath, e);
            throw new AppException(BaseErrCode.FILE_OPT_ERR);
        }
    }

    /**
     * http下载，将输入流写入Response
     * @param in  输入流，需自行处理流的关闭
     * @param fileName 下载的文件名
     * @param resp HttpServletResponse
     */
    public static void download(InputStream in, String fileName, HttpServletResponse resp){
        resp.setHeader("Content-Type","application/x-download;charset=" + Constants.UTF_8);
        try {
            resp.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, Constants.UTF_8));
        } catch (UnsupportedEncodingException e) {
            log.error("文件下载失败，fileName：{}", fileName, e);
            throw new AppException(BaseErrCode.FILE_DOWNLOAD_ERR);
        }

        try (OutputStream outputStream = resp.getOutputStream()){
            IOUtil.copy(in, outputStream);
        } catch (Exception e) {
            log.error("文件下载失败，fileName：{}", fileName, e);
            throw new AppException(BaseErrCode.FILE_DOWNLOAD_ERR);
        }
    }

    /**
     * http下载，将字节数组写入Response
     * @param in 字节数组
     * @param fileName 下载的文件名
     * @param resp HttpServletResponse
     */
    public static void download(byte[] in, String fileName, HttpServletResponse resp){
        try(ByteArrayInputStream bi = new ByteArrayInputStream(in)) {
            download(bi, fileName, resp);
        }catch (Exception e){
            log.error("openfeign文件下载失败，fileName：{}", fileName, e);
            throw new AppException(BaseErrCode.FILE_DOWNLOAD_ERR);
        }
    }

    /**
     * http下载，将本地文件流写入Response
     * @param filePath 文件路径
     * @param fileName 下载的文件名
     * @param resp HttpServletResponse
     */
    public static void download(String filePath, String fileName, HttpServletResponse resp){
        download(readAllBytes(filePath), fileName, resp);
    }

    /**
     * openfeign 远程调用下载
     * @param response openfeign响应对象
     * @param fileName 下载的文件名
     * @param httpResp HttpServletResponse
     */
    public static void download(Response response, String fileName, HttpServletResponse httpResp){
        Response.Body body = response.body();
        try (InputStream inputStream = body.asInputStream()){
            download(inputStream, fileName, httpResp);
        } catch (Exception e) {
            log.error("openfeign文件下载失败，fileName：{}", fileName, e);
            throw new AppException(BaseErrCode.FILE_DOWNLOAD_ERR);
        }
    }

    /**
     * 创建目录，支持递归创建父目录
     * @param dir
     */
    public static void createDirs(String dir) {
        Path path = Paths.get(dir);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            log.error("文件夹创建失败，dir：{}", dir, e);
            throw new AppException(BaseErrCode.FILE_OPT_ERR);
        }
    }

    /**
     * 创建目录，支持递归创建父目录
     * @param dir
     */
    public static void createDir(String dir) {
        Path path = Paths.get(dir);
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            log.error("文件夹创建失败，dir：{}", dir, e);
            throw new AppException(BaseErrCode.FILE_OPT_ERR);
        }
    }

    /**
     * 保存输入流至本地文件
     * @param in 输入流，需自行处理流的关闭
     * @param dir 保存文件的目录，不存在则自动创建
     * @param fileName 文件的名称
     * @return
     */
    public static void save(InputStream in, String dir, String fileName){
        createDirs(dir);
        String local = Paths.get(dir, fileName).toString();
        save(in, local);
    }

    /**
     * 保存输入流至本地文件，输入流位置将移至尾部
     * @param in 输入流，需自行处理流的关闭，
     * @param filePath 保存本地文件的全路径
     * @return
     */
    public static void save(InputStream in, String filePath){
        try {
            Files.copy(in, Paths.get(filePath));
        }catch (Exception e){
            log.error("保存至本地失败：{}", filePath, e);
            throw new AppException(BaseErrCode.FILE_OPT_ERR);
        }
    }

    /**
     * 将本地文件字节内容复制到输出流
     * @param filePath 本地文件的全路径
     * @param out 输出流，需自行处理流的关闭，
     * @return
     */
    public static void copy(String filePath, OutputStream out){
        try {
            Files.copy(Paths.get(filePath), out);
        }catch (Exception e){
            log.error("复制本地文件字节内容失败：{}", filePath, e);
            throw new AppException(BaseErrCode.FILE_OPT_ERR);
        }
    }

    /**
     * 读取本地文件所有行内容
     * @param filePath 本地文件的全路径
     * @param cs 编码
     * @return
     */
    public static List<String> readAllLines(String filePath, Charset cs){
        try {
            return Files.readAllLines(Paths.get(filePath), cs);
        }catch (Exception e){
            log.error("读取本地文件字节内容失败：{}", filePath, e);
            throw new AppException(BaseErrCode.FILE_OPT_ERR);
        }
    }

    /**
     * 读取本地文件所有行内容（UTF-8编码）
     * @param filePath 本地文件的全路径
     * @return
     */
    public static List<String> readAllLines(String filePath){
        return readAllLines(filePath, StandardCharsets.UTF_8);
    }

    /**
     * 获取文件最后修改日期
     * @param filePath
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String lastModified(String filePath) {
        File f = new File(filePath);
        if(!f.exists()){
            log.warn("文件不存在={}", filePath);
            return "";
        }
        return DateTimeUtil.format(f.lastModified());
    }

}

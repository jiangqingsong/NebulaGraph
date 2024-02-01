package com.seres.base.tool.sftp;

import com.seres.base.exception.AppException;
import com.seres.base.util.PathUtil;
import com.seres.base.util.SftpUtil;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

@Slf4j
public class SftpTemplate {

    private static final String ERR_MSG = "sftp操作失败";

    private SftpPool pool;

    public SftpTemplate(SftpPool pool) {
        this.pool = pool;
    }

    /**
     * 将输入流数据上传到sftp服务器，目录不存在则自动级联创建
     * 上传过程中文件名以.temp结尾，上传完成后去掉最后的.temp
     * @param ftpFile 上传后的绝对文件名（包含绝对路径），eg: /img/cc/xx.png
     * @param input 输入流
     * @return 上传后文件路径，即：dir+"/"+sftpFileName
     */
    public String upload(String ftpFile, InputStream input) {
        return upload(PathUtil.getParent(ftpFile), PathUtil.getFileName(ftpFile), input, true);
    }

    /**
     * 将输入流数据上传到sftp服务器，目录不存在则自动级联创建
     * 上传过程中文件名以.temp结尾，上传完成后去掉最后的.temp
     * @param ftpBasePath 上传后的绝对基础路径，eg: /img、/img/、/img/bb
     * @param ftpDir 上传后的相对目录，eg: cc/dd、cc/dd/
     * @param fileName 上传后的文件名，eg: xx.png
     * @param input 输入流
     * @return 上传后文件路径，即：dir+"/"+sftpFileName
     */
    public String upload(String ftpBasePath, String ftpDir, String fileName, InputStream input) {
        return upload(PathUtil.getPath(ftpBasePath, ftpDir), fileName, input, true);
    }

    /**
     * 将输入流数据上传到sftp服务器，目录不存在则自动级联创建
     * 上传过程中文件名以.temp结尾，上传完成后去掉最后的.temp
     * @param ftpDir 上传后的绝对目录，eg: /img、/img/、/img/cc
     * @param fileName 上传后的文件名，eg: xx.png
     * @param input 输入流
     * @return 上传后文件路径，即：dir+"/"+sftpFileName
     */
    public String upload(String ftpDir, String fileName, InputStream input) {
        return upload(ftpDir, fileName, input, true);
    }

    /**
     * 将输入流数据上传到sftp服务器
     * 上传过程中文件名以.temp结尾，上传完成后去掉最后的.temp
     * @param ftpDir 上传后的目录，eg: /img、/img/、/img/cc
     * @param fileName 上传后的文件名，eg: xx.png
     * @param input 输入流
     * @param mkdirs 目的目录不存在是否自动级联创建
     * @return 上传后文件路径，即：dir+"/"+sftpFileName
     */
    public String upload(String ftpDir, String fileName, InputStream input, boolean mkdirs) {
        ChannelSftp sftp = null;
        try {
            sftp = pool.borrowObject();
            if(mkdirs){
                SftpUtil.cdAndMkdirs(ftpDir, sftp);
            }else {
                sftp.cd(ftpDir);
            }
            String tempName = fileName + ".temp";
            sftp.put(input, tempName);  //上传文件中
            sftp.rename(tempName, fileName);  //上传完成后重命名
            log.info("上传文件成功：{}", fileName);
        } catch (SftpException e) {
            log.error("上传文件失败：{}", fileName, e);
            throw new AppException(e);
        } finally {
            pool.returnObject(sftp);
        }
        return PathUtil.getPath(ftpDir, fileName);
    }

    /**
     * 将文件上传到sftp服务器，目录不存在则自动级联创建
     * 上传过程中文件名以.temp结尾，上传完成后去掉最后的.temp
     * @param ftpFile 上传后的绝对文件名（包含绝对路径），eg: /img/cc/xx.png
     * @param localFile 上传的文件
     * @return 上传后文件路径，即：dir+"/"+sftpFileName
     */
    public String upload(String ftpFile, File localFile) {
        return upload(PathUtil.getParent(ftpFile), PathUtil.getFileName(ftpFile), localFile, true);
    }

    /**
     * 将文件上传到sftp服务器，目录不存在则自动级联创建
     * 上传过程中文件名以.temp结尾，上传完成后去掉最后的.temp
     * @param ftpBasePath 上传后的绝对基础路径，eg: /img、/img/、/img/bb
     * @param ftpDir 上传后的相对目录，eg: cc/dd、cc/dd/
     * @param fileName 上传后的文件名，eg: xx.png
     * @param localFile 上传的文件
     * @return 上传后文件路径，即：dir+"/"+sftpFileName
     */
    public String upload(String ftpBasePath, String ftpDir, String fileName, File localFile) {
        return upload(PathUtil.getPath(ftpBasePath, ftpDir), fileName, localFile, true);
    }

    /**
     * 将文件上传到sftp服务器，目录不存在则自动级联创建
     * 上传过程中文件名以.temp结尾，上传完成后去掉最后的.temp
     * @param ftpDir 上传后的绝对目录，eg: /img、/img/、/img/cc
     * @param fileName 上传后的文件名，eg: xx.png
     * @param localFile 上传的文件
     * @return 上传后文件路径，即：dir+"/"+sftpFileName
     */
    public String upload(String ftpDir, String fileName, File localFile) {
        return upload(ftpDir, fileName, localFile, true);
    }

    /**
     * 将文件上传到sftp服务器
     * 上传过程中文件名以.temp结尾，上传完成后去掉最后的.temp
     * @param ftpDir 上传后的目录，eg: /img、/img/、/img/cc
     * @param fileName 上传后的文件名，eg: xx.png
     * @param localFile 上传的文件
     * @param mkdirs 目的目录不存在是否自动级联创建
     * @return 上传后文件相对于basePath的相对路径，即：dir + sftpFileName
     */
    public String upload(String ftpDir, String fileName, File localFile, boolean mkdirs){
        try (FileInputStream input = new FileInputStream(localFile)){
            return upload(ftpDir, fileName, input, mkdirs);
        }catch (IOException e){
            log.error(ERR_MSG, e);
            throw new AppException(e);
        }
    }

    /**
     * 下载文件
     * @param ftpDir 文件目录，绝对路径
     * @param fileName 下载的文件
     * @param saveFile 存在本地的路径，全路径
     */
    public void downloadFile(String ftpDir, String fileName, String saveFile){
        downloadFile(PathUtil.getPath(ftpDir, fileName), saveFile);
    }

    /**
     * 下载文件
     * @param ftpFile 下载的文件（含目录绝对路径）
     * @param saveFile 存在本地的路径，全路径
     */
    public void downloadFile(String ftpFile, String saveFile){
        ChannelSftp sftp = null;
        try(FileOutputStream fos = new FileOutputStream(saveFile)){
            sftp = pool.borrowObject();
            sftp.get(ftpFile, fos);
        } catch (Exception e) {
            log.error(ERR_MSG, e);
            throw new AppException(e);
        } finally {
            pool.returnObject(sftp);
        }
    }

    /**
     * 获取远程字符文件内容
     * @param ftpFile 下载的文件（含目录绝对路径）
     * @param charset，编码，如：StandardCharsets.UTF_8
     * @return 文本内容
     */
    public String download(String ftpFile, Charset charset){
        byte[] bytes = download(ftpFile);
        if (StringUtils.isEmpty(charset)) {
            charset = StandardCharsets.UTF_8;
        }
        try {
            return new String(bytes, charset);
        } catch (Exception e) {
            log.error(ERR_MSG, e);
            throw new AppException(e);
        }
    }

    /**
     * 获取远程文件字节流
     * @param ftpDir 文件目录，绝对路径
     * @param fileName 下载的文件
     * @return 字节流数组
     */
    public byte[] download(String ftpDir, String fileName){
        return download(PathUtil.getPath(ftpDir, fileName));
    }

    /**
     * 获取远程文件字节流
     * @param ftpFile 下载的文件（含目录绝对路径）
     * @return 字节流数组
     */
    public byte[] download(String ftpFile){
        ChannelSftp sftp = pool.borrowObject();
        try (InputStream is = sftp.get(ftpFile)){
            return IOUtils.toByteArray(is);
        } catch (Exception e) {
            log.error(ERR_MSG, e);
            throw new AppException(e);
        } finally {
            pool.returnObject(sftp);
        }

    }

    /**
     * 删除文件
     * @param ftpDir 要删除文件所在目录，全路径
     * @param fileName 要删除的文件
     */
    public void delete(String ftpDir, String fileName){
        delete(PathUtil.getPath(ftpDir, fileName));
    }

    /**
     * 删除文件
     * @param ftpFile 要删除的文件（含目录绝对路径）
     */
    public void delete(String ftpFile){
        ChannelSftp sftp = null;
        try {
            sftp = pool.borrowObject();
            sftp.rm(ftpFile);
            log.info("删除文件成功：{}", ftpFile);
        } catch (SftpException e) {
            log.error("删除文件失败：{}", ftpFile, e);
            throw new AppException(e);
        } finally {
            pool.returnObject(sftp);
        }
    }

    /**
     * 列出目录下的文件
     * @param ftpDir 要列出的目录，全路径
     */
    public Vector<ChannelSftp.LsEntry> listFiles(String ftpDir) {
        ChannelSftp sftp = null;
        try {
            sftp = pool.borrowObject();
            return sftp.ls(ftpDir);
        } catch (SftpException e) {
            log.error(ERR_MSG, e);
            throw new AppException(e);
        } finally {
            pool.returnObject(sftp);
        }
    }

    /**
     * 移动或重命名文件，目标目录不存在则自动级联创建
     * @param src 源文件，绝对路径
     * @param target 目标文件，绝对路径
     */
    public void move(String src, String target) {
        ChannelSftp sftp = null;
        try {
            sftp = pool.borrowObject();
            SftpUtil.moveAndMkdirs(src, target, sftp);
            log.info("移动文件成功：{} -> {}", src, target);
        } catch (SftpException e) {
            log.error("移动文件失败：{} -> {}", src, target, e);
            throw new AppException(e);
        } finally {
            pool.returnObject(sftp);
        }
    }

}

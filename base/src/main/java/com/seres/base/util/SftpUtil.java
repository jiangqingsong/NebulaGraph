package com.seres.base.util;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SftpUtil {

    /**
     * 移动或重命名文件，目标目录不存在则自动级联创建
     * @param src 源文件，绝对路径
     * @param target 目标文件，绝对路径
     * @param sftp
     */
    public static void moveAndMkdirs(String src, String target, ChannelSftp sftp) throws SftpException {
        int index = target.lastIndexOf("/");
        if(index < 0){  // 移动到当前目录/重命名
            sftp.rename(src, target);
        }else {
            String dir = target.substring(0, index);
            cdAndMkdirs(dir, sftp);
            sftp.rename(src, target);
        }
    }

    /**
     * 进入目录，目录不存在则自动级联创建
     * @param directory
     * @param sftp
     * @throws SftpException
     */
    public static void cdAndMkdirs(String directory, ChannelSftp sftp) throws SftpException{
        try {
            sftp.cd(directory);
        } catch (SftpException e) {
            log.info("目录不存在，再次分别尝试进入父目录：{}", directory);
            String [] dirs = directory.split("/", -1);
            String tempPath = "";
            for(String dir : dirs) {
                if(null== dir || "".equals(dir)) {
                    continue;
                }
                tempPath += "/" + dir;
                syncCdAndMkdir(tempPath, sftp);
            }
        }
    }

    /**
     * 进入目录，目录不存在则自动创建最后一级
     * @param directory
     * @param sftp
     * @throws SftpException
     */
    public static void cdAndMkdir(String directory, ChannelSftp sftp) throws SftpException{
        try {
            sftp.cd(directory);
        } catch (SftpException e) {
            log.error("目录不存在，再次尝试：{}，msg={}", directory, e.getMessage(), e);
            syncCdAndMkdir(directory, sftp);
        }
    }

    /**
     * 同步进入目录，目录不存在则自动创建最后一级（同步方法，多线程安全）
     * @param directory
     * @param sftp
     * @throws SftpException
     */
    private static synchronized void syncCdAndMkdir(String directory, ChannelSftp sftp) throws SftpException{
        try {
            sftp.cd(directory);
        } catch (SftpException e) {
            log.warn("目录不存在，自动创建最后一级文件夹：{}，msg={}", directory, e.getMessage(), e);
            try {
                sftp.mkdir(directory);
            } catch (SftpException e1) {
                log.error("创建目录失败：{}，msg={}", directory, e1.getMessage(), e1);
            }
            sftp.cd(directory);
        }
    }

}

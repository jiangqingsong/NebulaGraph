package com.seres.base.tool.sftp;

import com.seres.base.exception.AppException;
import com.jcraft.jsch.ChannelSftp;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;

@Data
@Slf4j
public class SftpPool {

    private GenericObjectPool<ChannelSftp> pool;

    public SftpPool(SftpFactory factory) {
        this.pool = new GenericObjectPool<>(factory, factory.getProperties().getPool());
    }

    /**
     * 获取一个sftp连接对象
     * @return sftp连接对象
     */
    public ChannelSftp borrowObject() {
        try {
            ChannelSftp ftp = pool.borrowObject();
            return ftp;
        } catch (Exception e) {
            log.error("获取sftp连接失败", e);
            throw new AppException(e);
        }
    }

    /**
     * 归还一个sftp连接对象
     * @param channelSftp sftp连接对象
     */
    public void returnObject(ChannelSftp channelSftp) {
        if (channelSftp!=null) {
            pool.returnObject(channelSftp);
        }
    }

    /**
     * 使对象无效
     * @param channelSftp sftp连接对象
     */
    public void invalidateObject(ChannelSftp channelSftp) {
        if (null == channelSftp) {
            return;
        }
        try {
            pool.invalidateObject(channelSftp);
        } catch (Exception e) {
            log.error("使sftp连接无效失败", e);
            throw new AppException(e);
        }
    }

}
package com.seres.base.tool.sftp;

import com.seres.base.exception.AppException;
import com.jcraft.jsch.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
 
import java.util.Properties;

@Data
@Slf4j
public class SftpFactory extends BasePooledObjectFactory<ChannelSftp> {

    private static final int JSCH_LOG_LEVEL = Logger.ERROR;

    static {
        JSch.setLogger(new Logger() {
            @Override
            public boolean isEnabled(int level) {
                return true;
            }
            @Override
            public void log(int level, String message) {
                if(level >= JSCH_LOG_LEVEL){
                    log.info("{}:{}", level, message);
                }
            }
        });
    }

    private SftpProperties properties;

    public SftpFactory(SftpProperties properties) {
        this.properties = properties;
    }

    /**
     * 创建对象
     * @return
     */
    @Override
    public ChannelSftp create() {
        log.info("创建 channelSftp...");
        Session session = null;
        ChannelSftp channel = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(properties.getUsername(), properties.getHost(), properties.getPort());
            session.setPassword(properties.getPassword());
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);
            session.setServerAliveInterval(10000); // 发送保持连接的心跳间隔
            session.setServerAliveCountMax(1); // 心跳无应答最大次数
            session.setTimeout(600000); // socket timeout 读写超时
            session.connect(); // connection timeout 连接超时
            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect(5000); // 通道连接超时
            return channel;
        } catch (JSchException e) {
            log.error("连接sftp失败", e);
            if(null != channel){
                channel.disconnect();
            }
            if(null != session){
                session.disconnect();
            }
            throw new AppException(e);
        }
    }

    @Override
    public PooledObject<ChannelSftp> wrap(ChannelSftp channelSftp) {
        return new DefaultPooledObject<>(channelSftp);
    }

    /**
     * 销毁对象
     * @param p
     */
    @Override
    public void destroyObject(PooledObject<ChannelSftp> p) {
        log.info("销毁 channelSftp...");
        ChannelSftp channelSftp = p.getObject();
        if(null == channelSftp){
            return;
        }
        channelSftp.disconnect();
        try {
            Session session = channelSftp.getSession();
            if(null != session){
                session.disconnect();
            }
        } catch (JSchException e) {
            log.warn("session断开失败：{}", e.getMessage());
        }
    }

    /**
     * 验证对象是否可用
     * @param p
     * @return
     */
    @Override
    public boolean validateObject(PooledObject<ChannelSftp> p) {
        ChannelSftp channelSftp = p.getObject();
        if(null == channelSftp){
            return false;
        }
        if(!channelSftp.isConnected() || channelSftp.isClosed()){
            return false;
        }
        try {
            channelSftp.pwd(); // 是否真实可用
            return true;
        } catch (SftpException e) {
            log.error("sftp连接不可用", e);
            return false;
        }
    }

}
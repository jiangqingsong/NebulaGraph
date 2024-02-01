package com.seres.base.tool.sftp;

import com.jcraft.jsch.ChannelSftp;
import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
 
@Data

public class SftpProperties {
 
    private String host = "localhost";
    private int port = 22;
    private String username = "root";
    private String password = "root";
    private Pool pool = new Pool();
 
    public static class Pool extends GenericObjectPoolConfig<ChannelSftp> {

    }
 
}
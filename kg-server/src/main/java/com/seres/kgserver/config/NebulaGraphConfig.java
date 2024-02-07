package com.seres.kgserver.config;

import com.vesoft.nebula.client.graph.NebulaPoolConfig;
import com.vesoft.nebula.client.graph.data.HostAddress;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/7 8:55
 */
@Configuration
public class NebulaGraphConfig {
    @Value("${myapp.nebula.hosts}")
    private String hosts;
    @Value("${myapp.nebula.max-conn}")
    private int maxConn;
    @Value("${myapp.nebula.username}")
    private String username;
    @Value("${myapp.nebula.password}")
    private String password;

    @Bean(destroyMethod = "close")
    public NebulaSessionFactory nebulaSessionFactory() {
        List<HostAddress> hostAddresses = new ArrayList<>();
        String[] hostList = hosts.split(",[ ]*");
        for (String host : hostList) {
            String[] hostParts = host.split(":");
            if (hostParts.length != 2 || !hostParts[1].matches("\\d+")) {
                throw new RuntimeException("Invalid host name set for Nebula: " + host);
            }
            hostAddresses.add(new HostAddress(hostParts[0], Integer.parseInt(hostParts[1])));
        }
        NebulaPoolConfig poolConfig = new NebulaPoolConfig();
        poolConfig.setMaxConnSize(maxConn);
        NebulaPool pool = new NebulaPool();
        try {
            pool.init(hostAddresses, poolConfig);
        } catch (UnknownHostException e) {
            throw new RuntimeException("Unknown Nebula hosts");
        }
        return new NebulaSessionFactory(pool, username, password);
    }
}

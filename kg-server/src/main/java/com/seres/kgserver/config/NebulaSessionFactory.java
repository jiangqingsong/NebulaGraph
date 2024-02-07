package com.seres.kgserver.config;

import com.vesoft.nebula.client.graph.exception.AuthFailedException;
import com.vesoft.nebula.client.graph.exception.ClientServerIncompatibleException;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.exception.NotValidConnectionException;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import com.vesoft.nebula.client.graph.net.Session;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/7 8:56
 */
public class NebulaSessionFactory {
    private final NebulaPool pool;
    private final String username;
    private final String password;

    public NebulaSessionFactory(NebulaPool pool, String username, String password) {
        this.pool = pool;
        this.username = username;
        this.password = password;
    }

    public Session getSession() {
        try {
            return pool.getSession(username, password, false);
        } catch (NotValidConnectionException | IOErrorException | AuthFailedException |
                 ClientServerIncompatibleException e) {
            throw new RuntimeException("Nebula session exception", e);
        }
    }

    public void close() {
        pool.close();
    }
}

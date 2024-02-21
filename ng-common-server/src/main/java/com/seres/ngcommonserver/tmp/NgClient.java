package com.seres.ngcommonserver.tmp;

import com.vesoft.nebula.client.graph.NebulaPoolConfig;
import com.vesoft.nebula.client.graph.data.HostAddress;
import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.exception.AuthFailedException;
import com.vesoft.nebula.client.graph.exception.ClientServerIncompatibleException;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.exception.NotValidConnectionException;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import com.vesoft.nebula.client.graph.net.Session;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/6 10:48
 */
public class NgClient {
    public static void main(String[] args) throws UnknownHostException, IOErrorException, AuthFailedException, ClientServerIncompatibleException, NotValidConnectionException {
        NebulaPoolConfig nebulaPoolConfig = new NebulaPoolConfig();
        nebulaPoolConfig.setMaxConnSize(10);
        List<HostAddress> addresses = Arrays.asList(new HostAddress("10.36.253.10", 9669));
        NebulaPool pool = new NebulaPool();
        pool.init(addresses, nebulaPoolConfig);
        Session session = pool.getSession("root", "nebula", false);
        ResultSet result = session.execute("use demo_basketballplayer;MATCH (v) RETURN v");

        System.out.println(result.isSucceeded());

        String json = session.executeJson("use demo_basketballplayer;MATCH (a) RETURN  a limit 2;");
        System.out.println(json);

        String json2 = session.executeJson("use demo_basketballplayer;FETCH PROP ON * \"player100\", \"player106\", \"team200\" YIELD vertex AS v;");
        System.out.println(json2);

        String json3 = session.executeJson("use demo_basketballplayer;MATCH (a)-[v]-(b) RETURN a,v,b;");
        System.out.println(json3);


    }
}

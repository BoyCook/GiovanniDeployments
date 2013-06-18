package org.cccs.deployments.cache;

import org.cccs.deployments.domain.DBServer;
import org.cccs.deployments.domain.Server;

import java.util.HashSet;
import java.util.Set;

/**
 * User: boycook
 * Date: Aug 3, 2010
 * Time: 10:18:11 PM
 */
public class ServerCache {

    private static Set<Server> servers;
    private static Set<DBServer> dbs;

    static {
        servers = new HashSet<Server>();
        dbs = new HashSet<DBServer>();
    }

    public static synchronized Set<Server> getServers() {
        return servers;
    }

    public static synchronized Set<DBServer> getDbs() {
        return dbs;
    }

    public static synchronized void setServers(Set<Server> servers) {
        ServerCache.servers = servers;
    }

    public static synchronized void setDbs(Set<DBServer> dbs) {
        ServerCache.dbs = dbs;
    }

    public static Server getServer(String url) {
        for (Server s : getServers()) {
            if (s.URL.equalsIgnoreCase(url)) {
                return s;
            }
        }
        return null;
    }

    public static DBServer getDb(String url, String port, String schema) {
        for (DBServer db : getDbs()) {
            if (db.URL.equalsIgnoreCase(url) && db.port.equalsIgnoreCase(port) && db.schema.equalsIgnoreCase(schema)) {
                return db;
            }
        }
        return null;
    }

    public static Server getServerByKey(String key) {
        for (Server server : getServers()) {
            if (server.URL.equalsIgnoreCase(key)) {
                return server;
            }
        }
        return null;
    }

    public static Server getDbByKey(String key) {
        for (DBServer db : getDbs()) {
            if (db.URL.equalsIgnoreCase(key)) {
                return db;
            }
        }
        return null;
    }
}

package org.cccs.deployments.cache;

import org.cccs.deployments.domain.DBServer;
import org.cccs.deployments.domain.Server;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static org.cccs.deployments.cache.ServerCache.*;
import static org.cccs.deployments.utils.IOUtils.readFile;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * User: boycook
 * Date: Aug 12, 2010
 * Time: 7:41:57 PM
 */
public class TestServerCache {

    @SuppressWarnings({"unchecked"})
    @BeforeClass
    public static void setup() throws ClassNotFoundException, IOException {
        ServerCache.setDbs((Set<DBServer>) readFile("src/test/resources/dbs.data"));
        ServerCache.setServers((Set<Server>) readFile("src/test/resources/servers.data"));
    }

    @Test
    public void getServersShouldNotBeNull() {
        assertNotNull(getServers());
    }

    @Test
    public void getDbsShouldNotBeNull() {
        assertNotNull(getDbs());
    }

    @Test
    public void getServerByUrlShouldWork() {
        assertNotNull(getServer("localhost"));
    }

    @Test
    public void getServerByUrlShouldWorkReturnNullIfServerDoesNotExist() {
        assertNull(getServer("ThisDoesNotExist"));
    }

    @Test
    public void getServerByKeyShouldWorkReturnNullIfServerDoesNotExist() {
        assertNull(getServerByKey("ThisDoesNotExist"));
    }

    @Test
    public void getDbByUrlShouldWork() {
        assertNotNull(getDb("localhost", "1521", "ADMIN"));
    }

    @Test
    public void getDbByUrlShouldWorkReturnNullIfServerDoesNotExist() {
        assertNull(getDb("ThisDoesNotExist", "FOO", "BAR"));
    }

    @Test
    public void getDbByKeyShouldWorkReturnNullIfServerDoesNotExist() {
        assertNull(getDbByKey("ThisDoesNotExist"));
    }
}

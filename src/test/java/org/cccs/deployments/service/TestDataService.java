package org.cccs.deployments.service;

import org.cccs.deployments.cache.IndexCache;
import org.cccs.deployments.cache.ProjectCache;
import org.cccs.deployments.cache.ServerCache;
import org.cccs.deployments.domain.Attribute;
import org.cccs.deployments.domain.Stage;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

/**
 * User: boycook
 * Date: Aug 11, 2010
 * Time: 6:29:17 PM
 */
public class TestDataService {

    DataService data = new DataService();

    @Test
    public void serviceShouldLoadFromDiskIfCachesAreEmpty() {
        nullAndLoad();

        assertNotNull(IndexCache.getIndexes());
        assertNotNull(ProjectCache.getProjects());
        assertNotNull(ServerCache.getDbs());
        assertNotNull(ServerCache.getServers());
    }

    private void nullAndLoad() {
        IndexCache.setIndexes(null);
        ProjectCache.setProjects(null);
        ServerCache.setDbs(null);
        ServerCache.setServers(null);

        assertNull(IndexCache.getIndexes());
        assertNull(ProjectCache.getProjects());
        assertNull(ServerCache.getDbs());
        assertNull(ServerCache.getServers());

        data.loadFromDisk();
    }

    @Test
    public void tnsEntryParsingShouldWork() {
        final String tns = "  (DESCRIPTION =\n" +
                "    (ADDRESS_LIST =\n" +
                "      (ADDRESS = (PROTOCOL = TCP)(HOST = XXX)(PORT = 1521))\n" +
                "    )\n" +
                "    (CONNECT_DATA =\n" +
                "      (SERVICE_NAME = ADMIN)\n" +
                "    )\n" +
                "  )";

        assertEquals("XXX", data.getTnsValue("HOST", tns));
        assertEquals("1521", data.getTnsValue("PORT", tns));
        assertEquals("ADMIN", data.getTnsValue("SERVICE_NAME", tns));
        assertEquals("", data.getTnsValue("SERVICE_NAME", null));
    }

    @Test
    public void extractingDBPropertiesShouldWork() {
        String tns = "  (DESCRIPTION =\n" +
                "    (ADDRESS_LIST =\n" +
                "      (ADDRESS = (PROTOCOL = TCP)(HOST = XXX)(PORT = 1521))\n" +
                "    )\n" +
                "    (CONNECT_DATA =\n" +
                "      (SERVICE_NAME = ADMIN)\n" +
                "    )\n" +
                "  )";
        Stage s = new Stage();
        s.attributes.put("tnsEntry", new Attribute("tnsEntry", tns));
        data.extractDBProperties(s);

        assertFalse(s.attributes.containsKey("tnsEntry"));

        assertTrue(s.attributes.containsKey("dbHost"));
        assertTrue(s.attributes.containsKey("dbPort"));
        assertTrue(s.attributes.containsKey("dbSid"));

        assertEquals("XXX", s.attributes.get("dbHost").value);
        assertEquals("1521", s.attributes.get("dbPort").value);
        assertEquals("ADMIN", s.attributes.get("dbSid").value);
    }

    @Test
    public void extractingDBPropertiesShouldNotFailWithNoTNS() {
        Stage s = new Stage();
        final int beforeCnt = s.attributes.size();
        data.extractDBProperties(s);
        final int afterCnt = s.attributes.size();
        assertEquals(beforeCnt, afterCnt);
    }
}

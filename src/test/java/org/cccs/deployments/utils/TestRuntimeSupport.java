package org.cccs.deployments.utils;

import org.cccs.deployments.domain.Attribute;
import org.cccs.deployments.domain.Stage;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.cccs.deployments.utils.RuntimeSupport.convertMessage;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * User: boycook
 * Date: Aug 12, 2010
 * Time: 6:45:50 PM
 */
public class TestRuntimeSupport {

    Stage stage;

    @Before
    public void before() {
        stage = new Stage();
        stage.attributes.put("dbHost", new Attribute("dbHost", "host"));
        stage.attributes.put("dbPort", new Attribute("dbPort", "port"));
        stage.attributes.put("dbSid", new Attribute("dbSid", "sid"));
    }

    @Test
    public void messageConversionShouldWorkForSID() {
        stage.dbFailMessage = "ORA-12514: sid error";
        convertMessage(stage);
        assertEquals("Check SID - [sid]", stage.dbFailMessage);
        assertTrue(stage.attributes.get("dbHost").valid);
    }

    @Test
    public void messageConversionShouldWorkForUserPass() {
        stage.dbFailMessage = "ORA-01017: user error";
        convertMessage(stage);
        assertEquals("Check username/password", stage.dbFailMessage);
        assertTrue(stage.attributes.get("dbHost").valid);
    }

    @Test
    public void messageConversionShouldWorkForPort() {
        stage.dbFailMessage = "Io exception: The Network Adapter could not establish the connection";
        convertMessage(stage);
        assertEquals("Check port - [port]", stage.dbFailMessage);
        assertTrue(stage.attributes.get("dbHost").valid);
    }

    @Test
    public void messageConversionShouldWorkForHost() {
        stage.dbFailMessage = "Io exception: Unknown host specified";
        convertMessage(stage);
        assertEquals("Check host - [host]", stage.dbFailMessage);
        assertFalse(stage.attributes.get("dbHost").valid);
    }

    @Ignore
    @Test
    public void propertiesAreLoaded() {
        assertThat(RuntimeSupport.SERVERS_FILE, is(equalTo("data/servers.data")));
        assertThat(RuntimeSupport.DBS_FILE, is(equalTo("data/dbs.data")));
        assertThat(RuntimeSupport.PROJECTS_FILE, is(equalTo("data/projecttest.data")));
        assertThat(RuntimeSupport.INDEX_FILE, is(equalTo("data/svntest.data")));
    }
}

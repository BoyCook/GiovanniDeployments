package org.cccs.deployments.web;

import org.junit.Ignore;
import org.junit.Test;

/**
 * User: boycook
 * Date: Aug 12, 2010
 * Time: 7:23:33 PM
 */
public class TestServerController extends JettyIntegrationTestEnvironment {

    @Test
    public void getAllServersShouldWork() {
        httpGet(serviceBaseURL + "servers");
    }

    @Test
    public void getAllDBShouldWork() {
        httpGet(serviceBaseURL + "servers/db");
    }

    @Test
    public void getAllAppServerShouldWork() {
        httpGet(serviceBaseURL + "servers/server");
    }

    @Test
    public void getDbShouldWork() {
        httpGet(serviceBaseURL + "servers/db/XXXX/");
    }

    @Test
    public void getAppShouldWork() {
        httpGet(serviceBaseURL + "servers/server/XXX/");
    }

    @Ignore
    @Test
    public void getInvalidTypeShouldFail() {
        httpGet(serviceBaseURL + "servers/foo");
    }

    @Ignore
    @Test
    public void getInvalidIdOfInvalidTypeShouldFail() {
        httpGet(serviceBaseURL + "servers/foo/bar");
    }
}

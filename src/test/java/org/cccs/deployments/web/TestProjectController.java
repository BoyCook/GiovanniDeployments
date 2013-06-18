package org.cccs.deployments.web;

import org.junit.Test;

/**
 * User: Craig Cook
 * Date: Jun 10, 2010
 * Time: 7:51:47 PM
 */
@SuppressWarnings({"unchecked"})
public class TestProjectController extends JettyIntegrationTestEnvironment {

    @Test
    public void getProjectsShouldWork() {
        httpGet(serviceBaseURL + "projects");
    }

    @Test
    public void getProjectsSortedShouldWork() {
        httpGet(serviceBaseURL + "projects?sorted=true");
    }

    @Test
    public void getProjectShouldWork() {
        httpGet(serviceBaseURL + "projects/org.cccs.ber:ber-war/");
    }

    @Test
    public void getProjectStageShouldWork() {
        httpGet(serviceBaseURL + "projects/org.cccs.ber:ber-war/production");
    }
}

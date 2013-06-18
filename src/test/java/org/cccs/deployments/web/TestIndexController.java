package org.cccs.deployments.web;

import org.junit.Test;

/**
 * User: boycook
 * Date: Aug 12, 2010
 * Time: 7:27:35 PM
 */
public class TestIndexController extends JettyIntegrationTestEnvironment {

    @Test
    public void getAllIndexesShouldWork() {
        httpGet(serviceBaseURL + "indexes");
    }
}

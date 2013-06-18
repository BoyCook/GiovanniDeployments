package org.cccs.deployments.web;

import org.junit.Ignore;
import org.junit.Test;

/**
 * User: boycook
 * Date: Aug 17, 2010
 * Time: 9:27:02 PM
 */
public class TestSiteController extends JettyIntegrationTestEnvironment {

    @Ignore
    @Test
    public void getWadlShouldWork(){
        httpGet(serviceBaseURL + "wadl");
    }
}

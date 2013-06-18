package org.cccs.deployments.provider;

import org.junit.Ignore;
import org.junit.Test;

import static org.cccs.deployments.provider.HttpProvider.httpCheck;
import static org.cccs.deployments.provider.HttpProvider.httpGet;
import static org.cccs.deployments.provider.HttpProvider.httpHostCheck;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: boycook
 * Date: Aug 6, 2010
 * Time: 1:31:47 PM
 */
public class HttpProviderITCase {

    @Test
    public void invalidUrlShouldReturnFalse() {
        assertFalse(httpCheck("InvalidURL"));
    }

    @Test
    public void wrongUrlShouldReturnFalse() {
        assertFalse(httpCheck("http://zxzcsdftwerwerwerwerwvvdas"));
    }

    @Ignore
    @Test
    public void correctUrlShouldReturnFalse() {
        assertTrue(httpCheck("http://www.google.co.uk"));
    }

    @Test
    public void invalidHostShouldReturnFalse() {
        assertFalse(httpHostCheck("********"));
    }

    @Test
    public void wrongHostShouldReturnFalse() {
        assertFalse(httpHostCheck("http://zxzcsdftwerwerwerwerwvvdas"));
    }

    @Ignore
    @Test
    public void correctHostShouldReturnFalse() {
        assertTrue(httpHostCheck("google.co.uk"));
    }

    @Ignore
    @Test
    public void httpGetShouldReturnData() {
        String url = "http://localhost/fisheye/api/rest/query?rep=XXX&query=select%20revisions%20from%20dir%20/%20where%20path%20like%20%22deploy.rb%22%20and%20is%20head%20and%20not%20is%20dead%20group%20by%20changeset%20return%20path,%20revision,%20author,%20date,%20csid";
        System.out.println("Response: " + httpGet(url));
    }
}

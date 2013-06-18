package org.cccs.deployments.utils;

import org.cccs.deployments.domain.Attribute;
import org.cccs.deployments.domain.Project;
import org.cccs.deployments.domain.Stage;
import org.junit.Test;

import static org.cccs.deployments.utils.AttributeExtractor.getDbUrl;
import static org.cccs.deployments.utils.AttributeExtractor.getUrl;
import static org.cccs.deployments.utils.AttributeExtractor.getValue;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: boycook
 * Date: Aug 10, 2010
 * Time: 3:01:18 PM
 */
public class TestAttributeExtractor {

    @Test
    public void getDbUrlShouldNeverReturnNull() {
        Stage s = new Stage();
        assertNotNull(getDbUrl(s.attributes));
    }

    @Test
    public void getDbUrlShouldWork() {
        Stage s = new Stage();
        s.attributes.put("dbHost", new Attribute("dbHost", "127.0.0.1"));
        s.attributes.put("dbPort", new Attribute("dbPort", "5321"));
        s.attributes.put("dbSid", new Attribute("dbSid", "DB_SID"));
        assertNotNull(getDbUrl(s.attributes));
        assertEquals("127.0.0.1:5321/DB_SID", getDbUrl(s.attributes));
    }

    @Test
    public void getUrlShouldNeverReturnNull() {
        Stage s = new Stage();
        s.project = new Project();
        assertNotNull(getUrl(s));
        assertEquals(":/", getUrl(s));
    }

    @Test
    public void getUrlShouldWork() {
        Project p = new Project();
        Stage s = new Stage();
        s.project = p;
        p.attributes.put("application", new Attribute("application", "context"));
        p.attributes.put("tomcatHttpPort", new Attribute("tomcatHttpPort", "8080"));
        s.attributes.put("app", new Attribute("app", "127.0.0.1"));

        assertNotNull(getUrl(s));
        assertEquals("127.0.0.1:8080/context", getUrl(s));
    }

    @Test
    public void getUrlWithContextShouldWork() {
        Project p = new Project();
        Stage s = new Stage();
        s.project = p;
        p.attributes.put("application", new Attribute("application", "context"));
        p.attributes.put("tomcatHttpPort", new Attribute("tomcatHttpPort", "8080"));
        p.attributes.put("contextRoot", new Attribute("contextRoot", "newcontext"));
        s.attributes.put("app", new Attribute("app", "127.0.0.1"));

        assertNotNull(getUrl(s));
        assertEquals("127.0.0.1:8080/newcontext", getUrl(s));
    }

    @Test
    public void getNonExistentKeyShouldNeverReturnNull() {
        Stage s = new Stage();
        assertNotNull(getValue("foobar", s.attributes));
        assertEquals("", getValue("foobar", s.attributes));
    }
}

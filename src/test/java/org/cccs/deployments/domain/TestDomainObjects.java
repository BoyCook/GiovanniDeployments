package org.cccs.deployments.domain;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: Aug 8, 2010
 * Time: 7:50:32 PM
 */
public class TestDomainObjects {

    @Test
    public void testProjectGen() {
        Project p = new Project();
        p.groupId = "org.cccs";
        p.artifactId = "deployments";
        assertEquals("org.cccs:deployments", p.getId());
        assertEquals("org-cccs-deployments", p.getFriendlyId());
    }

    @Test
    public void testServerGen() {
        Server s = new Server("localhost");
        assertEquals("", s.getId());
        assertEquals("", s.getFriendlyId());
    }

    @Test
    public void testArtefactPath() {
        Project p = new Project();
        p.groupId = "org.cccs";
        p.artifactId = "deployments";
        p.version = "6.6-SNAPSHOT";
        p.toString();
        assertEquals("/com/bt/tools/deployments/6.6-SNAPSHOT", p.getArtefactPath());
    }

    @Test
    public void testStringSplit() {
        String text = "HelloWorld";
        String[] values = text.split("[A-Z]");
        String temp = text;
        String split = "";

        for (String v : values) {
            if (text.indexOf(v) == 0) {
                split = split + v;
                temp = temp.substring(v.length());
            } else {
                String value = temp.substring(0, 1) + v;
                split = split + " " + value;
                temp = temp.substring(value.length());
            }
        }

        System.out.println(split);
    }

    @Test
    public void testSVNIndex() {
        SVNIndex index = new SVNIndex();
        index.path = "foo";
        index.revision = "foo";
        index.author = "foo";
        index.date = "foo";

        System.out.println(index.toString());
    }

    @Test
    public void serverConstructorShouldWork() {
        Server s = new Server("foo");
        assertThat(s.URL, is(equalTo("foo")));
    }

    @Test
    public void dbServerConstructorShouldWork() {
        DBServer db = new DBServer("foo", "1234", "bar");
        assertThat(db.URL, is(equalTo("foo")));
        assertThat(db.port, is(equalTo("1234")));
        assertThat(db.schema, is(equalTo("bar")));
    }

    @Test
    public void testHashSetEqualsForStage() {
        Set<Stage> stages = new HashSet<Stage>();

        Project project = new Project();
        project.artifactId = "ber";
        project.groupId = "org-cccs";

        Stage s1 = new Stage();
        s1.name = "staging";
        s1.project = project;
        stages.add(s1);

        Stage s2 = new Stage();
        s2.name = "staging";
        s2.project = project;
        stages.add(s2);

        Stage s3 = new Stage();
        s3.name = "development";
        s3.project = project;
        stages.add(s3);

        Stage s4 = new Stage();
        s4.name = "production";
        s4.project = project;
        stages.add(s4);

        assertThat(stages.size(), is(equalTo(3)));
    }

    @Test
    public void testSVNIndexEquals() {
        SVNIndex i1 = new SVNIndex();
        i1.path = "/foo";

        SVNIndex i2 = new SVNIndex();
        i2.path = "/foo";

        SVNIndex i3 = new SVNIndex();
        i3.path = "/bar";

        SVNIndex i4 = new SVNIndex();
        i4.path = null;

        assertEquals(i1, i2);
        assertFalse(i1.equals(i3));
        assertFalse(i1.equals(i4));
        assertFalse(i1.equals(null));
    }
}

package org.cccs.deployments.provider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;

import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * User: boycook
 * Date: Aug 2, 2010
 * Time: 7:36:17 PM
 */
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SubversionProviderITCase {

    @Autowired
    private SubversionProvider provider;
    private static final String NEXUS_PATH = "/nexus/trunk/config/deploy.rb";
    private static final String DEPLOYMENTS_PATH = "/deployments-dashboard/trunk/config/deploy.rb";

    @Test
    public void readFileShouldWork() throws SVNException {
        provider.printFile(NEXUS_PATH);
    }

    @Test
    public void checkPathShouldWork() throws SVNException {
        assertTrue(provider.checkPath(NEXUS_PATH));
        assertTrue(provider.checkPath(DEPLOYMENTS_PATH));
    }

    @Test
    public void findFilesShouldWork() throws SVNException {
        Set<String> files = new HashSet<String>();
        provider.find("/nexus/trunk", ".rb", SVNNodeKind.FILE, files, true);
        assertThat(files.size(), is(greaterThan(2)));
    }

    @Test
    public void findDirectoryShouldWork() throws SVNException {
        Set<String> files = new HashSet<String>();
        provider.find("/nexus/trunk", "/config/deploy/stages", SVNNodeKind.DIR, files, true);
        assertEquals(1, files.size());
    }

    @Test
    public void getRevisionShouldWork() throws SVNException {
        String[] urls = new String[]{
                "/nexus/trunk",
                "/nexus/trunk/config/deploy.rb"
        };

        for (String url: urls) {
            System.out.println(format("[%s] is at revision [%d]",  url, provider.getRevision(url)));
        }
    }
}

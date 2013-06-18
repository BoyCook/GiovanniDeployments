package org.cccs.deployments.cache;

import org.cccs.deployments.domain.Project;
import org.cccs.deployments.domain.SVNIndex;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static org.cccs.deployments.cache.IndexCache.*;
import static org.cccs.deployments.utils.IOUtils.readFile;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * User: boycook
 * Date: Aug 12, 2010
 * Time: 7:39:58 PM
 */
public class TestIndexCache {

    @SuppressWarnings({"unchecked"})
    @BeforeClass
    public static void setup() throws ClassNotFoundException, IOException {
        ProjectCache.setProjects((Map<String, Project>) readFile("src/test/resources/projects.data"));
        IndexCache.setIndexes((Set<SVNIndex>) readFile("src/test/resources/svnindexs.data"));
    }

    @Test
    public void getIndexesShouldNotBeNull() {
        final Set<SVNIndex> indexes = getIndexes();
        assertNotNull(indexes);

        for (SVNIndex index: indexes){
            System.out.println("Index: " + index.path);
        }
    }

    @Test
    public void getIndexByProjectShouldWork() {
        final Project p = ProjectCache.getProjects().get("org.cccs.ber:ber-war");
        assertNotNull(getIndexByProject(p));
    }

    @Test
    public void getIndexByProjectShouldReturnNullForInvalidPath() {
        final Project p = new Project();
        p.path = "ThisIsMadeUp";
        assertNull(getIndexByProject(p));
    }

    @Test
    public void getIndexByKeyShouldWork() {
        assertNotNull(getIndexByPath("/nexus/trunk"));
    }

    @Test
    public void getInvalidIndexShouldReturnNull() {
        assertNull(getIndexByPath("/ThisIsFooBar"));
    }
}

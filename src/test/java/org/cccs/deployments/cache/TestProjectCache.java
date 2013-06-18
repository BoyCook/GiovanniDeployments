package org.cccs.deployments.cache;

import org.cccs.deployments.domain.Project;
import org.cccs.deployments.domain.SVNIndex;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static org.cccs.deployments.cache.ProjectCache.getProjectByIndex;
import static org.cccs.deployments.cache.ProjectCache.getProjects;
import static org.cccs.deployments.utils.IOUtils.readFile;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * User: boycook
 * Date: Aug 10, 2010
 * Time: 4:15:43 PM
 */
public class TestProjectCache {

    @SuppressWarnings({"unchecked"})
    @BeforeClass
    public static void setup() throws ClassNotFoundException, IOException {
        ProjectCache.setProjects((Map<String, Project>) readFile("src/test/resources/projects.data"));
        IndexCache.setIndexes((Set<SVNIndex>) readFile("src/test/resources/svnindexs.data"));
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void getSortedListShouldReturnSortedList() throws ClassNotFoundException, IOException {        
        for (String key: ProjectCache.getSortedProjects().keySet()) {
            Project p = ProjectCache.getSortedProjects().get(key);
            System.out.println(p.artifactId);
        }
    }

    @Test
    public void getProjectsShouldNotBeNull() {
        assertNotNull(getProjects());
    }

    @Test
    public void getProjectByIndexShouldWork() {
        final SVNIndex i = IndexCache.getIndexByPath("/nexus/trunk");
        assertNotNull(getProjectByIndex(i));
    }

    @Test
    public void getProjectByIndexShouldReturnNullForInvalidPath() {
        final SVNIndex i = new SVNIndex();
        i.path = "ThisIsMadeUp";
        assertNull(getProjectByIndex(i));
    }
}

package org.cccs.deployments.service;

import org.cccs.deployments.cache.IndexCache;
import org.cccs.deployments.cache.ProjectCache;
import org.cccs.deployments.utils.RuntimeSupport;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * User: boycook
 * Date: Aug 3, 2010
 * Time: 12:49:35 PM
 */
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Ignore("Just for demo purposes")
public class DataServiceITCase {

    @Autowired
    private DataService data;

    @BeforeClass
    public static void setup() {
        RuntimeSupport.DBS_FILE = "data/dbs.data";
        RuntimeSupport.INDEX_FILE = "data/indexes.data";
        RuntimeSupport.PROJECTS_FILE = "data/projects.data";
        RuntimeSupport.SERVERS_FILE = "data/servers.data";
    }

    @Ignore //This takes too long
    @Test
    public void loadIndexesShouldWork() {
        data.loadIndexes();
        assertEquals(1, IndexCache.getIndexes().size());
    }

    @Ignore //This takes too long
    @Test
    public void loadProjectDataShouldWork() {
        data.loadProjectData();
        assertEquals(1, ProjectCache.getProjects().size());
    }

    @Test
    public void loadProjectShouldWork() {
        assertNotNull(data.loadProject("/deployments-dashboard/trunk"));
    }
}

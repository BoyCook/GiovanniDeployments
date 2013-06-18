package org.cccs.deployments.integration.oxm;


import org.cccs.deployments.domain.Project
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import static org.cccs.deployments.utils.IOUtils.readFile

/**
 * User: Craig Cook
 * Date: May 30, 2010
 * Time: 11:23:37 AM
 */
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
class TestProjectConverter extends BaseConverterTest {

    private static Map<String, Project> projects;

    String expectedList = """
        <resources>

        </resources>
    """

    String expectedDD = """
        <project>

        </project>
    """

    @BeforeClass
    public static void setup() {
        projects = (Map<String, Project>) readFile("src/test/resources/projects.data");
    }

    @Test
    public void marshalingAllProjectsShouldWork()  {
        List<Project> data = new ArrayList<Project>(projects.values());
        marshal(data);
    }

    @Test
    public void marshalingListOfProjectsShouldWork()  {
        List<Project> data = new ArrayList<Project>();
        data.add(projects.get("org.cccs:deployments-dashboard"))
        String marshalled = marshal(data);
        println marshalled;
        assertXMLEqual("Comparing marshalled xml to expected xml", expectedList, marshalled);
    }

    @Test
    public void marshalingAProjectShouldWork()  {
        String marshalled = marshal(projects.get("org.cccs:deployments-dashboard"));
        println marshalled;
        assertXMLEqual("Comparing marshalled xml to expected xml", expectedDD, marshalled);
    }
}

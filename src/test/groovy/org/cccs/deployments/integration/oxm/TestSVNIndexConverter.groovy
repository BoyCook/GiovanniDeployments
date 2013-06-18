package org.cccs.deployments.integration.oxm;

import org.cccs.deployments.domain.SVNIndex;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.cccs.deployments.utils.RuntimeSupport.readFileToString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: 10/10/2011
 * Time: 16:57
 */
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
class TestSVNIndexConverter extends BaseConverterTest {

    private String indexXML;

    @Before
    public void setup() throws IOException {
        indexXML = readFileToString("/indexes.xml");
    }

    @Test
    public void unmarshallingListOfIndexesShouldWork() {
        //TODO: assertions
        Collection<SVNIndex> indexes = (Collection<SVNIndex>) unmarshal(indexXML);
        assertThat(indexes.size(), is(equalTo(431)));
    }
}

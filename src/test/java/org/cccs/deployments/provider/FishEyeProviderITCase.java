package org.cccs.deployments.provider;

import org.cccs.deployments.domain.SVNIndex;
import org.cccs.deployments.integration.oxm.Marshal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.OrderingComparisons.greaterThan;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: Aug 5, 2010
 * Time: 5:46:04 PM
 */
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class FishEyeProviderITCase {

    @Autowired
    private XStreamMarshaller marshaller;
    private FishEyeIndexProvider indexProvider;

    @Before
    public void setup() {
        indexProvider = new FishEyeIndexProvider();
        indexProvider.setMarshal(new Marshal(marshaller));
    }

    @Test
    public void queryShouldWork() {
        final Set<SVNIndex> indexes = indexProvider.getIndexes();
        for (SVNIndex index : indexes) {
            System.out.println(index.toString());
        }
        assertThat(indexes.size(), is(greaterThan(40)));
    }
}

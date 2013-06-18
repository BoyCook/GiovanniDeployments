package org.cccs.deployments.provider;

import org.cccs.deployments.domain.SVNIndex;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Set;

import static junit.framework.Assert.assertEquals;

/**
 * User: boycook
 * Date: Aug 16, 2010
 * Time: 2:59:11 PM
 */
@Ignore("Just for demo purposes")
public class SubversionIndexProviderITCase {

    SubversionIndexProvider indexProvider = new SubversionIndexProvider();

    @Before
    public void before() {
        indexProvider.scan = "/deployments-dashboard/trunk";
    }

    @Test
    public void queryShouldWork() {
        final Set<SVNIndex> indexes = indexProvider.getIndexes();
        for (SVNIndex index : indexes) {
            System.out.println(index.toString());
        }
        assertEquals(1, indexes.size());
    }
}

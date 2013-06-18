package org.cccs.deployments.provider;

import org.cccs.deployments.utils.RuntimeSupport;
import org.junit.Test;

/**
 * User: boycook
 * Date: Aug 16, 2010
 * Time: 3:16:36 PM
 */
public class TestSubversionIndexProvider {

    @Test
    public void constructProviderWithAnyUrlShouldWork() {
        String url = RuntimeSupport.SVN_URL;
        RuntimeSupport.SVN_URL = "http://ThisIsWrong";
        final SubversionIndexProvider indexProvider = new SubversionIndexProvider();
        indexProvider.setQuery("FooBar");
        RuntimeSupport.SVN_URL = url;
    }

    @Test
    public void constructProviderWithQueryShouldWork() {
        String url = RuntimeSupport.SVN_URL;
        RuntimeSupport.SVN_URL = "http://ThisIsWrong";
        new SubversionIndexProvider("SomeParam");
        RuntimeSupport.SVN_URL = url;
    }
}

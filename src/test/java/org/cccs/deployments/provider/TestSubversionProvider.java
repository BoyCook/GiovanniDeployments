package org.cccs.deployments.provider;

import org.junit.Test;
import org.tmatesoft.svn.core.SVNException;

/**
 * User: boycook
 * Date: Aug 13, 2010
 * Time: 10:48:22 PM
 */
public class TestSubversionProvider {

    @Test(expected = RuntimeException.class)
    public void usingInvalidUrlShouldThrowRuntimeException() throws SVNException {
        new SubversionProvider("foobar", "", "");
    }

    @Test
    public void usingInvalidCredentialsShouldWork() throws SVNException {
        new SubversionProvider("http://craigcook.co.uk", "foo", "bar");
    }
}

package org.cccs.deployments.provider;

import org.cccs.deployments.domain.SVNIndex;

import java.util.Set;

/**
 * User: boycook
 * Date: Aug 13, 2010
 * Time: 9:40:58 PM
 */
public interface IndexProvider {
    public Set<SVNIndex> getIndexes();
    public void setQuery(String query);
}

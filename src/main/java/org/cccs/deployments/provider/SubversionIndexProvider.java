package org.cccs.deployments.provider;

import org.cccs.deployments.domain.SVNIndex;
import org.cccs.deployments.utils.RuntimeSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;

import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: Aug 13, 2010
 * Time: 9:59:39 PM
 */
public class SubversionIndexProvider implements IndexProvider {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private SubversionProvider provider;
    public String scan = "/";
    public String query = "/config/deploy/stages";

    public SubversionIndexProvider() {
        try {
            provider = new SubversionProvider(RuntimeSupport.SVN_URL, RuntimeSupport.SVN_USERNAME, RuntimeSupport.SVN_PASSWORD);
        } catch (SVNException e) {
            log.error(format("There was an error connecting to the SVN repository [%s]", RuntimeSupport.SVN_URL));
            e.printStackTrace();
        }
    }

    public SubversionIndexProvider(String query) {
        this();
        this.query = query;
    }

    @Override
    public Set<SVNIndex> getIndexes() {
        return convert(findInSVN());
    }

    @Override
    public void setQuery(String query) {
        this.query = query;
    }

    private Set<String> findInSVN() {
        Set<String> files = new HashSet<String>();
        try {
            provider.find(scan, query, SVNNodeKind.DIR, files, true);
        } catch (SVNException e) {
            log.error("There was an error searching SVN");
            e.printStackTrace();
        }
        return files;
    }

    protected Set<SVNIndex> convert(Set<String> files) {
        Set<SVNIndex> indexes = new HashSet<SVNIndex>();
        for (String file : files) {
            SVNIndex index = new SVNIndex();
            index.path = file;
            indexes.add(index);
        }
        return indexes;
    }
}

package org.cccs.deployments.cache;

import org.cccs.deployments.domain.Project;
import org.cccs.deployments.domain.SVNIndex;
import org.cccs.deployments.domain.Server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: boycook
 * Date: Aug 2, 2010
 * Time: 6:16:07 PM
 */
public class IndexCache {

    private static Set<SVNIndex> indexes;

    static {
        indexes = new HashSet<SVNIndex>();
    }

    public static SVNIndex getIndexByProject(Project project) {
        List<SVNIndex> tempIndexes = new ArrayList<SVNIndex>(IndexCache.getIndexes());
        for (SVNIndex index : tempIndexes) {
            if (index.path.equalsIgnoreCase(project.path)) {
                return index;
            }
        }
        return null;
    }

    public static synchronized Set<SVNIndex> getIndexes() {
        return IndexCache.indexes;
    }

    public static SVNIndex getIndexByPath(String key) {
        for (SVNIndex index : getIndexes()) {
            if (index.path.equalsIgnoreCase(key)) {
                return index;
            }
        }
        return null;
    }

    public static synchronized void setIndexes(Set<SVNIndex> indexes) {
        IndexCache.indexes = indexes;
    }
}

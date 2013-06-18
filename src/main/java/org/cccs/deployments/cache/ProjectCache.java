package org.cccs.deployments.cache;

import org.cccs.deployments.domain.Project;
import org.cccs.deployments.domain.SVNIndex;

import java.util.*;

/**
 * User: boycook
 * Date: Aug 3, 2010
 * Time: 12:18:17 PM
 */
public class ProjectCache {

    private static Map<String, Project> projects;

    static {
        projects = new HashMap<String, Project>();
    }

    public static Project getProjectByIndex(SVNIndex index) {
        Map<String, Project> tempProjects = new HashMap<String, Project>(getProjects());
        for (String key : tempProjects.keySet()) {
            Project p = tempProjects.get(key);

            if (p.path.equalsIgnoreCase(index.path)) {
                return p;
            }
        }
        return null;
    }

    public static synchronized Map<String, Project> getProjects() {
        return ProjectCache.projects;
    }

    @SuppressWarnings({"unchecked"})
    public static synchronized Map<String, Project> getSortedProjects() {
        SortedMap sortedData = new TreeMap(new ArtefactIdComparer(ProjectCache.getProjects()));
        sortedData.putAll(ProjectCache.getProjects());
        return sortedData;
    }

    public static synchronized void setProjects(Map<String, Project> projects) {
        ProjectCache.projects = projects;
    }

    private static class ArtefactIdComparer implements Comparator {
        private Map _data = null;

        public ArtefactIdComparer(Map data) {
            super();
            _data = data;
        }

        public int compare(Object o1, Object o2) {
            final Project p1 = (Project) _data.get(o1);
            final Project p2 = (Project) _data.get(o2);

            if (p1 == null) {
                return -1;
            } else if (p2 == null) {
                return -1;
            } else if (p1.artifactId == null) {
                return -1;
            } else if (p2.artifactId == null) {
                return -1;
            }

            return p1.artifactId.compareTo(p2.artifactId);
        }
    }
}

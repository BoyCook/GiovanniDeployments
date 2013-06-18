package org.cccs.deployments.domain;

import java.io.Serializable;
import java.util.*;

/**
 * User: boycook
 * Date: Aug 2, 2010
 * Time: 5:29:00 PM
 */
public class Project implements Serializable {

    private static final long serialVersionUID = -6472793246935912480L;
    public Set<Stage> stages = new HashSet<Stage>();
    public Map<String, Attribute> attributes = new HashMap<String, Attribute>();
    public String path;
    public String artifactId;
    public String groupId;
    public String version;
    public String packaging;
    public long revision;

    public String getId() {
        return groupId + ":" + artifactId;
    }

    public String getFriendlyId() {
        return getId().replaceAll("\\.", "-").replaceAll(":", "-");
    }

    public String getArtefactPath() {
        return "/" + groupId.replaceAll("\\.", "/") + "/" + artifactId + "/" + version;
    }

    public Stage getStage(String name) {
        for (Stage stage: stages) {
            if (stage.name.equalsIgnoreCase(name)) {
                return stage;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Project{" +
                "path='" + path + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", version='" + version + '\'' +
                ", packaging='" + packaging + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return !(artifactId != null ? !artifactId.equals(project.artifactId) : project.artifactId != null) && !(groupId != null ? !groupId.equals(project.groupId) : project.groupId != null);
    }

    @Override
    public int hashCode() {
        int result = artifactId != null ? artifactId.hashCode() : 0;
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        return result;
    }
}

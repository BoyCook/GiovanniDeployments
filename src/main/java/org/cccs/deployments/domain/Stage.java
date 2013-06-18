package org.cccs.deployments.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * User: boycook
 * Date: Aug 3, 2010
 * Time: 5:57:29 PM
 */
public class Stage implements Serializable {

    private static final long serialVersionUID = -6107815405326363237L;
    public Map<String, Attribute> attributes = new HashMap<String, Attribute>();
    public Project project;
    public String name;
    public String dbFailMessage = "";
    public boolean urlValid = false;
    public boolean dbValid = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stage stage = (Stage) o;

        return !(name != null ? !name.equals(stage.name) : stage.name != null) && !(project != null ? !project.equals(stage.project) : stage.project != null);
    }

    @Override
    public int hashCode() {
        int result = project != null ? project.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}

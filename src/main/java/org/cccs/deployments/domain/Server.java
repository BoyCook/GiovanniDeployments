package org.cccs.deployments.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * User: boycook
 * Date: Aug 3, 2010
 * Time: 10:06:22 PM
 */
public class Server implements Serializable {

    private static final long serialVersionUID = 6900771736902487265L;
    public Set<Stage> stages = new HashSet<Stage>(); 
    public String URL;
    
    public Server(String URL) {
        this.URL = URL;
    }

    public String getId() {
        return this.URL;
    }

    public String getFriendlyId() {
        return getId().replaceAll("\\.", "-").replaceAll(":", "-");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return URL.equals(server.URL);
    }

    @Override
    public int hashCode() {
        return URL.hashCode();
    }
}

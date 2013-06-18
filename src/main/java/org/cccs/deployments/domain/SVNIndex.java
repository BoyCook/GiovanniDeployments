package org.cccs.deployments.domain;

import java.io.Serializable;

/**
 * User: boycook
 * Date: Aug 5, 2010
 * Time: 5:48:09 PM
 */
public class SVNIndex implements Serializable {

    private static final long serialVersionUID = -1826268618499290256L;
    public String path;
    public String revision;
    public String author;
    public String date;

    @Override
    public String toString() {
        return "SVNIndex{" +
                "author='" + author + '\'' +
                ", path='" + path + '\'' +
                ", revision='" + revision + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SVNIndex svnIndex = (SVNIndex) o;
        return !(path != null ? !path.equals(svnIndex.path) : svnIndex.path != null);
    }

    @Override
    public int hashCode() {
        return path != null ? path.hashCode() : 0;
    }
}

package org.cccs.deployments.domain;

/**
 * User: boycook
 * Date: Aug 3, 2010
 * Time: 10:08:42 PM
 */
public class DBServer extends Server {

    private static final long serialVersionUID = -33360022524467122L;
    public String schema;
    public String port;

    public DBServer(String URL, String port, String schema) {
        super(URL);
        this.port = port;
        this.schema = schema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DBServer dbServer = (DBServer) o;
        return port.equals(dbServer.port) && schema.equals(dbServer.schema);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + schema.hashCode();
        result = 31 * result + port.hashCode();
        return result;
    }
}

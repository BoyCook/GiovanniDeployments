package org.cccs.deployments.integration.oxm;

import org.cccs.deployments.domain.DBServer;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * User: boycook
 * Date: Aug 10, 2010
 * Time: 10:22:19 PM
 */
public class DBServerConverter extends BaseConverter<DBServer> {

    public DBServerConverter() {
        super(DBServer.class);
    }

    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        DBServer db = (DBServer) o;
        marshalDBServer(db, writer, context);
    }

    private void marshalDBServer(DBServer db, HierarchicalStreamWriter writer, MarshallingContext context) {
        writer.addAttribute("id", db.getId());
        writer.addAttribute("friendlyId", db.getFriendlyId());

        writer.startNode("url");
        writer.setValue(db.URL);
        writer.endNode();

        writer.startNode("schema");
        writer.setValue(db.schema);
        writer.endNode();

        writer.startNode("port");
        writer.setValue(db.port);
        writer.endNode();

        context.put("MARSHAL_PROJECT", "true");
        marshalStages(db.stages, writer, context);
        context.put("MARSHAL_PROJECT", null);
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        return null;
    }
}

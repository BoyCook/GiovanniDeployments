package org.cccs.deployments.integration.oxm;

import org.cccs.deployments.domain.Server;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * User: boycook
 * Date: Aug 10, 2010
 * Time: 9:49:32 PM
 */
public class ServerConverter extends BaseConverter<Server>{

    public ServerConverter() {
        super(Server.class);
    }

    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        Server s = (Server) o;
        marshalServer(s, writer, context);
    }

    private void marshalServer(Server s, HierarchicalStreamWriter writer, MarshallingContext context) {
        writer.addAttribute("id", s.getId());
        writer.addAttribute("friendlyId", s.getFriendlyId());

        writer.startNode("url");
        writer.setValue(s.URL);
        writer.endNode();
        context.put("MARSHAL_PROJECT", "true");
        marshalStages(s.stages, writer, context);
        context.put("MARSHAL_PROJECT", null);
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        return null;
    }
}

package org.cccs.deployments.integration.oxm;

import org.cccs.deployments.domain.SVNIndex;
import org.cccs.deployments.utils.RuntimeSupport;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * User: boycook
 * Date: 10/10/2011
 * Time: 23:10
 */
public class SVNIndexConverter extends BaseConverter<SVNIndex> {
    public SVNIndexConverter() {
        super(SVNIndex.class);
    }

    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        final SVNIndex index = new SVNIndex();
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            final String node = reader.getNodeName();
            final String value = reader.getValue();
            if (node.equalsIgnoreCase("path")) {
                if (value.contains(RuntimeSupport.DEPLOYMENT_FILE)) {
                    index.path = "/" + value.substring(0, value.lastIndexOf(RuntimeSupport.DEPLOYMENT_FILE));
                } else {
                    index.path = "/" + value;
                }
            } else if (node.equalsIgnoreCase("revision")) {
                index.revision = value;
            } else if (node.equalsIgnoreCase("author")) {
                index.author = value;
            } else if (node.equalsIgnoreCase("date")) {
                index.date = value;
            }
            reader.moveUp();
        }
        return index;
    }
}

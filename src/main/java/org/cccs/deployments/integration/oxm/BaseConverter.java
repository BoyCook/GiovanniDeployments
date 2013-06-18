package org.cccs.deployments.integration.oxm;

import org.cccs.deployments.domain.Attribute;
import org.cccs.deployments.domain.Stage;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.Map;
import java.util.Set;

/**
 * User: boycook
 * Date: Aug 10, 2010
 * Time: 9:57:09 PM
 */
public abstract class BaseConverter<T> implements Converter {

    private final Class<T> classType;

    public BaseConverter(final Class<T> type) {
        this.classType = type;
    }

    public Class<T> getType() {
        return classType;
    }

    @Override
    public boolean canConvert(Class type) {
        return (type == getType());
    }

    protected void marshalAttributes(Map<String, Attribute> attributes, HierarchicalStreamWriter writer) {
        for (String key : attributes.keySet()) {
            Attribute a = attributes.get(key);
            writer.startNode(a.name);
            writer.addAttribute("valid", String.valueOf(a.valid));
            writer.setValue(a.value);
            writer.endNode();
        }
    }

    protected void marshalStages(Set<Stage> stages, HierarchicalStreamWriter writer, MarshallingContext context) {
        if (stages.size() > 0) {
            writer.startNode("stages");
            for (Stage s : stages) {
                writer.startNode(s.name);
                
                writer.startNode("urlValid");
                writer.setValue(String.valueOf(s.urlValid));
                writer.endNode();

                writer.startNode("dbValid");
                writer.setValue(String.valueOf(s.dbValid));
                writer.endNode();

                if (s.dbFailMessage != null && !s.dbFailMessage.equals("")) {
                    writer.startNode("dbFailMessage");
                    writer.setValue(String.valueOf(s.dbFailMessage));
                    writer.endNode();
                }

                marshalAttributes(s.attributes, writer);

                if (context.get("MARSHAL_PROJECT") != null) {
                    writer.startNode("project");
                    context.convertAnother(s.project);
                    writer.endNode();
                }

                writer.endNode();
            }
            writer.endNode();
        }
    }
}

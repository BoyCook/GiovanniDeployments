package org.cccs.deployments.integration.oxm;

import org.cccs.deployments.domain.Project;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * User: Craig Cook
 * Date: Apr 3, 2010
 * Time: 2:15:42 PM
 */
public class ProjectConverter extends BaseConverter<Project> {

    public ProjectConverter() {
        super(Project.class);
    }

    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        Project p = (Project) o;
        marshalProject(p, writer, context);
    }

    public void marshalProject(Project p, HierarchicalStreamWriter writer, MarshallingContext context) {
        writer.addAttribute("id", p.getId());
        writer.addAttribute("friendlyId", p.getFriendlyId());

        writer.startNode("groupId");
        writer.setValue(p.groupId);
        writer.endNode();

        writer.startNode("artifactId");
        writer.setValue(p.artifactId);
        writer.endNode();

        writer.startNode("version");
        writer.setValue(p.version);
        writer.endNode();

        writer.startNode("revision");
        writer.setValue(Long.toString(p.revision)) ;
        writer.endNode();

        writer.startNode("packaging");
        writer.setValue(p.packaging);
        writer.endNode();

        writer.startNode("path");
        writer.setValue(p.path);
        writer.endNode();

        writer.startNode("artefactPath");
        writer.setValue(p.getArtefactPath());
        writer.endNode();

        marshalAttributes(p.attributes, writer);

        if (context.get("MARSHAL_PROJECT") == null) {
            marshalStages(p.stages, writer, context);
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        return new Project();
    }
}

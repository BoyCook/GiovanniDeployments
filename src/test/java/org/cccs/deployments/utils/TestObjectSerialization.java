package org.cccs.deployments.utils;

import org.cccs.deployments.domain.Attribute;
import org.cccs.deployments.domain.Project;
import org.cccs.deployments.domain.Stage;
import org.junit.Test;

import java.util.*;

import static org.cccs.deployments.utils.IOUtils.readFile;
import static org.cccs.deployments.utils.IOUtils.writeFile;
import static java.lang.String.format;

/**
 * User: boycook
 * Date: Aug 4, 2010
 * Time: 3:26:59 PM
 */
public class TestObjectSerialization {

    private static Set<Project> data = new HashSet<Project>();
    private static final String FILE = "data/data.data";

    @Test
    public void aWriteToDiskShouldWork() {
        Stage s1 = new Stage();
        s1.name = "production";
        s1.attributes.put("foo", new Attribute("foo", "bar"));
        s1.attributes.put("server", new Attribute("server", "test"));

        Stage s2 = new Stage();
        s2.name = "development";
        s2.attributes.put("name", new Attribute("name", "charles"));
        s2.attributes.put("hmm", new Attribute("hmm", "what"));

        Stage s3 = new Stage();
        s3.name = "staging";
        s3.attributes.put("this", new Attribute("this", "is"));
        s3.attributes.put("very", new Attribute("very", "boring"));

        Stage s4 = new Stage();
        s4.name = "testing";
        s4.attributes.put("really", new Attribute("really", "why"));
        s4.attributes.put("Charles", new Attribute("Charles", "does"));

        Stage s5 = new Stage();
        s5.name = "ort";
        s5.attributes.put("sometime", new Attribute("sometime", "act"));
        s5.attributes.put("care", new Attribute("care", "without"));        

        Project p1 = new Project();
        p1.path = "http://nexus";
        p1.groupId = "org.cccs";
        p1.artifactId = "nexus";
        p1.version = "1.0.2";
        p1.packaging = "war";
        p1.stages.add(s1);
        p1.stages.add(s2);


        Project p2 = new Project();
        p2.path = "http://hudson/war";
        p2.groupId = "org.cccs";
        p2.artifactId = "hudson";
        p2.version = "1.4.9";
        p2.packaging = "war";
        p2.stages.add(s3);

        Project p3 = new Project();
        p3.path = "http://babmoo/agent";
        p3.groupId = "org.cccs";
        p3.artifactId = "bamboo-agent";
        p3.version = "2.1.1";
        p3.packaging = "jar";
        p3.stages.add(s4);
        p3.stages.add(s5);
        
        data.add(p1);
        data.add(p2);
        data.add(p3);
        writeFile(FILE, data);
    }    

    @SuppressWarnings({"unchecked"})
    @Test
    public void readFromDiskShouldWork() {
        Set<Project> dataBack = (Set<Project>) readFile(FILE);

        for (Project p: dataBack) {
            System.out.println("-------------");
            System.out.println(p.path);
            System.out.println(p.groupId);
            System.out.println(p.artifactId);
            System.out.println(p.version);
            System.out.println(p.packaging);
            System.out.println("Stages:");
            for (Stage s: p.stages) {
                System.out.println(s.name);
                for (String key: s.attributes.keySet()) {
                    System.out.println(format("Key: [%s] Value: [%s]", key, s.attributes.get(key).value));
                }
            }
            System.out.println();
        }
    }

//    @Test
//    public void convert() throws ClassNotFoundException, IOException {
//        Map<String, Project> projects = (Map<String, Project>) getFileAsStream("/Users/boycook/temp/projects.data");
//
//
//        for (String key: projects.keySet()) {
//            Project p = projects.get(key);
//
//
//        }
////        writeFile("/Users/boycook/temp/projects.data", map);
//    }
}

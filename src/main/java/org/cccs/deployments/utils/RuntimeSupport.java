package org.cccs.deployments.utils;

import org.cccs.deployments.domain.Stage;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.String.format;

/**
 * User: Craig Cook
 * Date: Jun 9, 2010
 * Time: 9:29:53 AM
 */
public final class RuntimeSupport {

    public static String SVN_URL;
    public static String SVN_USERNAME;
    public static String SVN_PASSWORD;
    public static String FISHEYE_URL;
    public static String INDEX_FILE;
    public static String PROJECTS_FILE;
    public static String SERVERS_FILE;
    public static String DBS_FILE;
    public static String DEPLOYMENT_FILE = "/config/deploy.rb";

    static {
        Properties properties = new Properties();
        //TODO: set with defaults if property file empty
        try {
            properties.load(RuntimeSupport.class.getResourceAsStream("/app.properties"));
            INDEX_FILE = (String) properties.get("svn.paths.file");
            PROJECTS_FILE = (String) properties.get("projects.file");
            SERVERS_FILE = (String) properties.get("servers.file");
            DBS_FILE = (String) properties.get("dbs.file");
            FISHEYE_URL = (String) properties.get("fisheye.url");
            SVN_URL = (String) properties.get("subversion.repository.url");
            SVN_USERNAME = (String) properties.get("subversion.user.name");
            SVN_PASSWORD = (String) properties.get("subversion.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
    }

    public static void convertMessage(Stage stage) {
        if (stage.dbFailMessage.contains("ORA-12514")) {
            stage.dbFailMessage = format("Check SID - [%s]", stage.attributes.get("dbSid").value);
            stage.attributes.get("dbHost").valid = true;
        } else if (stage.dbFailMessage.contains("ORA-01017")) {
            stage.dbFailMessage = format("Check username/password");
            stage.attributes.get("dbHost").valid = true;
        } else if (stage.dbFailMessage.contains("Io exception: The Network Adapter could not establish the connection")) {
            stage.dbFailMessage = format("Check port - [%s]", stage.attributes.get("dbPort").value);
            stage.attributes.get("dbHost").valid = true;
        } else if (stage.dbFailMessage.contains("Io exception: Unknown host specified")) {
            stage.dbFailMessage = format("Check host - [%s]", stage.attributes.get("dbHost").value);
        }
    }

    public static String readFileToString(final String filename) throws IOException {
        final InputStream stream = RuntimeSupport.class.getResourceAsStream(filename);
        return org.apache.commons.io.IOUtils.toString(stream);
    }
}

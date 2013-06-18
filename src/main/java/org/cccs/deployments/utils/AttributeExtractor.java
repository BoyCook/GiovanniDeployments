package org.cccs.deployments.utils;

import org.cccs.deployments.domain.Attribute;
import org.cccs.deployments.domain.Stage;

import java.util.Map;

/**
 * User: boycook
 * Date: Aug 10, 2010
 * Time: 10:24:00 AM
 */
public final class AttributeExtractor {

    public static String getUrl(Stage s) {
        String context = getValue("application", s.project.attributes);

        if (s.project.attributes.containsKey("contextRoot")) {
            context = getValue("contextRoot", s.project.attributes);
        }
        return getValue("app", s.attributes) + ":" + getValue("tomcatHttpPort", s.project.attributes) + "/" + context;
    }

    public static String getDbUrl(Map<String, Attribute> attributes) {
        return getValue("dbHost", attributes) + ":" + getValue("dbPort", attributes) + "/" +  getValue("dbSid", attributes);
    }

    public static String getValue(String key, Map<String, Attribute> attributes) {
        if (attributes.containsKey(key)) {
            return attributes.get(key).value;
        } else {
             return "";
        }
    }
}

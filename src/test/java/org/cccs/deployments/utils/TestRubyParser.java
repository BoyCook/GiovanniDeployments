package org.cccs.deployments.utils;

import org.cccs.deployments.domain.Attribute;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static java.lang.String.format;
import static junit.framework.Assert.assertEquals;

/**
 * User: boycook
 * Date: Aug 4, 2010
 * Time: 6:27:19 PM
 */
public class TestRubyParser {

    private static Map<String, Attribute> properties;

    @BeforeClass
    public static void setup() throws IOException {
        String s = IOUtils.toString(TestRubyParser.class.getResourceAsStream("/stage.rb"));
        RubyParser p = new RubyParser(RubyParser.DEFAULT_PREFIXES);
        properties = p.parseRuby(s);
    }

    @Test
    public void parseSetPropertyShouldWork() {
        assertEquals("http:///crowd/services/", properties.get("crowdServerUrl").value);
    }

    @Test
    public void parseRolePropertyShouldWork() {
        assertEquals("localhost", properties.get("app").value);
    }

    @Test
    public void parseHashPropertyShouldWork() {
        assertEquals("ber1", properties.get("username").value);
        assertEquals("0", properties.get("maxStatements").value);
        assertEquals("50", properties.get("initialPoolSize").value);
    }

    @Test
    public void parseMultiLinePropertyShouldWork() {
        String multi = "  (DESCRIPTION =\n" +
                "    (ADDRESS_LIST =\n" +
                "      (ADDRESS = (PROTOCOL = TCP)(HOST = XXX)(PORT = 1521))\n" +
                "    )\n" +
                "    (CONNECT_DATA =\n" +
                "      (SERVICE_NAME = BERP)\n" +
                "    )\n" +
                "  )\n";

        assertEquals(multi, properties.get("tnsEntry").value);
    }

    @Test
    public void testDateDiff() throws InterruptedException {
        Date before = new Date();
        Thread.sleep(1349);
        Date after = new Date();
        double diff = after.getTime() - before.getTime();
        System.out.println(format("Time diff is [%f] seconds", diff / 1000));
    }

    @Test
    public void camelizeMultipleUnderscoresShouldWork() {
        String text = "charles_is_careless";
        RubyParser p = new RubyParser(RubyParser.DEFAULT_PREFIXES);
        String key = p.camelize(text);
        System.out.println(key);
        assertEquals("charlesIsCareless", key);
    }

    @Test
    public void camelizeNoUnderscoresShouldWork() {
        String text = "charles";
        RubyParser p = new RubyParser(RubyParser.DEFAULT_PREFIXES);
        String key = p.camelize(text);
        System.out.println(key);
        assertEquals("charles", key);
    }

    @Test
    public void camelizeSingleUnderscoresShouldWork() {
        String text = "charles_careless";
        RubyParser p = new RubyParser(RubyParser.DEFAULT_PREFIXES);
        String key = p.camelize(text);
        System.out.println(key);
        assertEquals("charlesCareless", key);
    }
}

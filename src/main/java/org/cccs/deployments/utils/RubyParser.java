package org.cccs.deployments.utils;

import org.cccs.deployments.domain.Attribute;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: Aug 4, 2010
 * Time: 9:35:01 PM
 */
public class RubyParser {
    //I know this is REALLY bad, but it's not as slow as you'd imagine and I'm being lazy

    private final Logger log = LoggerFactory.getLogger(RubyParser.class);
    public static final String[] DEFAULT_PREFIXES;
    public String[] prefixes;

    static {
        DEFAULT_PREFIXES = new String[]{"set :", "role :"};
    }

    public RubyParser(String[] prefixes) {
        this.prefixes = prefixes;
    }

    public Map<String, Attribute> parseRuby(String data) throws IOException {
        Map<String, Attribute> attributes = new HashMap<String, Attribute>();
        InputStream ins = IOUtils.toInputStream(data);
        DataInputStream in = new DataInputStream(ins);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        StringBuilder hashReader = new StringBuilder();
        StringBuilder multiLineReader = new StringBuilder();
        String multiLine = "";
        String multiLineKey = "";
        boolean inHash = false;
        boolean inMultiLine = false;

        while ((line = br.readLine()) != null) {
            LineType type = getType(line.trim());

            if (type == LineType.HASH_START) {
                inHash = true;
                hashReader = new StringBuilder();
                hashReader.append(line).append("\n");
            } else if (type == LineType.HASH_END) {
                inHash = false;
                hashReader.append(line);
                String hash = hashReader.toString();
                hash = hash.substring(hash.indexOf("{") + 1, hash.lastIndexOf("}"));
                RubyParser p = new RubyParser(new String[]{":"});
                attributes.putAll(p.parseRuby(hash));
            } else if (type == LineType.LT) {
                inMultiLine = true;
                multiLineReader = new StringBuilder();
                multiLine = getValue(line).substring(3);
                multiLineKey = getKey(getPrefix(line.trim()), line, getDiv(line));
            } else if (type == LineType.PROP) {
                String[] entry = parseProperty(line.trim());
                if (entry != null) {
                    attributes.put(camelize(entry[0]), new Attribute(camelize(entry[0]), entry[1]));
                }
            } else if (multiLine.length() > 0 && line.trim().equalsIgnoreCase(multiLine)) {
                log.debug(format("Key [%s] - Value [%s]", multiLineKey, multiLineReader.toString()));
                attributes.put(camelize(multiLineKey), new Attribute(camelize(multiLineKey), multiLineReader.toString()));
                inMultiLine = false;
                multiLine = "";
                multiLineKey = "";
            } else if (inHash) {
                hashReader.append(line).append("\n");
            } else if (inMultiLine) {
                multiLineReader.append(line).append("\n");
            }
        }
        return attributes;
    }

    private String[] parseProperty(String line) {
        String p = getPrefix(line.trim());
        if (p != null) {
            String div = getDiv(line);
            String key = getKey(p, line, div);
            String value = getValue(line, div);

            log.debug(format("Key [%s] - Value [%s]", key, value));
            return new String[]{key, value};
        }
        return null;
    }

    protected String camelize(String text){
        String[] values = text.split("_");
        String key = text;

        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            if (i == 0) {
                key = value;
            } else {
                key = key + value.substring(0, 1).toUpperCase() + value.substring(1);
            }
        }
        return key;
    }

    private String getKey(String prefix, String line, String div) {
        return line.substring(line.indexOf(prefix) + prefix.length(), line.indexOf(div)).trim();
    }

    private String getDiv(String line) {
        return line.indexOf("=>") == -1 ? "," : "=>";
    }

    private String getValue(String line) {
        return getValue(line, getDiv(line));
    }

    private String getValue(String line, String div) {
        //TODO: just replace start/end indexes
        String value = line.substring(line.indexOf(div) + div.length()).trim();
        value = value.replaceAll("\"", "");
        value = value.replaceAll("\'", "");
        value = value.replaceAll(",", "");
        return value;
    }

    private LineType getType(String line) {
        if (line.indexOf("}") == line.length() - 1 && line.length() > 0) {
            return LineType.HASH_END;
        } else if (line.substring(line.indexOf(",") + 1).trim().indexOf("{") == 0) {
            return LineType.HASH_START;
        } else if (getPrefix(line.trim()) == null) {
            return LineType.NULL;
        } else if (getValue(line).indexOf("<<-") == 0) {
            return LineType.LT;
        } else {
            return LineType.PROP;
        }
    }

    private String getPrefix(String line) { //Line is trimmed
        if (line.indexOf("#") == 0) {
            return null; //It's been commented out
        }
        for (String prefix : prefixes) {
            if (line.indexOf(prefix) == 0) {
                return prefix;
            }
        }
        return null;
    }

    enum LineType {
        PROP,
        HASH_START,
        HASH_END,
        LT,
        NULL
    }
}

package org.cccs.deployments.service;

import org.cccs.deployments.domain.*;
import org.cccs.deployments.provider.IndexProvider;
import org.cccs.deployments.provider.SubversionProvider;
import org.cccs.deployments.utils.RubyParser;
import org.cccs.deployments.utils.RuntimeSupport;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.cccs.deployments.cache.IndexCache.*;
import static org.cccs.deployments.cache.ProjectCache.*;
import static org.cccs.deployments.cache.ServerCache.*;
import static org.cccs.deployments.utils.AttributeExtractor.getDbUrl;
import static org.cccs.deployments.utils.AttributeExtractor.getValue;
import static org.cccs.deployments.utils.IOUtils.readFile;
import static org.cccs.deployments.utils.IOUtils.writeFile;
import static java.lang.String.format;

/**
 * User: boycook
 * Date: Aug 11, 2010
 * Time: 5:35:38 PM
 */
public class DataService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private SubversionProvider svn;
    private IndexProvider indexProvider;

    public static final String PATH_MATCH = "/config/deploy/stages";

    @SuppressWarnings({"unchecked"})
    public void loadFromDisk() {
        if (getIndexes() == null || getIndexes().size() == 0) {
            log.info("SVN URLs index is empty, reading from disk: " + RuntimeSupport.INDEX_FILE);
            Set<SVNIndex> indexes = (Set<SVNIndex>) readFile(RuntimeSupport.INDEX_FILE);
            if (indexes != null) {
                setIndexes(indexes);
            }
        }
        if (getProjects() == null || getProjects().size() == 0) {
            log.info("Project data store is empty, reading from disk: " + RuntimeSupport.PROJECTS_FILE);
            Map<String, Project> projects = (Map<String, Project>) readFile(RuntimeSupport.PROJECTS_FILE);
            if (projects != null) {
                setProjects(projects);
            }
        }
        if (getServers() == null || getServers().size() == 0) {
            log.info("Server data store is empty, reading from disk: " + RuntimeSupport.SERVERS_FILE);
            Set<Server> servers = (Set<Server>) readFile(RuntimeSupport.SERVERS_FILE);
            if (servers != null) {
                setServers(servers);
            }
        }
        if (getDbs() == null || getDbs().size() == 0) {
            log.info("Server data store is empty, reading from disk: " + RuntimeSupport.DBS_FILE);
            Set<DBServer> dbs = (Set<DBServer>) readFile(RuntimeSupport.DBS_FILE);
            if (dbs != null) {
                setDbs(dbs);
            }
        }
    }

    public void loadIndexes() {
        Set<SVNIndex> indexes = indexProvider.getIndexes();
        log.info(format("SVN Indexes found [%d]", indexes.size()));
        if (indexes.size() > 0) {
            setIndexes(indexes);
            log.info("Writing index data to disk: " + RuntimeSupport.INDEX_FILE);
            writeFile(RuntimeSupport.INDEX_FILE, getIndexes());
        }
    }

    public void loadProjectData() {
        log.info("Starting project meta-data import...");
        Date before = new Date();
        refreshProjects();
        loadProjectsFromIndexes();
        Date after = new Date();
        double diff = after.getTime() - before.getTime();
        log.info(format("Project meta-data import complete - time taken: [%f] seconds", diff / 1000));

        log.info("Writing index data to disk: " + RuntimeSupport.INDEX_FILE);
        writeFile(RuntimeSupport.INDEX_FILE, getIndexes());

        log.info("Writing project data to disk: " + RuntimeSupport.PROJECTS_FILE);
        writeFile(RuntimeSupport.PROJECTS_FILE, getProjects());

        log.info("Writing server data to disk: " + RuntimeSupport.SERVERS_FILE);
        writeFile(RuntimeSupport.SERVERS_FILE, getServers());

        log.info("Writing DB server data to disk: " + RuntimeSupport.DBS_FILE);
        writeFile(RuntimeSupport.DBS_FILE, getDbs());
    }

    private void refreshProjects() {
        Map<String, Project> tempProjects = new HashMap<String, Project>(getProjects());
        for (String key : tempProjects.keySet()) {
            Project p = tempProjects.get(key);
            if (p.revision < svn.getRevision(p.path + "/config")) {
                log.info(format("Project [%s] is not up-to-date, updating", p.getId()));
                Project project = loadProject(p.path);
                if (project == null) {
                    getProjects().remove(p.getId());
                    //Remove project from list
                } else {
                    p = project;  //Original object gets updated
                }
            }
        }
    }

    private void loadProjectsFromIndexes() {
        Map<String, Project> tempProjects = new HashMap<String, Project>();
        Set<SVNIndex> badIndexes = new HashSet<SVNIndex>();
        Set<SVNIndex> tempIndexes = new HashSet<SVNIndex>(getIndexes());

        //Check if index exist in project store
        for (SVNIndex index : tempIndexes) {
            Project cached = getProjectByIndex(index);
            if (cached == null) {
                log.info(format("Project [%s] is not in the cache, loading", index.toString()));
                Project project = loadProject(index.path);
                if (project == null) {
                    badIndexes.add(index);
                } else {
                    tempProjects.put(project.getId(), project);
                }
            }
        }
        //Removing bad indexes
        log.info(format("Bad SVN indexes found [%d]", badIndexes.size()));
        if (badIndexes.size() > 0) {
            for (SVNIndex index : badIndexes) {
                log.debug(format("Found bad index, removing [%s]", index.toString()));
                getIndexes().remove(index);
            }
        }
        //Check if project exists in index store (removing deleted config)
        for (String key : tempProjects.keySet()) {
            Project p = tempProjects.get(key);
            SVNIndex index = getIndexByProject(p);
            if (index == null) {
                tempProjects.remove(key);
            }
        }
        //Replacing projects in cache
        if (tempProjects.size() > 0) {
            setProjects(tempProjects);
        }
    }

    protected Project loadProject(String path) {
        log.debug("Loading: " + path);

        if (path.contains("tags") || path.contains("branches")) {
            log.debug(format("Cannot load [%s] as it's a tag/branch", path));
            return null;
        }

        Project p = new Project();
        p.path = path;

        try {
            if (svn.checkPath(path + "/config/deploy.rb")) {
                log.debug("Parsing deploy.rb");
                RubyParser parser = new RubyParser(RubyParser.DEFAULT_PREFIXES);
                Map<String, Attribute> attributes = parser.parseRuby(svn.getFileAsStream(path + "/config/deploy.rb").toString());
                if (attributes.containsKey("group_id")) {
                    p.groupId = attributes.get("group_id").value;
                    attributes.remove("group_id");
                }
                if (attributes.containsKey("artifact_id")) {
                    p.artifactId = attributes.get("artifact_id").value;
                    attributes.remove("artifact_id");
                }
                if (attributes.containsKey("version")) {
                    p.version = attributes.get("version").value;
                    attributes.remove("version");
                }
                if (attributes.containsKey("packaging")) {
                    p.version = attributes.get("packaging").value;
                    attributes.remove("packaging");
                }
                p.attributes = attributes;
            } else {
                log.warn(format("Deployment config [%s] not found in SVN", path));
                return null; //No deployment config
            }

            if (svn.checkPath(path + "/pom.xml")) {
                log.debug("Parsing POM.xml");
                InputStream in = IOUtils.toInputStream(svn.getFileAsStream(path + "/pom.xml").toString());
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document d = db.parse(in);
                XPathFactory factory = XPathFactory.newInstance();
                XPath xpath = factory.newXPath();
                p.groupId = (String) xpath.evaluate("/project/groupId", d, XPathConstants.STRING);
                p.artifactId = (String) xpath.evaluate("/project/artifactId", d, XPathConstants.STRING);
                p.version = (String) xpath.evaluate("/project/version", d, XPathConstants.STRING);
                p.packaging = (String) xpath.evaluate("/project/packaging", d, XPathConstants.STRING);
            }

            if (p.groupId == null || p.groupId.equals("") ||
                    p.artifactId == null || p.artifactId.equals("") ||
                    p.packaging == null || p.packaging.equals("")) {
                return null;
            }

            p.revision = svn.getRevision(path + "/config");

            Set<String> files = new HashSet<String>();
            svn.find(path + PATH_MATCH, ".rb", SVNNodeKind.FILE, files, true);

            for (String file : files) {
                Stage s = new Stage();
                RubyParser parser = new RubyParser(RubyParser.DEFAULT_PREFIXES);
                s.name = file.substring(file.lastIndexOf("/") + 1, file.indexOf(".rb"));
                s.attributes = parser.parseRuby(svn.getFileAsStream(file).toString());
                extractDBProperties(s);
                p.stages.add(s);
                s.project = p;

                if (s.name.equalsIgnoreCase("production") || s.name.equalsIgnoreCase("prod")) {
                    if (s.attributes.containsKey("username")) {
                        s.attributes.get("username").value = "PROD";
                    }
                    if (s.attributes.containsKey("password")) {
                        s.attributes.get("password").value = "PROD";
                    }
                }
                if (s.attributes.containsKey("app")) {
                    Server server = getServer(s.attributes.get("app").value);
                    if (server == null) {
                        server = new Server(s.attributes.get("app").value);
                        getServers().add(server);
                    }
                    server.stages.add(s);
                }
                final String dbUrl = getDbUrl(s.attributes);
                if (!dbUrl.equals(":/")) {
                    DBServer db = getDb(getValue("dbHost", s.attributes),
                            getValue("dbPort", s.attributes),
                            getValue("dbSid", s.attributes));

                    if (db == null) {
                        db = new DBServer(getValue("dbHost", s.attributes),
                                getValue("dbPort", s.attributes),
                                getValue("dbSid", s.attributes));
                        getDbs().add(db);
                    }
                    db.stages.add(s);
                }
            }
        } catch (SVNException e) {
            log.error("SVN error loading project");
            e.printStackTrace();
            p = null;
        } catch (ParserConfigurationException e) {
            log.error("There was a problem parsing: " + path + "/pom.xml");
            e.printStackTrace();
            p = null;
        } catch (SAXException e) {
            log.error("There was a problem parsing: " + path + "/pom.xml");
            e.printStackTrace();
            p = null;
        } catch (IOException e) {
            log.error("There was an IO Exception");
            e.printStackTrace();
            p = null;
        } catch (XPathExpressionException e) {
            log.error("There was an xpath problem with: " + path + "/pom.xml");
            e.printStackTrace();
            p = null;
        } catch (Exception e) {
            log.error("There was an unknown exception");
            e.printStackTrace();
            p = null;
        }

        return p;
    }

    protected void extractDBProperties(Stage s) {
        final String tns_key = "tnsEntry";
        if (s.attributes.containsKey(tns_key)) {
            Attribute tns = s.attributes.get(tns_key);
            s.attributes.remove(tns_key);
            s.attributes.put("dbHost", new Attribute("dbHost", getTnsValue("HOST", tns.value)));
            s.attributes.put("dbPort", new Attribute("dbPort", getTnsValue("PORT", tns.value)));
            s.attributes.put("dbSid", new Attribute("dbSid", getTnsValue("SERVICE_NAME", tns.value)));
        }
    }

    protected String getTnsValue(String key, String tns) {
        if (tns != null) {
            try {
                String value = tns.substring(tns.indexOf(key) + key.length());
                value = value.substring(value.indexOf("=") + 1, value.indexOf(")"));
                return value.trim();
            } catch (IndexOutOfBoundsException e) {
                return "";
            }
        }
        return "";
    }

    public void setSvn(SubversionProvider svn) {
        this.svn = svn;
    }

    public void setIndexProvider(IndexProvider indexProvider) {
        this.indexProvider = indexProvider;
    }
}

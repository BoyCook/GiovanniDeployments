package org.cccs.deployments.provider;

import org.cccs.deployments.domain.SVNIndex;
import org.cccs.deployments.integration.oxm.Marshal;
import org.cccs.deployments.utils.RuntimeSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.cccs.deployments.provider.HttpProvider.httpGet;
import static org.cccs.deployments.utils.RuntimeSupport.getTextValue;
import static java.lang.String.format;

/**
 * User: boycook
 * Date: Aug 5, 2010
 * Time: 5:01:59 PM
 */
public class FishEyeIndexProvider implements IndexProvider {

    /*
        select revisions from dir / where path like "deployments-dashboard/trunk/config/deploy.rb"
        and is head and not is dead group by changeset return path, revision, author, date, csid
     */

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private String query;
    private Marshal marshal;

    public FishEyeIndexProvider() {
        this("/", "deploy.rb", null);
    }

    public FishEyeIndexProvider(String root, String query, Marshal marshal) {
        this.marshal = marshal;
        this.query = "?rep=bt-dso&query=select%20revisions%20from%20dir%20" + root + "%20where%20path%20like%20%22" + query + "%22%20and%20is%20head%20and%20not%20is%20dead%20group%20by%20changeset%20return%20path,%20revision,%20author,%20date,%20csid";
    }

    public Set<SVNIndex> getIndexes() {
        log.info("Starting SVN location indexing...");
        Date before = new Date();
        Set<SVNIndex> indexes = parseQueryFile(RuntimeSupport.FISHEYE_URL + query);
        Date after = new Date();
        double diff = after.getTime() - before.getTime();
        log.info(format("SVN indexing complete - time taken: [%f] seconds", diff / 1000));
        return indexes;
    }

    @Override
    public void setQuery(String query) {
        this.query = query;
    }

    public void setMarshal(Marshal marshal) {
        this.marshal = marshal;
    }

    @SuppressWarnings({"unchecked"})
    private Set<SVNIndex> parseQueryFile(String url) {
        log.info(format("Building indexes with query: [%s]", url));
        Set<SVNIndex> indexes = null;
        try {
            Collection<SVNIndex> data = (Collection<SVNIndex>) marshal.unmarshal(httpGet(url));
            indexes = new HashSet<SVNIndex>(data);
        } catch (IOException e) {
            log.error("Failed to unmarsall SVN indexes");
            e.printStackTrace();
        }
        return indexes;
    }
}

package org.cccs.deployments.validation;

import org.cccs.deployments.cache.ProjectCache;
import org.cccs.deployments.domain.Attribute;
import org.cccs.deployments.domain.Project;
import org.cccs.deployments.domain.Stage;
import org.cccs.deployments.provider.HttpProvider;
//import oracle.jdbc.driver.OracleDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.cccs.deployments.provider.HttpProvider.httpHostCheck;
import static org.cccs.deployments.utils.AttributeExtractor.getDbUrl;
import static org.cccs.deployments.utils.RuntimeSupport.convertMessage;
import static java.lang.String.format;

/**
 * User: boycook
 * Date: Aug 8, 2010
 * Time: 8:09:05 PM
 */
public class ThreadedJDBCValidator implements Validator {

    private static final Logger log = LoggerFactory.getLogger(HttpProvider.class);

    @Override
    public void validate() {
        Map<String, Project> tempProjects = new HashMap<String, Project>(ProjectCache.getProjects());
        for (String key : tempProjects.keySet()) {
            Project project = tempProjects.get(key);
            for (Stage stage : project.stages) {
                if (!getDbUrl(stage.attributes).equals(":/")) {
                    JDBCValidator validator = new JDBCValidator(stage);
                    validator.start();
                }
            }
        }
    }

   static class JDBCValidator extends Thread {
        private Stage stage;

        JDBCValidator(Stage stage) {
            this.stage = stage;
        }

        @Override
        public void run() {
            String url = "jdbc:oracle:thin:@" + getDbUrl(stage.attributes);
            String user = stage.attributes.get("username").value;
            String pWord = stage.attributes.get("password").value;

            jdbcCheck(url, user, pWord);
            Attribute host = stage.attributes.get("dbHost");
            host.valid = stage.dbValid || httpHostCheck(host.value);
        }

        private void jdbcCheck(String url, String user, String pWord) {
            Connection conn = null;

            try {
                log.debug(format("Checking DB connection to [%s] [%s/%s]", url, user, pWord));
                //TODO: try to do this without using OJDBC
//                DriverManager.registerDriver(new OracleDriver());
                conn = DriverManager.getConnection(url, user, pWord);
                log.debug(format("DB connection [%s] [%s/%s] is valid", url, user, pWord));
                stage.dbValid = true;
                convertMessage(stage);
            } catch (SQLException e) {
                log.debug(format("Connection to DB [%s] [%s/%s] failed", url, user, pWord));
                stage.dbFailMessage = e.getMessage();
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                        conn = null;
                    }
                } catch (SQLException e) {
                    log.error("WARNING - db conn may still be open: " + url);
                    e.printStackTrace();
                }
            }
        }
    }
}

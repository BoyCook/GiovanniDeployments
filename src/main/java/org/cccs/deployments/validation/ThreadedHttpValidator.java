package org.cccs.deployments.validation;

import org.cccs.deployments.cache.ProjectCache;
import org.cccs.deployments.domain.Project;
import org.cccs.deployments.domain.Stage;

import java.util.HashMap;
import java.util.Map;

import static org.cccs.deployments.provider.HttpProvider.httpCheck;
import static org.cccs.deployments.provider.HttpProvider.httpHostCheck;
import static org.cccs.deployments.utils.AttributeExtractor.getUrl;

/**
 * User: boycook
 * Date: Aug 6, 2010
 * Time: 3:50:35 PM
 */
public class ThreadedHttpValidator implements Validator {

    @Override
    public void validate() {
        Map<String, Project> tempProjects = new HashMap<String, Project>(ProjectCache.getProjects());
        for (String key : tempProjects.keySet()) {
            Project project = tempProjects.get(key);
            for (Stage stage : project.stages) {
                HttpValidator validator = new HttpValidator(stage);
                validator.start();
            }
        }
    }

    static class HttpValidator extends Thread {
        private Stage stage;

        HttpValidator(Stage stage) {
            this.stage = stage;
        }

        @Override
        public void run() {
            if (httpCheck("http://" + getUrl(stage))) {
                this.stage.urlValid = true;
                this.stage.attributes.get("app").valid = true;
            } else if (stage.attributes.containsKey("app") && httpHostCheck(stage.attributes.get("app").value)) {
                this.stage.attributes.get("app").valid = true;
            }
        }
    }
}

package org.cccs.deployments.validation;

import org.cccs.deployments.domain.Server;
import org.cccs.deployments.domain.Stage;

import java.util.HashSet;
import java.util.Set;

import static org.cccs.deployments.cache.ServerCache.getServers;

/**
 * User: boycook
 * Date: Aug 16, 2010
 * Time: 11:35:02 PM
 */
public class AttributeValidator implements Validator {

    @Override
    public void validate() {
        Set<Server> tempServers = new HashSet<Server>(getServers());

        for (Server server: tempServers) {
            for (Stage stage: server.stages) {

            }
        }
    }
}

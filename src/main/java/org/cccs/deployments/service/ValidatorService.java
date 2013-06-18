package org.cccs.deployments.service;

import org.cccs.deployments.validation.ThreadedHttpValidator;
import org.cccs.deployments.validation.ThreadedJDBCValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: boycook
 * Date: Aug 6, 2010
 * Time: 1:03:39 PM
 */
public class ValidatorService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //TODO: think about a validator factory - sounds a bit T4'ish
    public void validate() {
        log.info("Starting validation...");
        ThreadedHttpValidator httpValidator = new ThreadedHttpValidator();
        httpValidator.validate();
        
        ThreadedJDBCValidator jdbcValidator = new ThreadedJDBCValidator();
        jdbcValidator.validate();
        log.info("Validation complete");
    }
}

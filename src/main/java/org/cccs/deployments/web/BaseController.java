package org.cccs.deployments.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * User: Craig Cook
 * Date: Apr 1, 2010
 * Time: 7:12:59 PM
 */
public abstract class BaseController {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    protected String MARSHALLER = "marshallingView";

    public static final String DOMAIN_DATA = "domain-data";
    public static final String ERROR_DATA = "error-data";

    protected void logRequest(final HttpServletRequest request) {
        log.info(request.getMethod() + " " + request.getRequestURI() + "?" + request.getQueryString());
    }

    protected String getPathItem(final HttpServletRequest request, int position) {
        List<String> stringList = asList(request.getRequestURI().split("/"));
        return stringList.get(stringList.size() - position);
    }
}

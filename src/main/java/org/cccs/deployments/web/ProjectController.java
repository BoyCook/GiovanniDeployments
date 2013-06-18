package org.cccs.deployments.web;

import org.cccs.deployments.cache.ProjectCache;
import org.cccs.deployments.domain.Project;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

import static java.lang.String.format;

/**
 * User: Craig Cook
 * Date: Apr 2, 2010
 * Time: 4:10:53 PM
 */
@Controller
@Scope("request")
public class ProjectController extends BaseController {

    @SuppressWarnings({"unchecked"})
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String getProjects(final Model model, HttpServletRequest request) {
        logRequest(request);
        log.info("Getting all projects...");
        model.addAttribute(DOMAIN_DATA, new ArrayList<Project>(ProjectCache.getSortedProjects().values()));
        return MARSHALLER;
    }

    @SuppressWarnings({"unchecked"})
    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public String getProject(@PathVariable("id") String id, final Model model, HttpServletRequest request) {
        logRequest(request);
        id = getPathItem(request, 1);
        log.info("Getting project: " + id);
        model.addAttribute(DOMAIN_DATA, ProjectCache.getProjects().get(id));
        return MARSHALLER;
    }

    @SuppressWarnings({"unchecked"})
    @RequestMapping(value = "/projects/{id}/{stage}", method = RequestMethod.GET)
    public String getProjectStage(@PathVariable("id") final String id,
                                  @PathVariable("stage") final String stage,
                                  final Model model, HttpServletRequest request) {
        logRequest(request);
        log.info(format("Getting project stage: [%s/%s]", id, stage));
        model.addAttribute(DOMAIN_DATA, ProjectCache.getProjects().get(id).getStage(stage));
        return MARSHALLER;
    }

    @SuppressWarnings({"unchecked"})
    protected boolean isProperty(final Map<String, String[]> params, final String property) {
        if (params != null) {
            if (params.containsKey(property)) {
                return Boolean.parseBoolean(params.get(property)[0]);
            }
        }
        return false;
    }
}

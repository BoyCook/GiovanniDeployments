package org.cccs.deployments.web;

import org.cccs.deployments.cache.IndexCache;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: boycook
 * Date: Aug 4, 2010
 * Time: 1:27:16 PM
 */
@Controller
@Scope("request")
public class IndexController extends BaseController {

    @SuppressWarnings({"unchecked"})
    @RequestMapping(value = "/indexes", method = RequestMethod.GET)
    public String getProjects(final Model model) {
        log.info("Getting all projects...");
        model.addAttribute(DOMAIN_DATA, IndexCache.getIndexes());
        return MARSHALLER;
    }
}

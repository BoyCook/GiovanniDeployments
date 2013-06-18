package org.cccs.deployments.web;

import org.cccs.wadlgenerator.domain.Resources;
import org.cccs.wadlgenerator.service.ResourceService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: boycook
 * Date: Aug 17, 2010
 * Time: 9:22:25 PM
 */
@Controller
@Scope("request")
public class SiteController extends BaseController{

    private static Resources resources;

    @RequestMapping(value = "/wadl", method = RequestMethod.GET)
    public String getRequestMappings(final Model model,
                                     final HttpServletRequest request,
                                     final HttpServletResponse response) {
        log.info("Received get on /service/wadl");

        //This only needs to be set once for the application
        if (resources == null) {
            resources = new Resources();
            resources.baseUrl = "https://collaborate.bt.com/deployments";
            resources.resources = ResourceService.getMappings("org.cccs.deployments.web");
        }

        response.setStatus(HttpServletResponse.SC_OK);
        model.addAttribute(BaseController.DOMAIN_DATA, resources);
        return MARSHALLER;
    }
}

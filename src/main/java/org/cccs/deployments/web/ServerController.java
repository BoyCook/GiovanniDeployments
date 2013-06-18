package org.cccs.deployments.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import static org.cccs.deployments.cache.ServerCache.*;
import static java.lang.String.format;

/**
 * User: boycook
 * Date: Aug 3, 2010
 * Time: 10:02:28 PM
 */
@Controller
@Scope("request")
public class ServerController extends BaseController {

    @SuppressWarnings({"unchecked"})
    @RequestMapping(value = "/servers", method = RequestMethod.GET)
    public String getAllServers(final Model model) {
        log.info("Getting all servers:");
        List all = new ArrayList();
        all.addAll(getDbs());
        all.addAll(getServers());
        model.addAttribute(DOMAIN_DATA, all);
        return MARSHALLER;
    }

    @SuppressWarnings({"unchecked"})
    @RequestMapping(value = "/servers/{type}", method = RequestMethod.GET)
    public String getServersByType(@PathVariable("type") final String type,
                                   final Model model) {
        log.info(format("Getting servers by type [%s]", type));
        if (type.equalsIgnoreCase("server")) {
            model.addAttribute(DOMAIN_DATA, new ArrayList(getServers()));
        } else if (type.equalsIgnoreCase("db")) {
            model.addAttribute(DOMAIN_DATA, new ArrayList(getDbs()));
        }
        return MARSHALLER;
    }

    @SuppressWarnings({"unchecked"})
    @RequestMapping(value = "/servers/{type}/{key}", method = RequestMethod.GET)
    public String getServersByUrl(@PathVariable("type") final String type,
                                  @PathVariable("key") final String key,
                                  final Model model) {
        log.info(format("Getting servers by type [%s] and id [%s]", type, key));
        if (type.equalsIgnoreCase("server")) {
            model.addAttribute(DOMAIN_DATA, getServerByKey(key));
        } else if (type.equalsIgnoreCase("db")) {
            model.addAttribute(DOMAIN_DATA, getDbByKey(key));
        }
        return MARSHALLER;
    }
}

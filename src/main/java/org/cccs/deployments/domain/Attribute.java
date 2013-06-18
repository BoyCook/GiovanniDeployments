package org.cccs.deployments.domain;

import java.io.Serializable;

/**
 * User: boycook
 * Date: Aug 9, 2010
 * Time: 6:55:10 PM
 */
public class Attribute implements Serializable {

    private static final long serialVersionUID = 3633636277912177487L;
    public String name = "";
    public String value = "";
    public boolean valid = false;

    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }
}

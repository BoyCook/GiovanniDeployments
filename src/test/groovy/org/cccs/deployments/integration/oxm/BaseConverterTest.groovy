package org.cccs.deployments.integration.oxm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.xstream.XStreamMarshaller
import org.custommonkey.xmlunit.XMLTestCase
import org.custommonkey.xmlunit.XMLUnit;

/**
 * User: boycook
 * Date: Aug 11, 2010
 * Time: 8:01:56 PM
 */
public abstract class BaseConverterTest extends XMLTestCase {

    static {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
    }

    @Autowired
    protected XStreamMarshaller marshaller;

    protected String marshal(final Object obj) throws IOException {
        return getMarshal().marshal(obj);
    }

    protected Object unmarshal(final String data) throws IOException {
        return getMarshal().unmarshal(data);
    }

    private Marshal getMarshal() {
        return new Marshal(marshaller);
    }
}

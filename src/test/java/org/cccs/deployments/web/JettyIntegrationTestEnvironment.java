package org.cccs.deployments.web;

import org.cccs.deployments.integration.oxm.BaseConverterTest;
import org.apache.commons.io.IOUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * User: Craig Cook
 * Date: May 14, 2010
 * Time: 9:58:56 AM
 */
@SuppressWarnings({"unchecked"})
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class JettyIntegrationTestEnvironment extends BaseConverterTest {
    protected static final Logger log = LoggerFactory.getLogger(JettyIntegrationTestEnvironment.class);

    @Autowired
    protected MarshallingHttpMessageConverter domainObjectConverter;

    protected static URL baseUrl;
    protected ApplicationContext applicationContext;
    protected String serviceBaseURL;
    protected RestTemplate client;
    private RestTemplate wsClient;
    public static Server server;
//    protected final XPathSupport xpath = new XPathSupport();

    protected ResponseExtractor responseExtractor = new ResponseExtractor<Object>() {
        @Override
        public Object extractData(final ClientHttpResponse response) throws IOException {
            final InputStream is = response.getBody();
            if (is.available() > 0) {
                log.debug("### Trying to unmarshall stream");
                return marshaller.unmarshal(new StreamSource(is));
            } else {
                log.debug("### No stream to unmarshall");
                return null;
            }
        }
    };

    @BeforeClass
    public static void runOnce() {
        startWebService();
    }

    @Before
    public void beforeEach() {
        client = new RestTemplate();
        client.setMessageConverters(CollectionUtils.arrayToList(
                new HttpMessageConverter<?>[]{
                domainObjectConverter, new SourceHttpMessageConverter()
        }));
        wsClient = new RestTemplate();
        wsClient.setMessageConverters(CollectionUtils.arrayToList(
                new HttpMessageConverter<?>[]{
                domainObjectConverter, new SourceHttpMessageConverter()
        }));
        serviceBaseURL = baseUrl + "/service/";
    }

    @AfterClass
    public static void stopWebService() throws Exception {
        log.debug("Stopping jetty test instance");
        server.stop();
        log.debug("Jetty test instance stopped");
    }
    public static void startWebService() {
        try {
            server = new Server();
            SelectChannelConnector connector = new SelectChannelConnector();
            connector.setMaxIdleTime(30000);
            connector.setAcceptors(2);
            connector.setStatsOn(true);
            connector.setLowResourcesConnections(5000);
            server.addConnector(connector);
            WebAppContext webapp = new WebAppContext();
            webapp.setContextPath("/");
            webapp.setWar("src/main/webapp");
            webapp.setDescriptor("src/test/resources/web.xml");
            server.setHandler(webapp);
            log.debug("Starting jetty test instance...");
            server.start();
            log.debug("Jetty startup complete.");
            baseUrl = new URL("http://localhost:" + connector.getLocalPort());
            log.debug("Server started:  " + baseUrl);
        } catch (Exception e) {
            log.debug("Error starting web service: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Does a get on a path, and unmarshalls the object it gets back
     *
     * @param url
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object httpGet(String url) {
        log.debug("######## DOING GET ON: " + url);
        return client.execute(url, HttpMethod.GET, new RequestCallback() {
            @Override
            public void doWithRequest(final ClientHttpRequest request) throws IOException {
                final HttpHeaders headers = request.getHeaders();
                headers.putAll(getHeaders());
            }
        }, responseExtractor);
    }

    /**
     * This takes the path to post to, and the object to post
     * It will automaticall marshall the object, and add the XML to the request body
     *
     * @param url
     * @param data
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object httpPost(String url, final Object data, final HttpMethod method) {
        return httpPost(url, data, method, client);
    }

    /**
     * This takes the path to post to, and the object to post
     * It will automaticall marshall the object, and add the XML to the request body
     *
     * @param client
     * @param url
     * @param data
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object httpPost(String url, final Object data, final HttpMethod method, final RestTemplate client) {
        return client.execute(url, method,
                new RequestCallback() {
                    @Override
                    public void doWithRequest(final ClientHttpRequest request) throws IOException {
                        final HttpHeaders headers = request.getHeaders();
                        headers.putAll(getHeaders());
                        if (data != null) marshaller.marshal(data, new StreamResult(request.getBody()));
//                        if (data != null) MarshalFactory.getDefaultInstance().marshal(data, request.getBody());
                    }
                }, responseExtractor);
    }

    /**
     * This takes the path to DELETE to.
     * It will automaticall add an *ignored* XML payload to the request body and unmarshal the response.
     *
     * @param url
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object httpDelete(String url) {
        return client.execute(url, HttpMethod.DELETE,
                new RequestCallback() {
                    @Override
                    public void doWithRequest(final ClientHttpRequest request) throws IOException {
                        final HttpHeaders headers = request.getHeaders();
                        headers.putAll(getHeaders());
                        request.getBody().write("<ignored>true</ignored>".getBytes());
                    }
                }, responseExtractor);
    }

    public String httpRaw(final String url) {
        return httpRaw(url, null, HttpMethod.GET);
    }

    public String httpRaw(final String url, final Object data, final HttpMethod method) {
        final Object payload = (data == null) ? "<ignored />" : data;

        return (String)wsClient.execute(serviceBaseURL + url, method,
            new RequestCallback() {
                @Override
                public void doWithRequest(final ClientHttpRequest request) throws IOException {
                    final HttpHeaders headers = request.getHeaders();
                    headers.putAll(getHeaders());

                    if (method != HttpMethod.GET) {
                        marshaller.marshal(payload, new StreamResult(request.getBody()));
                    }
                }
            }, new ResponseExtractor<String>() {
                @Override
                public String extractData(final ClientHttpResponse response) throws IOException {
                    final InputStream is = response.getBody();
                    if (is.available() > 0) {
                        return IOUtils.toString(is);
                    } else {
                        return null;
                    }
                }
            });
    }

    public HttpHeaders getHeaders() {
        final HttpHeaders headers = new HttpHeaders();
//        headers.add("User-Agent", "BER/CLIENT");
        headers.add("Accept", "application/xml");
        headers.add("Content-Type", "application/xml");
        return headers;
    }

//    @SuppressWarnings("unchecked")
//    public List<Asset> getAssets(String params) {
//        final HttpResponseDescriptor response = httpGet(serviceBaseURL + "assets/" + params);
//        assertEquals(200, response.getStatusCode().intValue());
//
//        final List<Asset> assets = (List<Asset>) ((HttpResponseDescriptor) response).getData();
//        assertThat(assets.size(), is(greaterThan(0)));
//
//        log.debug(format("There are %d %s", assets.size(), params));
//        return assets;
//    }
}

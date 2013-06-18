package org.cccs.deployments.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: Aug 6, 2010
 * Time: 1:30:08 PM
 */
public final class HttpProvider {

    private static final Logger log = LoggerFactory.getLogger(HttpProvider.class);

    public static boolean httpCheck(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(30);
            if (conn.getResponseCode() == 200) {
                log.debug(format("URL [%s] is valid", urlStr));
                return true;
            }
        } catch (ConnectException e) {
            log.error(format("Connection to URL [%s] failed", urlStr));
        } catch (MalformedURLException e) {
            log.error(format("URL [%s] is invalid", urlStr));
        } catch (UnknownHostException e) {
            log.error(format("URL [%s] does not exist", urlStr));
        } catch (IOException e) {
            log.error(format("URL [%s] is unreachable", urlStr));
        } catch (Exception e) {
            log.error(format("Unknown error with URL [%s]", urlStr));
        }
        return false;
    }

    public static String httpGet(String urlStr) {
        log.debug(format("Doing get on URL [%s]", urlStr));
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(30);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            StringBuilder responseText = new StringBuilder();
            while ((line = in.readLine()) != null) {
                responseText.append(line);
            }
            in.close();
            return responseText.toString();
        } catch (ConnectException e) {
            log.error(format("Connection to URL [%s] failed", urlStr));
        } catch (MalformedURLException e) {
            log.error(format("URL [%s] is invalid", urlStr));
        } catch (UnknownHostException e) {
            log.error(format("URL [%s] does not exist", urlStr));
        } catch (IOException e) {
            log.error(format("URL [%s] is unreachable", urlStr));
        } catch (Exception e) {
            log.error(format("Unknown error with URL [%s]", urlStr));
        }
        return null;
    }

    public static boolean httpHostCheck(String host) {
        try {
            InetAddress.getByName(host);
            return true;
        } catch (Exception e) {
            log.error(format("Error with host [%s]", host));
        }
        return false;
    }
}

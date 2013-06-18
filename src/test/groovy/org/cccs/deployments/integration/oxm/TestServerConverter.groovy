package org.cccs.deployments.integration.oxm;

import org.cccs.deployments.domain.DBServer;
import org.cccs.deployments.domain.Server;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.cccs.deployments.utils.IOUtils.readFile;

/**
 * User: boycook
 * Date: Aug 11, 2010
 * Time: 8:01:13 PM
 */
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
class TestServerConverter extends BaseConverterTest {

    private static List<Server> servers;
    private static List<DBServer> dbs;

    String serverXML = """
        <server id="tpl008a005.nat.bt.com" friendlyId="tpl008a005-nat-bt-com">
            <url>tpl008a005.nat.bt.com</url>
            <stages>
                <staging>
                    <urlValid>false</urlValid>
                    <dbValid>false</dbValid>
                    <crowdServerUrl valid="false">http://10.248.12.24/crowd/services/</crowdServerUrl>
                    <crowdApplicationName valid="false">nexus</crowdApplicationName>
                    <app valid="false">tpl008a005.nat.bt.com</app>
                    <division valid="false">fetch(:snapshots_repo)</division>
                    <crowdApplicationPassword valid="false">nexus</crowdApplicationPassword>
                    <project id="com.bt.collaborate.nexus:bt-nexus" friendlyId="com-bt-collaborate-nexus-bt-nexus">
                        <groupId>com.bt.collaborate.nexus</groupId>
                        <artifactId>bt-nexus</artifactId>
                        <version>1.7.1-1-SNAPSHOT</version>
                        <revision>18862</revision>
                        <packaging>war</packaging>
                        <path>/nexus/trunk</path>
                        <artefactPath>/com/bt/collaborate/nexus/bt-nexus/1.7.1-1-SNAPSHOT</artefactPath>
                        <application valid="false">nexus</application>
                        <tomcatHttpPort valid="false">30004</tomcatHttpPort>
                        <contextRoot valid="false">artefacts</contextRoot>
                        <httpGetMethod valid="false">wget -nv</httpGetMethod>
                        <warFileName valid="false">artefacts</warFileName>
                        <tomcatShutdownPort valid="false">31004</tomcatShutdownPort>
                    </project>
                </staging>
                <development>
                    <urlValid>false</urlValid>
                    <dbValid>false</dbValid>
                    <app valid="false">tpl008a005.nat.bt.com</app>
                    <acquireRetryAttempts valid="false">5</acquireRetryAttempts>
                    <pooled valid="false">true</pooled>
                    <acquireRetryDelay valid="false">500</acquireRetryDelay>
                    <type valid="false">com.mchange.v2.c3p0.ComboPooledDataSource</type>
                    <password valid="false">etl</password>
                    <acquireIncrement valid="false">10</acquireIncrement>
                    <checkoutTimeout valid="false">0</checkoutTimeout>
                    <dbHost valid="false">tpl008d001.nat.bt.com</dbHost>
                    <tomcatHttpPort valid="false">31973</tomcatHttpPort>
                    <username valid="false">etl</username>
                    <dbSid valid="false">ADMIN</dbSid>
                    <maxStatements valid="false">0</maxStatements>
                    <maxPoolSize valid="false">350</maxPoolSize>
                    <configPath valid="false">classpath:application.properties</configPath>
                    <minPoolSize valid="false">50</minPoolSize>
                    <initialPoolSize valid="false">50</initialPoolSize>
                    <factory valid="false">org.apache.naming.factory.BeanFactory</factory>
                    <tomcatShutdownPort valid="false">31974</tomcatShutdownPort>
                    <numHelperThreads valid="false">15</numHelperThreads>
                    <dbPort valid="false">1521</dbPort>
                    <project id="com.bt.collaborate:dashboard-etl" friendlyId="com-bt-collaborate-dashboard-etl">
                        <groupId>com.bt.collaborate</groupId>
                        <artifactId>dashboard-etl</artifactId>
                        <version>1.0-SNAPSHOT</version>
                        <revision>19253</revision>
                        <packaging>war</packaging>
                        <path>/dashboard-etl/trunk</path>
                        <artefactPath>/com/bt/collaborate/dashboard-etl/1.0-SNAPSHOT</artefactPath>
                        <application valid="false">dashboard-etl</application>
                        <contextRoot valid="false">dashboard-etl</contextRoot>
                        <tomcatJavaOpts valid="false">-server -Xms2056m -Xmx2056m -XX:PermSize=1024m -XX:MaxPermSize=1024m -XX:+UseParallelOldGC -XX:+AggressiveOpts</tomcatJavaOpts>
                        <httpGetMethod valid="false">https_proxy=http://proxy.intra.bt.com:8080 wget -nv</httpGetMethod>
                    </project>
                </development>
                <integration>
                    <urlValid>false</urlValid>
                    <dbValid>false</dbValid>
                    <port valid="false">61616</port>
                    <jotmJarDownloadUrl valid="false">http://collaborate.bt.com/artefacts/content/repositories/bt-dso-releases/jira-tomcat-dependencies/jtom/1.4.3/jtom-1.4.3.jar</jotmJarDownloadUrl>
                    <geronimoJtaJarDownloadUrl valid="false">https://collaborate.bt.com/artefacts/content/groups/public/org/apache/geronimo/specs/geronimo-jta_1.0.1B_spec/1.0.1/geronimo-jta_1.0.1B_spec-1.0.1.jar</geronimoJtaJarDownloadUrl>
                    <c3p0JarDownloadUrl valid="false">https://collaborate.bt.com/artefacts/content/groups/public/c3p0/c3p0/0.9.1.2/c3p0-0.9.1.2.jar</c3p0JarDownloadUrl>
                    <tnsName valid="false">INTEG_STORM</tnsName>
                    <commonsLoggingJarDownloadUrl valid="false">http://collaborate.bt.com/artefacts/content/repositories/bt-dso-releases/jira-tomcat-dependencies/commons-logging/1.0.4/commons-logging-1.0.4.jar</commonsLoggingJarDownloadUrl>
                    <urlName valid="false">storm</urlName>
                    <otsJtsJarDownloadUrl valid="false">http://collaborate.bt.com/artefacts/content/repositories/bt-dso-releases/jira-tomcat-dependencies/ots-jts/1.0/ots-jts-1.0.jar</otsJtsJarDownloadUrl>
                    <log4jJarDownloadUrl valid="false">http://collaborate.bt.com/artefacts/content/repositories/bt-dso-releases/jira-tomcat-dependencies/log4j/1.2.7/log4j-1.2.7.jar</log4jJarDownloadUrl>
                    <ipaddress valid="false">10.248.12.9</ipaddress>
                    <password valid="false">stormd</password>
                    <username valid="false">STORMD</username>
                    <carolJarDownloadUrl valid="false">http://collaborate.bt.com/artefacts/content/repositories/bt-dso-releases/jira-tomcat-dependencies/carol/1.5.2/carol-1.5.2.jar</carolJarDownloadUrl>
                    <division valid="false">public</division>
                    <tmp valid="false">/var/tmp</tmp>
                    <crowdPropertiesDownloadUrl valid="false">https://collaborate.bt.com/svn/bt-dso/storm/trunk/storm-webapp/src/main/resources/integration/crowd.properties</crowdPropertiesDownloadUrl>
                    <app valid="false">tpl008a005.nat.bt.com</app>
                    <geronimoJmsJarDownloadUrl valid="false">https://collaborate.bt.com/artefacts/content/groups/public/org/apache/geronimo/specs/geronimo-jms_1.1_spec/1.1.1/geronimo-jms_1.1_spec-1.1.1.jar</geronimoJmsJarDownloadUrl>
                    <c3p0OracleThinJarDownloadUrl valid="false">https://collaborate.bt.com/artefacts/content/repositories/bt-dso-releases/jira-tomcat-dependencies/c3p0-oracle-thin-extras/0.9.1.2/c3p0-oracle-thin-extras-0.9.1.2.jar</c3p0OracleThinJarDownloadUrl>
                    <kahadbJarDownloadUrl valid="false">https://collaborate.bt.com/artefacts/content/groups/public/org/apache/activemq/kahadb/5.3.1/kahadb-5.3.1.jar</kahadbJarDownloadUrl>
                    <httpGetMethod valid="false">wget -nv</httpGetMethod>
                    <activemqCoreJarDownloadUrl valid="false">https://collaborate.bt.com/artefacts/content/groups/public/org/apache/activemq/activemq-core/5.3.1/activemq-core-5.3.1.jar</activemqCoreJarDownloadUrl>
                    <geronimoJ2eeJarDownloadUrl valid="false">https://collaborate.bt.com/artefacts/content/groups/public/org/apache/geronimo/specs/geronimo-j2ee-management_1.0_spec/1.0/geronimo-j2ee-management_1.0_spec-1.0.jar</geronimoJ2eeJarDownloadUrl>
                    <activeioCoreJarDownloadUrl valid="false">https://collaborate.bt.com/artefacts/content/groups/public/org/apache/activemq/activeio-core/3.1.2/activeio-core-3.1.2.jar</activeioCoreJarDownloadUrl>
                    <objectwebJarDownloadUrl valid="false">http://collaborate.bt.com/artefacts/content/repositories/bt-dso-releases/jira-tomcat-dependencies/objectweb-datasource/1.4.3/objectweb-datasource-1.4.3.jar</objectwebJarDownloadUrl>
                    <jtaJarDownloadUrl valid="false">http://collaborate.bt.com/artefacts/content/repositories/bt-dso-releases/jira-tomcat-dependencies/jta/1.0.1/jta-1.0.1.jar</jtaJarDownloadUrl>
                    <carolPropertiesJarDownloadUrl valid="false">http://collaborate.bt.com/artefacts/content/repositories/bt-dso-releases/jira-tomcat-dependencies/carol-properties/1.0.0/carol-properties-1.0.0.jar</carolPropertiesJarDownloadUrl>
                    <jonasTimerJarDownloadUrl valid="false">http://collaborate.bt.com/artefacts/content/repositories/bt-dso-releases/jira-tomcat-dependencies/jonas_timer/1.4.3/jonas_timer-1.4.3.jar</jonasTimerJarDownloadUrl>
                    <jotmJrmpJarDownloadUrl valid="false">http://collaborate.bt.com/artefacts/content/repositories/bt-dso-releases/jira-tomcat-dependencies/jotm-jrmp_stubs/1.4.3/jotm-jrmp_stubs-1.4.3.jar</jotmJrmpJarDownloadUrl>
                    <hsqldbJarDownloadUrl valid="false">http://collaborate.bt.com/artefacts/content/repositories/bt-dso-releases/jira-tomcat-dependencies/hsqldb/1.8.0.5/hsqldb-1.8.0.5.jar</hsqldbJarDownloadUrl>
                    <xapoolJarDownloadUrl valid="false">http://collaborate.bt.com/artefacts/content/repositories/bt-dso-releases/jira-tomcat-dependencies/xapool/1.3.1/xapool-1.3.1.jar</xapoolJarDownloadUrl>
                    <jotmIiopJarDownloadUrl valid="false">http://collaborate.bt.com/artefacts/content/repositories/bt-dso-releases/jira-tomcat-dependencies/jotm-iiop_stubs/1.4.3/jotm-iiop_stubs-1.4.3.jar</jotmIiopJarDownloadUrl>
                    <project id="com.bt.dso.storm:storm" friendlyId="com-bt-dso-storm-storm">
                        <groupId>com.bt.dso.storm</groupId>
                        <artifactId>storm</artifactId>
                        <version>6.6-SNAPSHOT</version>
                        <revision>18647</revision>
                        <packaging>war</packaging>
                        <path>/storm/trunk/storm-webapp</path>
                        <artefactPath>/com/bt/dso/storm/storm/6.6-SNAPSHOT</artefactPath>
                        <application valid="false">storm</application>
                        <tomcatHttpPort valid="false">30015</tomcatHttpPort>
                        <tomcatJavaOpts valid="false">-server -Xms4096m -Xmx4096m -XX:PermSize=1024m -XX:MaxPermSize=1024m -Doracle.net.tns_admin=/var/lib/oracle -Dorg.apache.jasper.runtime.BodyContentImpl.LIMIT_BUFFER=true -XX:+UseParallelOldGC -XX:+AggressiveOpts -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=#{jmx_remote_port} -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.password.file=/var/lib/storm/current/conf/jmxremote.password -Dcom.sun.management.jmxremote.access.file=/var/lib/storm/current/conf/jmxremote.access</tomcatJavaOpts>
                        <tomcatShutdownPort valid="false">31015</tomcatShutdownPort>
                        <jmxRemotePort valid="false">40015</jmxRemotePort>
                    </project>
                </integration>
                <development>
                    <urlValid>false</urlValid>
                    <dbValid>false</dbValid>
                    <app valid="false">tpl008a005.nat.bt.com</app>
                    <acquireRetryAttempts valid="false">5</acquireRetryAttempts>
                    <pooled valid="false">true</pooled>
                    <acquireRetryDelay valid="false">500</acquireRetryDelay>
                    <type valid="false">com.mchange.v2.c3p0.ComboPooledDataSource</type>
                    <password valid="false">etl</password>
                    <acquireIncrement valid="false">10</acquireIncrement>
                    <checkoutTimeout valid="false">0</checkoutTimeout>
                    <dbHost valid="false">tpl008d001.nat.bt.com</dbHost>
                    <tomcatHttpPort valid="false">31975</tomcatHttpPort>
                    <username valid="false">etl</username>
                    <dbSid valid="false">ADMIN</dbSid>
                    <maxStatements valid="false">0</maxStatements>
                    <maxPoolSize valid="false">350</maxPoolSize>
                    <configPath valid="false">classpath:development.properties</configPath>
                    <minPoolSize valid="false">50</minPoolSize>
                    <initialPoolSize valid="false">50</initialPoolSize>
                    <factory valid="false">org.apache.naming.factory.BeanFactory</factory>
                    <tomcatShutdownPort valid="false">31976</tomcatShutdownPort>
                    <numHelperThreads valid="false">15</numHelperThreads>
                    <dbPort valid="false">1521</dbPort>
                    <project id="com.bt.collaborate.dashboard:dashboard-landing" friendlyId="com-bt-collaborate-dashboard-dashboard-landing">
                        <groupId>com.bt.collaborate.dashboard</groupId>
                        <artifactId>dashboard-landing</artifactId>
                        <version>1.0-SNAPSHOT</version>
                        <revision>19255</revision>
                        <packaging>war</packaging>
                        <path>/dashboard-landing/trunk</path>
                        <artefactPath>/com/bt/collaborate/dashboard/dashboard-landing/1.0-SNAPSHOT</artefactPath>
                        <application valid="false">dashboard-landing</application>
                        <contextRoot valid="false">dashboard-landing</contextRoot>
                        <tomcatJavaOpts valid="false">-server -Xms2056m -Xmx2056m -XX:PermSize=1024m -XX:MaxPermSize=1024m -XX:+UseParallelOldGC -XX:+AggressiveOpts</tomcatJavaOpts>
                        <httpGetMethod valid="false">https_proxy=http://proxy.intra.bt.com:8080 wget -nv</httpGetMethod>
                    </project>
                </development>
                <development>
                    <urlValid>false</urlValid>
                    <dbValid>false</dbValid>
                    <crowdServerUrl valid="false">http://tpl008a002.nat.bt.com:30027/crowd2/services/</crowdServerUrl>
                    <crowdApplicationName valid="false">nexus</crowdApplicationName>
                    <app valid="false">tpl008a005.nat.bt.com</app>
                    <division valid="false">fetch(:snapshots_repo)</division>
                    <crowdApplicationPassword valid="false">nexus</crowdApplicationPassword>
                    <project id="com.bt.collaborate.nexus:bt-nexus" friendlyId="com-bt-collaborate-nexus-bt-nexus">
                        <groupId>com.bt.collaborate.nexus</groupId>
                        <artifactId>bt-nexus</artifactId>
                        <version>1.7.1-1-SNAPSHOT</version>
                        <revision>18862</revision>
                        <packaging>war</packaging>
                        <path>/nexus/trunk</path>
                        <artefactPath>/com/bt/collaborate/nexus/bt-nexus/1.7.1-1-SNAPSHOT</artefactPath>
                        <application valid="false">nexus</application>
                        <tomcatHttpPort valid="false">30004</tomcatHttpPort>
                        <contextRoot valid="false">artefacts</contextRoot>
                        <httpGetMethod valid="false">wget -nv</httpGetMethod>
                        <warFileName valid="false">artefacts</warFileName>
                        <tomcatShutdownPort valid="false">31004</tomcatShutdownPort>
                    </project>
                </development>
            </stages>
        </server>
    """

    String serversXML = """
        <resources>""" + serverXML + """</resources>
    """

    String dbXML = """
        <db id="tpl008d001.nat.bt.com" friendlyId="tpl008d001-nat-bt-com">
            <url>tpl008d001.nat.bt.com</url>
            <schema>ADMIN</schema>
            <port>1521</port>
            <stages>
                <testing>
                    <urlValid>false</urlValid>
                    <dbValid>false</dbValid>
                    <crowdApplicationName valid="false">ber</crowdApplicationName>
                    <crowdServerUrl valid="false">http://tpl008a006.nat.bt.com:30007/crowd/services/</crowdServerUrl>
                    <app valid="false">tpl008a004.nat.bt.com</app>
                    <acquireRetryAttempts valid="false">5</acquireRetryAttempts>
                    <pooled valid="false">true</pooled>
                    <acquireRetryDelay valid="false">500</acquireRetryDelay>
                    <type valid="false">com.mchange.v2.c3p0.ComboPooledDataSource</type>
                    <password valid="false">B3RTESTING</password>
                    <acquireIncrement valid="false">10</acquireIncrement>
                    <checkoutTimeout valid="false">0</checkoutTimeout>
                    <dbHost valid="false">tpl008d001.nat.bt.com</dbHost>
                    <username valid="false">BERTESTING</username>
                    <dbSid valid="false">ADMIN</dbSid>
                    <tomcatJavaOpts valid="false">-Xms1024m -Xmx2046m -Dcrowd.properties=/var/lib/#{application}/current/conf/crowd.properties</tomcatJavaOpts>
                    <maxStatements valid="false">0</maxStatements>
                    <maxPoolSize valid="false">350</maxPoolSize>
                    <crowdApplicationPassword valid="false">ber</crowdApplicationPassword>
                    <minPoolSize valid="false">50</minPoolSize>
                    <initialPoolSize valid="false">50</initialPoolSize>
                    <factory valid="false">org.apache.naming.factory.BeanFactory</factory>
                    <numHelperThreads valid="false">15</numHelperThreads>
                    <dbPort valid="false">1521</dbPort>
                    <project id="org.cccs.ber:ber-war" friendlyId="com-bt-tools-ber-ber-war">
                        <groupId>org.cccs.ber</groupId>
                        <artifactId>ber-war</artifactId>
                        <version/>
                        <revision>19459</revision>
                        <packaging>war</packaging>
                        <path>/ber/trunk/war</path>
                        <artefactPath>/com/bt/tools/ber/ber-war/</artefactPath>
                        <crowdServerUrl valid="false">http://tpl006a009.nat.bt.com:30007/crowd/services/</crowdServerUrl>
                        <crowdApplicationName valid="false">ber</crowdApplicationName>
                        <crowdSessionTokenkey valid="false">session.tokenkey</crowdSessionTokenkey>
                        <application valid="false">berservice</application>
                        <crowdSessionIsauthenticated valid="false">session.isauthenticated</crowdSessionIsauthenticated>
                        <crowdSessionLastvalidation valid="false">session.lastvalidation</crowdSessionLastvalidation>
                        <tomcatVersion valid="false">6.0.26</tomcatVersion>
                        <crowdSessionValidationinterval valid="false">10</crowdSessionValidationinterval>
                        <jmxRemotePort valid="false">40020</jmxRemotePort>
                        <tomcatHttpPort valid="false">30020</tomcatHttpPort>
                        <contextRoot valid="false">sdkrepo</contextRoot>
                        <tomcatJavaOpts valid="false">-Xms512m -Xmx1024m</tomcatJavaOpts>
                        <crowdApplicationPassword valid="false">ber</crowdApplicationPassword>
                        <tomcatShutdownPort valid="false">31020</tomcatShutdownPort>
                        <crowdApplicationLoginUrl valid="false">https://colaborate.bt.com/sdkrepo</crowdApplicationLoginUrl>
                    </project>
                </testing>
                <development>
                    <urlValid>false</urlValid>
                    <dbValid>false</dbValid>
                    <app valid="false">tpl008a005.nat.bt.com</app>
                    <acquireRetryAttempts valid="false">5</acquireRetryAttempts>
                    <pooled valid="false">true</pooled>
                    <acquireRetryDelay valid="false">500</acquireRetryDelay>
                    <type valid="false">com.mchange.v2.c3p0.ComboPooledDataSource</type>
                    <password valid="false">etl</password>
                    <acquireIncrement valid="false">10</acquireIncrement>
                    <checkoutTimeout valid="false">0</checkoutTimeout>
                    <dbHost valid="false">tpl008d001.nat.bt.com</dbHost>
                    <tomcatHttpPort valid="false">31973</tomcatHttpPort>
                    <username valid="false">etl</username>
                    <dbSid valid="false">ADMIN</dbSid>
                    <maxStatements valid="false">0</maxStatements>
                    <maxPoolSize valid="false">350</maxPoolSize>
                    <configPath valid="false">classpath:application.properties</configPath>
                    <minPoolSize valid="false">50</minPoolSize>
                    <initialPoolSize valid="false">50</initialPoolSize>
                    <factory valid="false">org.apache.naming.factory.BeanFactory</factory>
                    <tomcatShutdownPort valid="false">31974</tomcatShutdownPort>
                    <numHelperThreads valid="false">15</numHelperThreads>
                    <dbPort valid="false">1521</dbPort>
                    <project id="com.bt.collaborate:dashboard-etl" friendlyId="com-bt-collaborate-dashboard-etl">
                        <groupId>com.bt.collaborate</groupId>
                        <artifactId>dashboard-etl</artifactId>
                        <version>1.0-SNAPSHOT</version>
                        <revision>19253</revision>
                        <packaging>war</packaging>
                        <path>/dashboard-etl/trunk</path>
                        <artefactPath>/com/bt/collaborate/dashboard-etl/1.0-SNAPSHOT</artefactPath>
                        <application valid="false">dashboard-etl</application>
                        <contextRoot valid="false">dashboard-etl</contextRoot>
                        <tomcatJavaOpts valid="false">-server -Xms2056m -Xmx2056m -XX:PermSize=1024m -XX:MaxPermSize=1024m -XX:+UseParallelOldGC -XX:+AggressiveOpts</tomcatJavaOpts>
                        <httpGetMethod valid="false">https_proxy=http://proxy.intra.bt.com:8080 wget -nv</httpGetMethod>
                    </project>
                </development>
                <sandbox>
                    <urlValid>false</urlValid>
                    <dbValid>false</dbValid>
                    <crowdApplicationName valid="false">ber</crowdApplicationName>
                    <crowdServerUrl valid="false">http://tpl006a009.nat.bt.com:30007/crowd/services/</crowdServerUrl>
                    <app valid="false">tpl008a006.nat.bt.com</app>
                    <acquireRetryAttempts valid="false">5</acquireRetryAttempts>
                    <pooled valid="false">true</pooled>
                    <acquireRetryDelay valid="false">500</acquireRetryDelay>
                    <type valid="false">com.mchange.v2.c3p0.ComboPooledDataSource</type>
                    <password valid="false">B3RSANDBOX</password>
                    <acquireIncrement valid="false">10</acquireIncrement>
                    <checkoutTimeout valid="false">0</checkoutTimeout>
                    <dbHost valid="false">tpl008d001.nat.bt.com</dbHost>
                    <username valid="false">BERSANDBOX</username>
                    <dbSid valid="false">ADMIN</dbSid>
                    <tomcatJavaOpts valid="false">-Xms512m -Xmx1024m -Dcrowd.properties=/var/lib/#{application}/current/conf/crowd.properties</tomcatJavaOpts>
                    <maxStatements valid="false">0</maxStatements>
                    <maxPoolSize valid="false">350</maxPoolSize>
                    <crowdApplicationPassword valid="false">ber</crowdApplicationPassword>
                    <minPoolSize valid="false">50</minPoolSize>
                    <initialPoolSize valid="false">50</initialPoolSize>
                    <factory valid="false">org.apache.naming.factory.BeanFactory</factory>
                    <numHelperThreads valid="false">15</numHelperThreads>
                    <dbPort valid="false">1521</dbPort>
                    <project id="org.cccs.ber:ber-war" friendlyId="com-bt-tools-ber-ber-war">
                        <groupId>org.cccs.ber</groupId>
                        <artifactId>ber-war</artifactId>
                        <version/>
                        <revision>19459</revision>
                        <packaging>war</packaging>
                        <path>/ber/trunk/war</path>
                        <artefactPath>/com/bt/tools/ber/ber-war/</artefactPath>
                        <crowdServerUrl valid="false">http://tpl006a009.nat.bt.com:30007/crowd/services/</crowdServerUrl>
                        <crowdApplicationName valid="false">ber</crowdApplicationName>
                        <crowdSessionTokenkey valid="false">session.tokenkey</crowdSessionTokenkey>
                        <application valid="false">berservice</application>
                        <crowdSessionIsauthenticated valid="false">session.isauthenticated</crowdSessionIsauthenticated>
                        <crowdSessionLastvalidation valid="false">session.lastvalidation</crowdSessionLastvalidation>
                        <tomcatVersion valid="false">6.0.26</tomcatVersion>
                        <crowdSessionValidationinterval valid="false">10</crowdSessionValidationinterval>
                        <jmxRemotePort valid="false">40020</jmxRemotePort>
                        <tomcatHttpPort valid="false">30020</tomcatHttpPort>
                        <contextRoot valid="false">sdkrepo</contextRoot>
                        <tomcatJavaOpts valid="false">-Xms512m -Xmx1024m</tomcatJavaOpts>
                        <crowdApplicationPassword valid="false">ber</crowdApplicationPassword>
                        <tomcatShutdownPort valid="false">31020</tomcatShutdownPort>
                        <crowdApplicationLoginUrl valid="false">https://colaborate.bt.com/sdkrepo</crowdApplicationLoginUrl>
                    </project>
                </sandbox>
                <anna>
                    <urlValid>false</urlValid>
                    <dbValid>false</dbValid>
                    <app valid="false">tpl008a001.nat.bt.com</app>
                    <acquireRetryAttempts valid="false">5</acquireRetryAttempts>
                    <pooled valid="false">true</pooled>
                    <acquireRetryDelay valid="false">500</acquireRetryDelay>
                    <type valid="false">com.mchange.v2.c3p0.ComboPooledDataSource</type>
                    <password valid="false">etl</password>
                    <acquireIncrement valid="false">10</acquireIncrement>
                    <checkoutTimeout valid="false">0</checkoutTimeout>
                    <dbHost valid="false">tpl008d001.nat.bt.com</dbHost>
                    <tomcatHttpPort valid="false">31979</tomcatHttpPort>
                    <username valid="false">etl</username>
                    <dbSid valid="false">ADMIN</dbSid>
                    <maxStatements valid="false">0</maxStatements>
                    <maxPoolSize valid="false">350</maxPoolSize>
                    <configPath valid="false">classpath:development.properties</configPath>
                    <minPoolSize valid="false">50</minPoolSize>
                    <initialPoolSize valid="false">50</initialPoolSize>
                    <factory valid="false">org.apache.naming.factory.BeanFactory</factory>
                    <tomcatShutdownPort valid="false">31980</tomcatShutdownPort>
                    <numHelperThreads valid="false">15</numHelperThreads>
                    <dbPort valid="false">1521</dbPort>
                    <project id="com.bt.collaborate:dashboard-etl" friendlyId="com-bt-collaborate-dashboard-etl">
                        <groupId>com.bt.collaborate</groupId>
                        <artifactId>dashboard-etl</artifactId>
                        <version>1.0-SNAPSHOT</version>
                        <revision>19253</revision>
                        <packaging>war</packaging>
                        <path>/dashboard-etl/trunk</path>
                        <artefactPath>/com/bt/collaborate/dashboard-etl/1.0-SNAPSHOT</artefactPath>
                        <application valid="false">dashboard-etl</application>
                        <contextRoot valid="false">dashboard-etl</contextRoot>
                        <tomcatJavaOpts valid="false">-server -Xms2056m -Xmx2056m -XX:PermSize=1024m -XX:MaxPermSize=1024m -XX:+UseParallelOldGC -XX:+AggressiveOpts</tomcatJavaOpts>
                        <httpGetMethod valid="false">https_proxy=http://proxy.intra.bt.com:8080 wget -nv</httpGetMethod>
                    </project>
                </anna>
                <development>
                    <urlValid>false</urlValid>
                    <dbValid>false</dbValid>
                    <app valid="false">tpl008a005.nat.bt.com</app>
                    <acquireRetryAttempts valid="false">5</acquireRetryAttempts>
                    <pooled valid="false">true</pooled>
                    <acquireRetryDelay valid="false">500</acquireRetryDelay>
                    <type valid="false">com.mchange.v2.c3p0.ComboPooledDataSource</type>
                    <password valid="false">etl</password>
                    <acquireIncrement valid="false">10</acquireIncrement>
                    <checkoutTimeout valid="false">0</checkoutTimeout>
                    <dbHost valid="false">tpl008d001.nat.bt.com</dbHost>
                    <tomcatHttpPort valid="false">31975</tomcatHttpPort>
                    <username valid="false">etl</username>
                    <dbSid valid="false">ADMIN</dbSid>
                    <maxStatements valid="false">0</maxStatements>
                    <maxPoolSize valid="false">350</maxPoolSize>
                    <configPath valid="false">classpath:development.properties</configPath>
                    <minPoolSize valid="false">50</minPoolSize>
                    <initialPoolSize valid="false">50</initialPoolSize>
                    <factory valid="false">org.apache.naming.factory.BeanFactory</factory>
                    <tomcatShutdownPort valid="false">31976</tomcatShutdownPort>
                    <numHelperThreads valid="false">15</numHelperThreads>
                    <dbPort valid="false">1521</dbPort>
                    <project id="com.bt.collaborate.dashboard:dashboard-landing" friendlyId="com-bt-collaborate-dashboard-dashboard-landing">
                        <groupId>com.bt.collaborate.dashboard</groupId>
                        <artifactId>dashboard-landing</artifactId>
                        <version>1.0-SNAPSHOT</version>
                        <revision>19255</revision>
                        <packaging>war</packaging>
                        <path>/dashboard-landing/trunk</path>
                        <artefactPath>/com/bt/collaborate/dashboard/dashboard-landing/1.0-SNAPSHOT</artefactPath>
                        <application valid="false">dashboard-landing</application>
                        <contextRoot valid="false">dashboard-landing</contextRoot>
                        <tomcatJavaOpts valid="false">-server -Xms2056m -Xmx2056m -XX:PermSize=1024m -XX:MaxPermSize=1024m -XX:+UseParallelOldGC -XX:+AggressiveOpts</tomcatJavaOpts>
                        <httpGetMethod valid="false">https_proxy=http://proxy.intra.bt.com:8080 wget -nv</httpGetMethod>
                    </project>
                </development>
            </stages>
        </db>
    """

    String dbsXML = """
        <resources>""" + dbXML + """</resources>
    """

    @BeforeClass
    public static void setup() {
        Set<Server> fileServers = (Set<Server>) readFile("src/test/resources/servers.data");
        Set<DBServer> fileDbs = (Set<DBServer>) readFile("src/test/resources/dbs.data");
        servers = new ArrayList<Server>(fileServers);
        dbs = new ArrayList<DBServer>(fileDbs);
    }

    @Test
    public void marshalingAServerShouldWork() {
        String marshalled = marshal(servers.get(0));
        println(marshalled);
        assertXMLEqual("Comparing marshalled xml to expected xml", serverXML, marshalled);
    }

    @Test
    public void marshalingListOfServersShouldWork() {
        List<Server> data = new ArrayList<Server>();
        data.add(servers.get(0))
        String marshalled = marshal(data);
        println(marshalled);
        assertXMLEqual("Comparing marshalled xml to expected xml", serversXML, marshalled);
    }

    @Test
    public void marshalingAllServersShouldWork() {
        String marshalled = marshal(servers);
        println(marshalled);
    }

    @Test
    public void marshalingADBShouldWork() {
        String marshalled = marshal(dbs.get(0));
        println(marshalled);
        assertXMLEqual("Comparing marshalled xml to expected xml", dbXML, marshalled);
    }

    @Test
    public void marshalingListOfDBsShouldWork() {
        List<Server> data = new ArrayList<Server>();
        data.add(dbs.get(0))
        String marshalled = marshal(data);
        println(marshalled);
        assertXMLEqual("Comparing marshalled xml to expected xml", dbsXML, marshalled);
    }

    @Test
    public void marshalingAllDBsShouldWork() {
        String marshalled = marshal(dbs);
        println(marshalled);
    }

    @Test
    public void marshalingListOfServersAndDBsShouldWork() {
        List all = new ArrayList();
        all.addAll(dbs);
        all.addAll(servers);
        String marshalled = marshal(all);
        println(marshalled);
    }
}

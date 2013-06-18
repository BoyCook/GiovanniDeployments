<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:param name="filter1" select="''" />
    <xsl:param name="filter2" select="''" />
    <xsl:param name="filter3" select="''" />
    <xsl:param name="lc" select="'abcdefghijklmnopqrstuvwxyz'" />
    <xsl:param name="uc" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
    <xsl:template match="/">
        <div>
            <div id="apps">
                <xsl:apply-templates/>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="resources">
        <!--<xsl:for-each select="*">-->
        <xsl:for-each select="*[
            (contains(translate(url, $lc, $uc), translate($filter1, $lc, $uc)) or
            contains(translate(port, $lc, $uc), translate($filter1, $lc, $uc)) or
            contains(translate(schema, $lc, $uc), translate($filter1, $lc, $uc)))
            and
            (contains(translate(url, $lc, $uc), translate($filter2, $lc, $uc)) or
            contains(translate(port, $lc, $uc), translate($filter2, $lc, $uc)) or
            contains(translate(schema, $lc, $uc), translate($filter2, $lc, $uc)))
            and
            (contains(translate(url, $lc, $uc), translate($filter3, $lc, $uc)) or
            contains(translate(port, $lc, $uc), translate($filter3, $lc, $uc)) or
            contains(translate(schema, $lc, $uc), translate($filter3, $lc, $uc)))
        ]">
            <div class="resource_div">
                <xsl:attribute name="id"><xsl:value-of select="@friendlyId"/>-<xsl:value-of select="port"/></xsl:attribute>
                <h3><xsl:value-of select="url"/></h3>
                <div>
                    <a>
                        <xsl:attribute name="href">server.html#<xsl:value-of select="name()"/>/<xsl:value-of select="url"/>
                        </xsl:attribute>
                        <h2>
                            <xsl:value-of select="url"/>
                            <xsl:if test="name() = 'db'">:<xsl:value-of select="port"/>/<xsl:value-of select="schema"/>
                            </xsl:if>
                            -
                            <xsl:value-of select="name()"/>
                        </h2>
                    </a>
                    <table>
                        <xsl:for-each select="stages/*">
                            <tr>
                                <td>
                                    <xsl:choose>
                                        <xsl:when test="urlValid = 'true'">
                                            <xsl:attribute name="class">isgreen</xsl:attribute>
                                        </xsl:when>
                                        <xsl:when test="app/@valid = 'true'">
                                            <xsl:attribute name="class">isamber</xsl:attribute>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:attribute name="class">isred</xsl:attribute>
                                        </xsl:otherwise>
                                    </xsl:choose>

                                    <xsl:variable name="contextPath">
                                        <xsl:choose>
                                            <xsl:when test="project/contextRoot != ''">
                                                <xsl:value-of select="project/contextRoot"/>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:value-of select="project/application"/>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:variable>

                                    <a style="color:black;">
                                        <xsl:attribute name="href">http://<xsl:value-of select="app"/>:<xsl:value-of select="project/tomcatHttpPort"/>/<xsl:value-of select="$contextPath"/>
                                        </xsl:attribute>
                                        <xsl:value-of select="project/artifactId"/><xsl:text>  </xsl:text><xsl:value-of select="project/version"/><xsl:text>  </xsl:text>(<xsl:value-of select="name()"/>)
                                    </a>
                                </td>
                                <td>
                                    <a>
                                        <xsl:attribute name="href">project.html#<xsl:value-of select="project/@id"/>
                                        </xsl:attribute>
                                        <xsl:value-of select="project/groupId"/>:<xsl:value-of select="project/artifactId"/>
                                    </a>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </table>
                </div>
            </div>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>

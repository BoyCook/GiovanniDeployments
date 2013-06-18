<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:param name="filter1" select="''" />
    <xsl:param name="filter2" select="''" />
    <xsl:param name="filter3" select="''" />
    <xsl:param name="lc" select="'abcdefghijklmnopqrstuvwxyz'" />
    <xsl:param name="uc" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
    <xsl:template match="/">
        <div>
            <div id="projects">
                <xsl:apply-templates/>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="resources">
        <xsl:for-each select="project[
            (contains(translate(artifactId, $lc, $uc), translate($filter1, $lc, $uc)) or
            contains(translate(groupId, $lc, $uc), translate($filter1, $lc, $uc)) or
            contains(translate(groupId, $lc, $uc), translate($filter1, $lc, $uc)))
            and
            (contains(translate(artifactId, $lc, $uc), translate($filter2, $lc, $uc)) or
            contains(translate(groupId, $lc, $uc), translate($filter2, $lc, $uc)) or
            contains(translate(groupId, $lc, $uc), translate($filter3, $lc, $uc)))
            and
            (contains(translate(artifactId, $lc, $uc), translate($filter3, $lc, $uc)) or
            contains(translate(groupId, $lc, $uc), translate($filter3, $lc, $uc)) or
            contains(translate(groupId, $lc, $uc), translate($filter3, $lc, $uc)))
        ]">
            <div class="resource_div">
                <xsl:attribute name="id"><xsl:value-of select="@friendlyId"/>
                </xsl:attribute>
                <h3>
                    <xsl:value-of select="artifactId"/>
                </h3>
                <div>
                    <table style="vertical-align:middle;">
                        <tr>
                            <td>
                                <a class="detail" target="_blank">
                                    <xsl:attribute name="href">project.html#<xsl:value-of select="@id"/>
                                    </xsl:attribute>
                                    <h2>
                                        <xsl:value-of select="artifactId"/>
                                    </h2>
                                </a>
                            </td>
                            <td>
                                <a target="_blank">
                                    <xsl:attribute name="href">
                                        https://collaborate.bt.com/artefacts/content/groups/public<xsl:value-of select="artefactPath"/>
                                    </xsl:attribute>
                                    <xsl:value-of select="groupId"/>:<xsl:value-of select="artifactId"/>:<xsl:value-of select="version"/>:<xsl:value-of select="packaging"/>
                                </a>
                            </td>
                            <td>
                                <a target="_blank">
                                    <xsl:attribute name="href">https://collaborate.bt.com/svn/bt-dso<xsl:value-of
                                            select="path"/>
                                    </xsl:attribute>
                                    https://collaborate.bt.com/svn/bt-dso<xsl:value-of select="path"/>
                                </a>
                                (<xsl:value-of select="revision"/>)
                            </td>
                        </tr>
                    </table>

                    <table style="vertical-align:middle;">
                        <xsl:for-each select="stages/*">
                            <tr>
                                <td>
                                    <xsl:value-of select="name()"/>
                                </td>
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
                                            <xsl:when test="../../contextRoot != ''">
                                                <xsl:value-of select="../../contextRoot"/>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:value-of select="../../application"/>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:variable>

                                    <a style="color:black;" target="_blank">
                                        <xsl:attribute name="href">http://<xsl:value-of select="app"/>:<xsl:value-of select="../../tomcatHttpPort"/>/<xsl:value-of select="$contextPath"/></xsl:attribute>
                                        http://<xsl:value-of select="app"/>:<xsl:value-of select="../../tomcatHttpPort"/>/<xsl:value-of select="$contextPath"/>
                                    </a>
                                </td>
                                <td>
                                    <xsl:if test="dbHost != ''">
                                        <xsl:choose>
                                            <xsl:when test="dbValid = 'true'">
                                                <xsl:attribute name="class">isgreen</xsl:attribute>
                                            </xsl:when>
                                            <xsl:when test="dbHost/@valid = 'true'">
                                                <xsl:attribute name="class">isamber</xsl:attribute>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:attribute name="class">isred</xsl:attribute>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                        <xsl:value-of select="dbHost"/>:<xsl:value-of select="dbPort"/>/<xsl:value-of select="dbSid"/>
                                    </xsl:if>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </table>
                </div>
            </div>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>

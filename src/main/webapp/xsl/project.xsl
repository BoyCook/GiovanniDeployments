<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <div>
            <xsl:apply-templates select="*"/>
        </div>
    </xsl:template>

    <xsl:template match="project">
        <div class="resource_div">
            <table>
                <tr>
                    <td>
                        <h2>
                            <xsl:value-of select="artifactId"/>
                        </h2>
                    </td>
                    <td>
                        <xsl:value-of select="artifactId"/>:<xsl:value-of select="groupId"/>:<xsl:value-of
                            select="version"/>:<xsl:value-of select="packaging"/>
                    </td>
                    <td>
                        <xsl:value-of select="path"/>
                    </td>
                </tr>
            </table>
            <table>
                <xsl:for-each
                        select="*[not(self::artifactId or self::groupId or self::version or self::packaging or self::stages)]">
                    <tr>
                        <td>
                            <xsl:value-of select="name()"/>
                        </td>
                        <td>
                            <xsl:value-of select="current()"/>
                        </td>
                    </tr>
                </xsl:for-each>
            </table>
        </div>
        <xsl:for-each select="stages/*">
            <div class="resource_div">
                <h3>
                    <xsl:value-of select="name()"/>
                </h3>
                <table>
                    <xsl:for-each select="*">
                        <tr>
                            <td>
                                <xsl:value-of select="name()"/>
                            </td>
                            <td>
                                <xsl:value-of select="current()"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </div>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>

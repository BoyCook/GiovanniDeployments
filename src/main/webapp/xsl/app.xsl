<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/*">
        <div>
            <h2>
                <xsl:value-of select="url"/>
                <xsl:if test="name() = 'db'">:<xsl:value-of select="port"/>/<xsl:value-of select="schema"/>
                </xsl:if>
                -
                <xsl:value-of select="name()"/>
            </h2>
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
        </div>
    </xsl:template>
</xsl:stylesheet>

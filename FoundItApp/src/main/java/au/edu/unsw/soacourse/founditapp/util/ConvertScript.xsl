<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="2.0">
	<xsl:output indent="yes" />

	<xsl:template match="/">
		<Jobs>
			<xsl:apply-templates select="job-posting/job" />
		</Jobs>
	</xsl:template>

	<xsl:template match="job">
		<Job>
			<id>
				<xsl:value-of select="generate-id()" />
			</id>
			<title>
				<xsl:value-of select="title" />
			</title>
			<companyid>
				<xsl:value-of select="generate-id()" />
			</companyid>
			<positionType>Engineer</positionType>
			<salary>25$ per hour</salary>
			<description>
				<xsl:value-of select="description" />
			</description>
			<skills>Programming,AJAX</skills>
			<link><xsl:value-of select="link" /></link>
			<location>Sydney City CBD</location>
			<status>open</status>
		</Job>
	</xsl:template>

</xsl:stylesheet>
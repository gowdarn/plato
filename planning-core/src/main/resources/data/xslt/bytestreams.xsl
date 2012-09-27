<!--
==============================================================================
Stylesheet for plan-export: collecting bytestreams for temp-files into the XML
==============================================================================
Changes:

*
==========================================================
Plato: Planning Tool
developed within the EU IST FP6 project PLANETS: Preservation and Long-term Access through Networked Services,
Contract number 033789, June 2006-May 2009.
Subproject: PP - Preservation Planning
Workpackage: PP4 - Preservation Plan Decision Support
Responsible partner: TUWIEN - Vienna University of Technology, Department of Software Technology and Interactive Systems
Further information:
www.planets-project.eu
www.ifs.tuwien.ac.at/dp
--> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="2.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:plato="http://ifs.tuwien.ac.at/dp/plato">
	<xsl:output method="xml" indent="yes" encoding="UTF-8" />
	<xsl:preserve-space elements="*" />

	<xsl:param name="tempDir" select="''" />

	<xsl:template match="*|text()|@*">
		<xsl:copy>
			<xsl:apply-templates select="@*" />
			<xsl:apply-templates select="*|text()" />
		</xsl:copy>
	</xsl:template>


	<!-- COPY BASE64-ENCODED DATA FROM TEMP FILES INTO THE TARGET XML -->
	<xsl:template match="*/plato:data[@hasData='true']">
		<xsl:element name="data" xmlns="http://ifs.tuwien.ac.at/dp/plato">
			<xsl:copy-of select="@*" />
			<xsl:variable name="id">
				<xsl:value-of select="." />
			</xsl:variable>
			<xsl:value-of select="document(concat($tempDir,$id,'.xml'))/data" />
		</xsl:element>
	</xsl:template>

	<!-- Include Preservation action plan data -->
	<xsl:template
		match="//plato:preservationActionPlan/plato:objects[number(.) = number(.)]">
		<xsl:element name="objects" xmlns="http://ifs.tuwien.ac.at/dp/plato">
			<xsl:copy-of select="@*" />
			<xsl:variable name="id">
				<xsl:value-of select="." />
			</xsl:variable>
			<xsl:for-each select="document(concat($tempDir,$id,'.xml'))//elements/*">
				<xsl:element name="object" xmlns="http://ifs.tuwien.ac.at/dp/plato">
					<xsl:attribute name="uid">
						<xsl:value-of select="./@uid" />
					</xsl:attribute>
				</xsl:element>
			</xsl:for-each>

		</xsl:element>
	</xsl:template>

	<!-- Include t2flow -->
	<xsl:template
		match="//plato:preservationActionPlan/plato:executablePlan[@type='t2flow' and number(.) = number(.)]">
		<xsl:variable name="id">
			<xsl:value-of select="." />
		</xsl:variable>
		<xsl:element name="executablePlan" xmlns="http://ifs.tuwien.ac.at/dp/plato">
			<xsl:copy-of select="@*" />
			<xsl:copy-of select="document(concat($tempDir,$id,'.xml'))/." />
		</xsl:element>
	</xsl:template>

</xsl:stylesheet>
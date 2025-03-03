<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.utils.MedRecWebAppUtils"%>

<html:html>
<head>
  <title><bean:message key="title.MedRec"/></title>
  <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>
<body bgcolor="white" topmargin="0" leftmargin="0">

<!-- START Header -->
<jsp:include page="Header.jsp" flush="true"/>
<!-- END Header -->

<!-- Start Padding for Content -->
<table width="100%" border="0" cellspacing="1" cellpadding="10">
  <tr>
    <td>
      <!-- Content -->
      <!-- START Breadcrumbs  -->
      <sslext:link page="/home.do"><span class="pagetitle-admin"><bean:message key="link.home"/></span></sslext:link> &gt;
      <span class="pagetitle-admin"><bean:message key="page.title.diagnostics"/></span>
      <!-- END Breadcrumbs -->
      <br/>
<br/>

<sslext:form method="<%=MedRecWebAppUtils.getHttpMethod()%>" action="/diagnostics.do" focus="logType">
<table border="0" cellspacing="0" cellpadding="2">
  <tr>
    <td><bean:message key="LogType"/>:</td>
    <td><html:select property="logType">
       <html:optionsCollection property="logTypes" label="optionLabel" value="optionValue"/>
    </html:select></td>
  </tr>
  <tr><td colspan="2">&nbsp;</td></tr>
  <tr>
    <td><bean:message key="MessageSeverity"/>:</td>
    <td><html:select property="msgSeverity">
       <html:optionsCollection property="msgSeverities" label="optionLabel" value="optionValue"/>
    </html:select></td>
  </tr>
  <tr><td colspan="2">&nbsp;</td></tr>
  <tr>
    <td><bean:message key="Timespan"/>:</td>
    <td><html:select property="timeSpan">
       <html:optionsCollection property="timeSpans" label="optionLabel" value="optionValue"/>
    </html:select></td>
  </tr>
  <tr><td colspan="2">&nbsp;</td></tr>
  <tr>
    <td><bean:message key="MessageSearchString"/>:</td>
    <td><html:text property="searchCriteria" size="20" maxlength="45" tabindex="1"/></td>
  </tr>
  <tr><td colspan="2">&nbsp;</td></tr>
  <tr><td colspan="2" align="center">
    <html:submit property="action" tabindex="3" styleClass="profilebutton">
      <bean:message key="button.Logs"/>
    </html:submit>
    <html:cancel property="action" tabindex="4" styleClass="cancelbutton">
      <bean:message key="button.Cancel"/>
    </html:cancel>
  </td></tr>
</table>
</sslext:form>
<!-- Content END -->

<!-- End Padding for Content -->
    </td>
  </tr>
</table>
</body>
</html:html>

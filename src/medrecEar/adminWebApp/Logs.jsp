<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.actions.AdminConstants"%>

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
      <sslext:link page="/diagnostics.do"><span class="pagetitle-admin"><bean:message key="link.diagnostics"/></span></sslext:link> &gt;
      <span class="pagetitle-admin"><bean:message key="page.title.view.logs"/> - <bean:write name="<%=AdminConstants.LOG_TYPE%>" scope="request"/> </span>
<!-- END Breadcrumbs -->
      <br/>
	  <br/>
    <table width="100%" border="1" cellspacing="0" cellpadding="1">
    <tr>
      <td><font size="-2" weight="bold">ROW</font></td>
      <logic:iterate id="col" name="<%=AdminConstants.LOG_COLUMNS%>" scope="request">
        <td><font size="-2" weight="bold"><bean:write name="col" property="columnName"/></font></td>
      </logic:iterate>
    <tr>
    <% int i=0; %>
    <bean:define id="logType" name="<%=AdminConstants.LOG_TYPE%>"/>
    <logic:iterate id="row"
                   name="<%=AdminConstants.LOG_RECORDS%>"
                   scope="request">
      <tr>
        <td><font size="-4"><%=++i%></font>&nbsp;</td>
        <bean:define id="objs" name="row"/>
        <logic:iterate id="obj" name="objs">
          <logic:present name="obj" scope="page">
            <td><font size="-4"><bean:write name="obj"/></font>&nbsp;</td>
          </logic:present>
          <logic:notPresent  name="obj" scope="page">
            <td><font size="-4"></font>&nbsp;</td>
          </logic:notPresent >
        </logic:iterate>
      </tr>
    </logic:iterate>
    <br/>
    <br/>
<!-- End Padding for Content -->
    </td>
  </tr>
</table>
</body>
</html:html>

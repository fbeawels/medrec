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
  <span class="pagetitle-admin"><bean:message key="page.title.view.pending.xml"/></span>
  <!-- END Breadcrumbs -->

  <!-- START Content -->
  <br/><br/>
  <!-- START Import Content -->
  <p class="title"><bean:message key="PendingXMLFiles"/></p>
  <table border="0" cellspacing="0" cellpadding="2">
  <tr>
    <td>&nbsp;&nbsp;</td>
    <td>
      <table border="1" cellspacing="0" cellpadding="2">
      <tr>
        <td class="coltitle-admin"><bean:message key="Result"/></td>
        <td class="coltitle-admin"><bean:message key="File"/></td>
        <td class="coltitle-admin"><bean:message key="Date"/></td>
        <td class="coltitle-admin" align="center"><bean:message key="Size"/> (b)</td>
        <td class="coltitle-admin" colspan="2" align="center"><bean:message key="Action"/></td>
      </tr>
      <!-- START Dynamic Import Content -->
      <bean:define id="xmlFileBeans" name="<%=AdminConstants.IMPORT_BEANS%>" scope="request"/>
      <bean:size id="numOfFile" name="xmlFileBeans"/>
      <logic:equal name="numOfFile" value="0" >
          <tr><td colspan="6"><bean:message key="message.no.xml.files"/></td></tr>
      </logic:equal>
      <logic:greaterThan name="numOfFile" value="0" >
        <% int i=0; %>
        <logic:iterate id="xmlFileBean" name="xmlFileBeans" type="com.bea.medrec.beans.XMLImportBean">
          <tr>
            <td><center><%=++i%></center></td>
            <td><bean:write name="xmlFileBean" property="filename"/>
            <td><bean:write name="xmlFileBean" property="date"/></td>
            <td align="right"><bean:write name="xmlFileBean" property="size"/></td>
            <td>
              <center>
              <sslext:link page="/import.do?action=upload" paramId="<%=AdminConstants.XML_FILE%>"
                paramName="xmlFileBean" paramProperty="filename">
                <bean:message key="link.upload"/>
              </sslext:link>
              </center>
            </td>
          </tr>
        </logic:iterate>
      </logic:greaterThan>
      <!-- END Dynamic Import Content -->
      </table>
    </td>
  </tr>
  </table>

<!-- END Visit Content -->
<!-- END Padding for Content -->
    </td>
  </tr>
</table>
</body>
</html:html>

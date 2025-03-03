<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.actions.AdminConstants,
                 com.bea.medrec.utils.MedRecWebAppUtils"%>

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
  <sslext:link page="/viewimports.do"><span class="pagetitle-admin"><bean:message key="page.title.view.pending.xml"/></span></sslext:link> &gt;
  <span class="pagetitle-admin"><bean:message key="page.title.import.confirmation"/></span>
  <!-- END Breadcrumbs -->

  <!-- START Content -->
  <br/><br/>
  <!-- START Import Content -->
  <p class="title"></p>
  <table border="0" cellspacing="0" cellpadding="2">
    <tr>
      <td>&nbsp;&nbsp;<bean:message key="message.xml.import.complete"/></td>
    </tr>
  </table>
<!-- END Import Content -->
<!-- END Padding for Content -->
    </td>
  </tr>
</table>
</body>
</html:html>

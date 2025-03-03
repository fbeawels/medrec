<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>

<html:html>
<head>
  <title><bean:message key="title.MedRec"/></title>
  <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>
<body bgcolor="white" topmargin="0" leftmargin="0">

<!-- START Header -->
<jsp:include page="Header.jsp" flush="true"/>
<!-- END Header -->

<!-- START Padding for Content -->
<table width="100%" border="0" cellspacing="1" cellpadding="10">
  <tr>
    <td>
      <!-- START Breadcrumbs  -->
      <span class="pagetitle-admin"><bean:message key="link.home"/></span>
      <!-- END Breadcrumbs -->
      <br/>
      <br/>
      <!-- START Content -->
      <table border="0" cellspacing="1" cellpadding="0">
        <tr>
          <td><b><bean:message key="page.title.administration.tasks"/></b></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;<sslext:link page="/createnewadmin.do"><bean:message key="link.add.new.admin"/></sslext:link>
          <br>&nbsp;&nbsp;<bean:message key="desc.add.new.administrator"/></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;<sslext:link page="/viewrequests.do"><bean:message key="link.view.pending.requests"/></sslext:link>
          <br/>&nbsp;&nbsp;<bean:message key="desc.approve.deny.registration"/></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;<sslext:link page="/viewimports.do"><bean:message key="link.import.records"/></sslext:link>
          <br/>&nbsp;&nbsp;<bean:message key="desc.upload.records"/></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;<sslext:link page="/diagnostics.do"><bean:message key="link.diagnostics"/></sslext:link>
          <br/>&nbsp;&nbsp;<bean:message key="desc.diagnostics"/></td>
        </tr>
      </table>
      <!-- END Content -->

    </td>
  </tr>
</table>
<!-- END Padding for Content -->

</body>
</html:html>

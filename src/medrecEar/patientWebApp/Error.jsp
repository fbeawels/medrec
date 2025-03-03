<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.beans.ErrorBean"%>

<jsp:useBean id="errorBean" scope="request" type="com.bea.medrec.beans.ErrorBean"/>

<html:html>
<head>
  <html:base/>
  <title><bean:message key="title.MedRec"/></title>
  <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>
<body bgCOLOR="white" topmargin="0" leftmargin="0">

<!-- Patient Header Begin -->
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
  <td bgcolor="#006666" align=left>
    <img src="images/patient_top_banner3.gif">
  </td>
</tr>
<tr>
  <td bgcolor="#E9F4F0">
    <img src="images/patient_banner_photo.jpg">
  </td>
  <td bgcolor="#E9F4F0">
    <br/>
  </td>
</tr>
</table>
<!-- Patient Header End -->

<!-- START Padding for Content  -->
<table width="100%" border="0" cellspacing="1" cellpadding="10">
<tr><td>

  <!-- START Content  -->
  <p class="pagetitle-patient"><bean:message key="message.error"/></p>
  <br/>
  <table border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td>
    <%
      ErrorBean errBean = (ErrorBean)request.getAttribute("errorBean");
      String errMsg = errBean.getErrMessage();
      String returnPage = "/"+errBean.getLink();
    %>
    &nbsp;
    <%=errMsg%>
    <br/><br/>
    &nbsp;<sslext:link page="<%=returnPage%>"><bean:message key="link.return"/></sslext:link>
    </td>
  </tr>
  </table>
  <!-- END Content  -->

  </td></tr>
</table>
<!-- END Padding for Content -->

</body>
</html:html>

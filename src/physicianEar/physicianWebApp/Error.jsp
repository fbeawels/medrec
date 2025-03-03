<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.beans.ErrorBean"%>

<jsp:useBean id="errorBean" scope="request" type="com.bea.medrec.beans.ErrorBean"/>

<html:html>
<head>
  <html:base/>
  <title><bean:message key="title.MedRec"/> - <bean:message key="Error"/></title>
  <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>
<body bgCOLOR="white" topmargin="0" leftmargin="0">

<!-- START Title Bar  -->
<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <tr>
    <td bgcolor="#CC3300" align=left><img src="images/physician_top_banner3.gif"></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFCC"><img src="images/physician_banner_photo.jpg" width="354" height="65"></td>
    <td bgcolor="#FFFFCC"><br/></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF" height=1 colspan=2><img src="images/clear.gif" width="100" height="1" alt="" border="0"></td>
  </tr>
</table>
<!-- Title Bar END -->

<!-- START Padding for Content  -->
<table width="100%" border="0" cellspacing="1" cellpadding="10"><tr><td>

<!-- START Content  -->
<p class="pagetitle-md"><bean:message key="page.title.error.occured"/></p>
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

</td></tr></table>
<!-- END Padding for Content -->

</body>
</html:html>

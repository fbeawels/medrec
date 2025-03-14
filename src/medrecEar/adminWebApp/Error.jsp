<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.beans.ErrorBean"%>

<jsp:useBean id="errorBean" scope="request" type="com.bea.medrec.beans.ErrorBean"/>

<html:html>
<HEAD>
  <TITLE><bean:message key="title.MedRec"/> - <bean:message key="Error"/></TITLE>
  <link rel="stylesheet" type="text/css" href="stylesheet.css">
</HEAD>
<BODY bgCOLOR="white" TOPMARGIN="0" LEFTMARGIN="0">

<!-- Title Bar Start -->
<table width="100%" cellpadding=0 cellspacing=0 border=0>
  <tr>
    <td bgcolor="#669900" align=left><img src="images/admin_top_banner3.gif"></td>
  </tr>
  <tr>
    <td bgcolor="#D1FFFE"><img src="images/admin_banner_photo.jpg"></td>
    <td bgcolor="#D1FFFE">&nbsp;</td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF" height=1 colspan=2><img src="images/clear.gif" width="100" height="1" alt="" border="0"></td>
  </tr>

</table>
<!-- Title Bar End -->

<!-- START Content  -->
<!-- Start Padding for Content -->
<table width="100%" border="0" cellspacing="1" cellpadding="10">
  <tr>
    <td>

<p class="pagetitle-md"><bean:message key="page.title.error.occured"/></p>
<BR>
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="5">
<TR>
  <TD>
    <%
      ErrorBean errBean = (ErrorBean)request.getAttribute("errorBean");
      String errMsg = errBean.getErrMessage();
      String returnPage = "/"+errBean.getLink();
    %>
    &nbsp;
    <bean:define id="err" name="errorBean" scope="request"/>
    <bean:write name="err" property="errMessage"/>
    <br/><br/>
    &nbsp;<sslext:link page="<%=returnPage%>"><bean:message key="link.return"/></sslext:link>
  </TD>
</TR>
</TABLE>
<!-- END Content  -->

    </td>
  </tr>
</table>
<!-- END Padding for Content -->

</BODY>
</html:html>

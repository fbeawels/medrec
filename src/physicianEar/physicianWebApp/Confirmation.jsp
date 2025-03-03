<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.beans.RecordBean" %>
<%@ page session="true"%>

<%
  RecordBean r = (RecordBean)session.getAttribute("recordBean");
  System.out.println("R: "+(r != null ? r.toString() : "null"));
%>

<html:html>
<head>
  <title><bean:message key="title.MedRec"/> - <bean:message key="title.physician.app"/></title>
  <link rel="stylesheet" type="text/css" href="stylesheet.css">
  <html:base/>
</head>

<body bgcolor="white" topmargin="0" leftmargin="0">

<!-- START Header -->
<jsp:include page="PatientHeader.jsp" flush="true"/>
<!-- END Header -->

<!-- START Content -->
<table width="100%" border="0" cellspacing="1" cellpadding="10">
<tr>
  <td>
    <!-- START Breadcrumbs -->
    <sslext:link page="/search.do" >
      <span class="pagetitle-md">
        <bean:message key="page.title.physcian.home"/>
      </span>
    </sslext:link> &gt;
    <sslext:link page="/medicalrecord.do" >
      <span class="pagetitle-md">
        <bean:message key="page.title.patient.record"/>
      </span>
    </sslext:link> &gt;
    <span class="pagetitle-md"><bean:message key="page.title.new.visit.confirmation"/></span>
    <br/><br/>
    <bean:message key="message.record.saved"/>
    <!-- END Breadcrumbs -->
  </td>
</tr>
</table>
<!-- END Content -->

</body>
</html:html>

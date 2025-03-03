<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>

<html:html>
<head>
  <html:base/>
  <title><bean:message key="title.MedRec"/></title>
  <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>
<body bgcolor="white" topmargin="0" leftmargin="0">

<!-- Patient Header Begin -->
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
  <td bgcolor="#006666" align=left>
    <img src="images/patient_top_banner3.gif">
  </td>
  <td bgcolor="#006666">
    <div class="pageheaderpatient">
      <bean:message key="page.title.patient.registration"/>
    </div>
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

<!-- Padding for Content Start -->
<table width="100%" border="0" cellspacing="1" cellpadding="10"><tr><td>

<!-- Content -->
<p class="pagetitle-patient"><bean:message key="pagetitle.registration.successful"/></p>
<br/>
<bean:message key="message.registration.successful"/>
<br/><br/>
<html:link forward="medrec.startpage"><bean:message key="link.return.home"/></html:link>
<!-- Content END -->
</td>
</tr>
</table>
</body>
</html:html>

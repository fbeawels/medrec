<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<html:html>
<head>
  <html:base/>
  <title><bean:message key="title.MedRec"/></title>
  <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>
<body bgcolor="white" topmargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">

<!-- Admin Header Begin -->
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
	<td bgcolor="#006666" align=left>
		<img src="images/admin_top_banner3.gif">
	</td>
	<td bgcolor="#006666">
		<div class="pageheaderadmin">
			<bean:message key="page.title.admin.creation"/>
		</div>
	</td>
</tr>
<tr>
	<td bgcolor="#E9F4F0">
		<img src="images/admin_banner_photo.jpg">
	</td>
	<td bgcolor="#E9F4F0">
		<br/>
	</td>
</tr>
</table>
<!-- Admin Header End -->

<!-- Padding for Content Start -->
<table width="100%" border="0" cellspacing="1" cellpadding="10">
<tr><td>

<!-- Content -->
<p class="pagetitle-admin-left"><bean:message key="page.title.admin.creation.successful"/></p>
<br/>
<bean:message key="message.admin.creation.successful"/>
<br/><br/>
<html:link href="/admin/Login.jsp"><bean:message key="link.login"/></html:link>
<!-- Content END -->
</td></tr>
</table>
</body>
</html:html>

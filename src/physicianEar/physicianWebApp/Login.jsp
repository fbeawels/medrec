<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.utils.MedRecWebAppUtils"%>

<%
  response.setHeader("Expires", "0");
%>

<html:html>
<head>
  <title><bean:message key="title.MedRec"/></title>
  <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>
<body bgCOLOR="white" topmargin="0" leftmargin="0">

<!-- START Title Bar  -->
<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td bgcolor="#CC3300" align=left><img src="images/physician_top_banner3.gif"></td>
		<td bgcolor="#CC3300"><div class="pageheaderphysician"><bean:message key="page.title.physician.login"/></div></td>
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
<table width="100%" border="0" cellspacing="1" cellpadding="10">
  <tr>
    <td>
      <br/>
<!-- START Content  -->
      <sslext:form method="<%=MedRecWebAppUtils.getHttpMethod()%>" action="/login.do" focus="username">
      <table align=center class="tableborder" cellpadding="10" cellspacing="0" border="0">
        <tr>
          <td>
            <table align="center" border="0" cellspacing="1" cellpadding="1">
              <tr>
                <td>&nbsp;</td>
                <td>
                  <html:messages id="error" property="invalidLogin">
                    <bean:write name="error" filter="false"/>
                  </html:messages>
                </td>
              </tr>
              <tr>
                <td class="label"><bean:message key="Username"/></td>
                <td><html:text
                      property="username"
                      size="20"
                      maxlength="45"
                      value="mary@md.com"
                      tabindex="1"/>
                </td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td align="left">
                  <html:messages id="error" property="username">
                    <bean:write name="error" filter="false"/>
                  </html:messages>
                </td>
              </tr>
              <tr>
                <td class="label"><bean:message key="Password"/></td>
                <td><html:password
                      property="password"
                      size="20"
                      maxlength="10"
                      redisplay="false"
                      value="weblogic"
                      tabindex="2"/>
                </td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td align="left">
                  <html:messages id="error" property="password">
                    <bean:write name="error" filter="false"/>
                  </html:messages>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td align="middle">
            <table>
              <tr><td>
                <html:submit property="action" tabindex="3" styleClass="profilebutton">
                 <bean:message key="button.Login"/>
                </html:submit>
                </td>
                <td>
                <html:link tabindex="4" styleClass="cancelbutton" forward="medrec.startpage">
                  <bean:message key="button.Cancel"/>
                </html:link>
              </td></tr>
            <table>
          </td>
        </tr>
      </table>
    </sslext:form>
<!-- END Content  -->
    </td>
  </tr>
</table>
<!-- END Padding for Content -->


</body>
</html:html>

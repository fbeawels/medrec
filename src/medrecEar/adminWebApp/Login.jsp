<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.utils.MedRecWebAppUtils"%>

<% response.setHeader("Expires", "0"); %>

<html:html>
<head>
  <title><bean:message key="title.MedRec"/></title>
  <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>

<body bgcolor="white" topmargin="0" leftmargin="0">

<!-- Title Bar Start -->
<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <tr>
    <td bgcolor="#669900" align=left><img src="images/admin_top_banner3.gif"></td>
    <td bgcolor="#669900"><div class="pageheaderadmin"><bean:message key="page.title.admin.login"/></div></td>
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


<!-- Padding for Content Start -->
<table width="100%" border="0" cellspacing="1" cellpadding="10">
  <tr>
    <td>
      <!-- Content -->
      <br/>
      <sslext:form method="<%=MedRecWebAppUtils.getHttpMethod()%>" action="login.do" focus="username">
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
                <td>
                  <html:text property="username"
                    size="20"
                    maxlength="45"
                    tabindex="1"/>
                </td>
                <tr>
                  <td>&nbsp;</td>
                  <td align="left">
                    <html:messages id="error" property="username">
                      <bean:write name="error" filter="false"/>
                    </html:messages>
                  </td>
                </tr>
              </tr>
              <tr>
                <td class="label"><bean:message key="Password"/></td>
                <td>
                  <html:password property="password"
                    size="20"
                    maxlength="10"
                    redisplay="false"
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
    </td>
  </tr>
</table>

</body>
</html:html>

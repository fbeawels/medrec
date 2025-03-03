<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.utils.MedRecWebAppUtils"%>

<html:html>
<head>
  <title><bean:message key="title.MedRec"/></title>
  <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>
<body bgcolor="white" topmargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">

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
  <span class="pagetitle-admin"><bean:message key="page.title.admin.creation"/></span>
  <!-- END Breadcrumbs -->

  <!-- START Content -->
  <br/><br/>
  <!-- START Import Content -->
  <p class="title"><bean:message key="page.title.admin.creation"/></p>
  <table border="0" cellspacing="0" cellpadding="2">
  <tr>
    <td>&nbsp;&nbsp;</td>
    <td>
      <sslext:form method="<%=MedRecWebAppUtils.getHttpMethod()%>" action="createnewadmin.do" focus="username">
      <table align=center class="tableborder" cellpadding=10 cellspacing=0 border=0>
        <tr>
          <td>
            <table ALIGN="center" border="0" cellspacing="1" cellpadding="1">
              <tr>
                <td></td>
                <td>
                	<html:messages id="error" property="invalidUserName">
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
                  <td></td>
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
                <td></td>
                <td align="left">
                	<html:messages id="error" property="password">
                    <bean:write name="error" filter="false"/>
                  </html:messages>
                </td>
              </tr>
              <tr>
                <td class="label"><bean:message key="ConfirmPassword"/></td>
                <td>
                  <html:password property="confirmpassword"
                    size="20"
                    maxlength="10"
                    redisplay="false"
                    tabindex="2"/>
                </td>
              </tr>
              <tr>
                <td></td>
                <td align="left">
                	<html:messages id="error" property="confirmpassword">
                    <bean:write name="error" filter="false"/>
                  </html:messages>
                </td>
              </tr>

              <tr>
                <td></td>
                <td>
                  <BR>
                    <html:submit property="action" tabindex="3" styleClass="profilebutton">
                     <bean:message key="button.Create"/>
                    </html:submit>
                    <html:cancel property="action" tabindex="4" styleClass="cancelbutton">
                      <bean:message key="button.Cancel"/>
                    </html:cancel>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      </sslext:form>
    </td>
  </tr>
  </table>
<!-- END Visit Content -->
<!-- END Padding for Content -->
    </td>
  </tr>
</table>
</body>
</html:html>

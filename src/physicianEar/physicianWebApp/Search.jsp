<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.utils.MedRecWebAppUtils"%>

<html:html>
<head>
  <title><bean:message key="title.MedRec"/> - <bean:message key="title.physician.app"/> - <bean:message key="title.patient.search"/></title>
  <link rel="stylesheet" type="text/css" href="stylesheet.css">
  <SCRIPT ID=clientEventHandlersJS LANGUAGE=javascript>
  </SCRIPT>
  <html:base/>
</head>

<body bgcolor="white" topmargin="0" leftmargin="0">

<!-- START Header -->
<jsp:include page="PhysicianHeader.jsp" flush="true"/>
<!-- START Logout -->
<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td class="physicianbanner3" valign=middle><img src="images/clear.gif" height=1 width=450></td>
		<td class="physicianbanner3"><img src="images/clear.gif"><table width="100%" cellpadding="0" cellspacing=4 border="0">
				<tr>
					<td><img src="images/clear.gif" width=10 height=1></td>
					<td><img src="images/clear.gif" width="5" height="1" alt="" border="0"></td>
					<td><sslext:link styleClass="logoutbutton" page="/logout.do"><bean:message key="button.Logout"/></sslext:link></td>
					<td><img src="images/clear.gif" width="10" height="1" alt="" border="0"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<!-- END Logout -->
<!-- END Header -->

<!-- START Padding for Content -->
<table width="100%" border="0" cellspacing="1" cellpadding="10">
<tr>
  <td>
    <!-- Content -->
    <br/>
    <br/>
    <sslext:form method="<%=MedRecWebAppUtils.getHttpMethod()%>" action="/searchresults.do" focus="lastName">
    <td align=left class="pagetitle-admin"><bean:message key="patient.lookup"/></p>
    <table border="0" cellspacing="1" cellpadding="6" align=center class="tableborder">
    <tr>
      <td>
        <table border="0" cellspacing="1" cellpadding="1">
        <tr>
          <td style=instructions colspan="2"><bean:message key="descr.patient.search"/>
            <br/><br/>
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>
            <html:messages id="error" property="multipleEntries">
              <bean:write name="error" filter="false"/>
            </html:messages>
          </td>
        <tr>
          <td class="label"><bean:message key="LastName"/></td>
          <td>
            <html:text
              name="searchBean"
              property="lastName"
              style="width: 210px; HEIGHT: 22px" size="30"
              maxlength="30"
              tabindex="1"/>
          </td>
        </tr>
        <tr>
          <td></td>
          <td><bean:message key="or"/></td>
        </tr>
        <tr>
          <td class="label"><bean:message key="SocialSecurityNumber"/></td>
          <td>
            <html:text
              name="searchBean"
              property="ssn"
              style="width: 80px; HEIGHT: 22px" size="9"
              maxlength="9"
              tabindex="2"/>
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>
            <html:messages id="error" property="ssn">
              <bean:write name="error" filter="false"/>
            </html:messages>
          </td>
        </tr>
        <tr>
          <td></td>
          <td>
            <html:submit property="action" tabindex="3" styleClass="profilebutton">
              <bean:message key="button.Search"/>
            </html:submit>
          </td>
        </tr>
        </table>
      </td>
    </tr>
    </table>
    </sslext:form>
    <!-- END Content -->

  </td>
</tr>
</table>
<!-- END Padding for Content -->


</body>
</html:html>

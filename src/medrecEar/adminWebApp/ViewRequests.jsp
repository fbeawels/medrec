<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>

<html:html>
<head>
  <title><bean:message key="title.MedRec"/></title>
  <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>
<body bgcolor="white" topmargin="0" leftmargin="0">

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
<span class="pagetitle-admin"><bean:message key="page.title.view.pending.requests"/></span>
<!-- END Breadcrumbs -->
<br/>
<br/>

<table border="0" cellspacing="0" cellpadding="2">
  <tr><td>&nbsp;&nbsp;</td><td>
  <table border="1" cellspacing="0" cellpadding="2">
    <tr>
      <td class="coltitle-admin"><bean:message key="From"/></td>
      <td class="coltitle-admin"><bean:message key="UserType"/></td>
      <td class="coltitle-admin"><bean:message key="RequestType"/></td>
      <td class="coltitle-admin"><bean:message key="Action"/></td>
    </tr>
    <bean:define id="patientApprovalBeans" name="PatientApprovalBeans" scope="request"/>
    <bean:size id="size" name="patientApprovalBeans"/>
    <logic:equal name="size" value="0" >
      <tr><td colspan=4><bean:message key="message.no.records"/></td></tr>
    </logic:equal>
    <logic:greaterThan name="size" value="0" >
      <logic:iterate id="patientApprovalBean" name="PatientApprovalBeans" type="com.bea.medrec.beans.PatientApprovalBean">
        <tr>
          <td>
            <bean:write name="patientApprovalBean" property="name"/>
          </td>
          <td>
            <bean:message key="Patient"/>
          </td>
          <td>
            <bean:message key="NewUserRegistration"/>
          </td>
          <td>
            <sslext:link page="/viewpatientrequest.do" paramId="patientId"
              paramName="patientApprovalBean" paramProperty="id">
              <bean:message key="link.view.request"/>
            </sslext:link>
          </td>
        </tr>
      </logic:iterate>
    </logic:greaterThan>

  </table>
</table>
<!-- Content END -->

<!-- End Padding for Content -->
    </td>
  </tr>
</table>
</body>
</html:html>

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
      <sslext:link page="/viewrequests.do"><span class="pagetitle-admin"><bean:message key="link.view.pending.requests"/></span></sslext:link> &gt;
      <span class="pagetitle-admin"><bean:message key="page.title.patient.profile"/></span>
      <!-- END Breadcrumbs -->
<br/><br/>


<table border="0" cellspacing="2" cellpadding="3">
  <tr class="row1">
    <td class="label"><bean:message key="FirstName"/></td>
    <td><bean:write name="patientBean" property="firstName"/></td>
  </tr>
  <tr class="row2">
    <td class="label"><bean:message key="LastName"/></td>
    <td><bean:write name="patientBean" property="lastName"/></td>
  </tr>
  <tr class="row1">
    <td class="label"><bean:message key="MiddleName"/></td>
    <td><bean:write name="patientBean" property="middleName"/></td>
  </tr>
  <tr class="row2">
    <td class="label"><bean:message key="Gender"/></td>
    <td><bean:write name="patientBean" property="gender"/></td>
  </tr>
  <tr class="row1">
    <td class="label"><bean:message key="DateOfBirth"/></td>
    <td><bean:write name="patientBean" property="dob"/></td>
  </tr>
  <tr class="row2">
    <td class="label"><bean:message key="SocialSecurityNumber"/></td>
    <td><bean:write name="patientBean" property="ssn"/></td>
  </tr>
  <tr class="row1">
    <td class="label"><bean:message key="Phone"/></td>
    <td><bean:write name="patientBean" property="phone"/></td>
  </tr>
  <tr class="row2">
    <td class="label"><bean:message key="Email"/></td>
    <td><bean:write name="patientBean" property="email"/></td>
  </tr>
  <tr class="row1">
    <td class="label"><bean:message key="Address"/></td>
    <td><bean:write name="patientBean" property="address.streetName1"/>
    <br/><bean:write name="patientBean" property="address.streetName2"/>
    <br/><bean:write name="patientBean" property="address.city"/>
    ,&nbsp;<bean:write name="patientBean" property="address.state"/>
    &nbsp;&nbsp;<bean:write name="patientBean" property="address.zipCode"/>
    <br/><bean:write name="patientBean" property="address.country"/>
    </td>
  </tr>
  <tr>
    <td></td>
    <td colspan="2" align="center">
      <br/>
      <sslext:form method="<%=MedRecWebAppUtils.getHttpMethod()%>" action="/approve.do">
        <html:submit property="action">
          <bean:message key="button.Approve"/>
        </html:submit>
        <html:submit property="action">
          <bean:message key="button.Deny"/>
        </html:submit>
        <html:cancel property="action">
          <bean:message key="button.Cancel"/>
        </html:cancel>
      </sslext:form>
    </td>
  </tr>
</table>


<!-- Content END -->

<!-- End Padding for Content -->
    </td>
  </tr>
</table>
</body>
</html:html>

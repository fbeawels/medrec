<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>

<html:html>
<head>
  <html:base/>
  <title><bean:message key="title.MedRec"/></title>
  <link rel="stylesheet" type="text/css" HREF="stylesheet.css">
</head>
<body bgcolor="white" topmargin="0" leftmargin="0">

<!-- START Header -->
<jsp:include page="Header.jsp" flush="true"/>
<!-- END Header -->

<!-- START Padding for Content -->
<table width="100%" border="0" cellspacing="1" cellpadding="10">
<tr>
  <td>

<!-- START Content -->
<sslext:link page="/medicalrecord.do"><span class="pagetitle-patient"><bean:message key="link.home"/></span></sslext:link> &gt;
<span class="pagetitle-patient"><bean:message key="page.title.patient.profile"/></span>
<br/><br/>

<table border="0" cellspacing="1" cellpadding="6" align=center class="tableborder">
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
    <td class="label"><bean:message key="DOB"/></td>
    <td><bean:write name="patientBean" property="dob"/></td>
  </tr>
  <tr class="row2">
    <td class="label"><bean:message key="SSN"/></td>
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
    <td>
      <br/>
      <sslext:link page="/editprofile.do" styleClass="profilebutton"><bean:message key="link.edit.profile"/></sslext:link>
    </td>
  </tr>
</table>
<!-- END Content -->

  </tr>
</table>
<!-- END Padding for Content -->

</body>
</html:html>

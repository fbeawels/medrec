<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.utils.MedRecWebAppUtils,
                 com.bea.medrec.actions.PhysicianConstants" %>

<html:html>
<head>
  <title><bean:message key="title.MedRec"/> - <bean:message key="title.patient.profile"/></title>
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
    <sslext:link page="/search.do" ><span class="pagetitle-md"><bean:message key="link.search"/></span></sslext:link> &gt;
    <sslext:link page="/medicalrecord.do" ><span class="pagetitle-md"><bean:message key="page.title.patient.record"/></span></sslext:link> &gt;
    <span class="pagetitle-md"><bean:message key="page.title.patient.profile"/></span>
    <br/><br/>

    <table border="0" cellspacing="1" cellpadding="6" align=center class="tableborder">
      <tr class="row1">
        <td class="label"><bean:message key="FirstName"/></td>
        <td><bean:write name="<%=PhysicianConstants.PATIENT_BEAN%>" property="firstName"/></td>
      </tr>
      <tr class="row2">
        <td class="label"><bean:message key="LastName"/></td>
        <td><bean:write name="<%=PhysicianConstants.PATIENT_BEAN%>" property="lastName"/></td>
      </tr>
      <tr class="row1">
        <td class="label"><bean:message key="MiddleName"/></td>
        <td><bean:write name="<%=PhysicianConstants.PATIENT_BEAN%>" property="middleName"/></td>
      </tr>
      <tr class="row2">
        <td class="label"><bean:message key="Gender"/></td>
        <td><bean:write name="<%=PhysicianConstants.PATIENT_BEAN%>" property="gender"/></td>
      </tr>
      <tr class="row1">
        <td class="label"><bean:message key="DateOfBirth"/></td>
        <td><bean:write name="<%=PhysicianConstants.PATIENT_BEAN%>" property="dob"/></td>
      </tr>
      <tr class="row2">
        <td class="label"><bean:message key="SocialSecurityNumber"/></td>
        <td><bean:write name="<%=PhysicianConstants.PATIENT_BEAN%>" property="ssn"/></td>
      </tr>
      <tr class="row1">
        <td class="label"><bean:message key="Phone"/></td>
        <td><bean:write name="<%=PhysicianConstants.PATIENT_BEAN%>" property="phone"/></td>
      </tr>
      <tr class="row2">
        <td class="label"><bean:message key="Email"/></td>
        <td><bean:write name="<%=PhysicianConstants.PATIENT_BEAN%>" property="email"/></td>
      </tr>
      <tr class="row1">
        <td class="label"><bean:message key="Address"/></td>
        <td><bean:write name="<%=PhysicianConstants.PATIENT_BEAN%>" property="address.streetName1"/>
        <br/><bean:write name="<%=PhysicianConstants.PATIENT_BEAN%>" property="address.streetName2"/>
        <br/><bean:write name="<%=PhysicianConstants.PATIENT_BEAN%>" property="address.city"/>
        ,&nbsp;<bean:write name="<%=PhysicianConstants.PATIENT_BEAN%>" property="address.state"/>
        &nbsp;&nbsp;<bean:write name="<%=PhysicianConstants.PATIENT_BEAN%>" property="address.zipCode"/>
        <br/><bean:write name="<%=PhysicianConstants.PATIENT_BEAN%>" property="address.country"/>
        </td>
      </tr>
    </table>
  </td>
</tr>
</table>
<!-- END Content -->

</body>
</html:html>

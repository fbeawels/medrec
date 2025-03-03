<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.actions.PatientConstants" %>

<html:html>
<head>
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
<span class="pagetitle-patient"><bean:message key="page.title.home"/></span>
<br/>
<br/>

<!-- START Visit Content -->
<p class="title"><bean:message key="page.title.visits"/></p>
<table border="0" cellspacing="0" cellpadding="2">
  <tr>
    <td>&nbsp;&nbsp;</td>
    <td>
      <table border="1" cellspacing="0" cellpadding="2">
      <!-- Visit Title -->
      <!-- Note that the style 'coltitle-patient' should vary
          (coltitle-patient, coltitle-patient) for the user type -->
        <tr>
          <td class=coltitle-patient><bean:message key="Date"/></td>
          <td class=coltitle-patient><bean:message key="VisitReason"/></td>
          <td class=coltitle-patient><bean:message key="Physician"/></td>
        </tr>

      <!-- START Dynamic Record Content -->
      <bean:define id="recordBeans"
                   name="<%=PatientConstants.RECORD_BEANS%>"
                   scope="request"/>
      <bean:size id="recordSize" name="recordBeans"/>
      <logic:equal name="recordSize" value="0" >
        <tr>
          <td colspan="3"><bean:message key="message.no.records"/></td>
        </tr>
      </logic:equal>
      <logic:greaterThan name="recordSize" value="0" >
        <logic:iterate id="recordBean"
                       name="recordBeans"
                       type="com.bea.medrec.beans.RecordBean">
            <tr>
              <td>
                <sslext:link page="/record.do" paramId="id"
                  paramName="recordBean" paramProperty="id">
                  <bean:write name="recordBean" property="date"/>
                </sslext:link>
              </td>
              <td><bean:write name="recordBean" property="symptoms"/></td>
              <td><bean:write name="recordBean" property="physicianName"/></td>
            </tr>
        </logic:iterate>
      </logic:greaterThan>
      <!-- END Dynamic Record Content -->
    </table>
  </td>
  </tr>
</table>

<!-- END Visit Content -->
<br/>
<br/>
<br/>

<!-- START Prescriptions -->
<p class="title"><bean:message key="Prescriptions"/></p>
<table border="0" cellspacing="0" cellpadding="2">
  <tr><td>&nbsp;&nbsp;</td><td>
  <table border="1" cellspacing="0" cellpadding="2">
    <tr>
      <td class=coltitle-patient><bean:message key="DateStarted"/></td>
        <td class=coltitle-patient><bean:message key="Drug"/></td>
        <td class=coltitle-patient><bean:message key="Dosage"/></td>
        <td class=coltitle-patient><bean:message key="Frequency"/></td>
        <td class=coltitle-patient><bean:message key="Refills"/></td>
        <td class=coltitle-patient><bean:message key="Instructions"/></td>
      </tr>

      <!-- START Dynamic Prescription Content -->
      <bean:define id="prescriptionBeans"
                   name="<%=PatientConstants.PRESCRIPTION_BEANS%>"
                   scope="request"/>
      <bean:size id="prescriptionSize" name="prescriptionBeans"/>
      <logic:equal name="prescriptionSize" value="0" >
        <tr><td colspan="6">
          <bean:message key="message.no.prescriptions.prescribed"/>
        </td></tr>
      </logic:equal>
      <logic:greaterThan name="prescriptionSize" value="0" >
        <logic:iterate id="prescriptionBean"
                       name="prescriptionBeans"
                       type="com.bea.medrec.beans.PrescriptionBean">
          <tr>
            <td><bean:write name="prescriptionBean" property="datePrescribed"/></td>
            <td><bean:write name="prescriptionBean" property="drug"/></td>
            <td><bean:write name="prescriptionBean" property="dosage"/></td>
            <td><bean:write name="prescriptionBean" property="frequency"/></td>
            <td><bean:write name="prescriptionBean" property="refillsRemaining"/></td>
            <td><bean:write name="prescriptionBean" property="instructions"/>&nbsp;</td>
          </tr>
        </logic:iterate>
      </logic:greaterThan>
      <!-- END Dynamic Record Content -->
    </table>
  </td>
  </tr>
</table>


<!-- END Content -->
</td>
</tr>
</table>
<!-- END Padding for Content -->

</body>
</html:html>

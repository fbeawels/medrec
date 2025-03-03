<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.actions.PhysicianConstants" %>

<jsp:useBean id="recordBean" class="com.bea.medrec.beans.RecordBean" scope="request"/>

<html:html>
<head>
  <title><bean:message key="title.MedRec"/> - <bean:message key="title.physician.app"/></title>
  <Link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>
<body bgcolor="white" topmargin="0" leftmargin="0">

<!-- START Header -->
<jsp:include page="PatientHeader.jsp" flush="true"/>
<!-- END Header -->

<!-- START Padding for Content -->
<table width="100%" border="0" cellspacing="1" cellpadding="10">
<tr>
  <td>
    <!-- START Breadcrumbs -->
    <sslext:link page="/search.do"><span class="pagetitle-md"><bean:message key="page.title.physcian.home"/></span></sslext:link> &gt;
    <sslext:link page="/medicalrecord.do"><span class="pagetitle-md"><bean:message key="page.title.patient.record"/></span></sslext:link> &gt;
    <span class="pagetitle-md"><bean:message key="page.title.visit.summary"/></span>
    <!-- END Breadcrumbs -->
    <br/>
    <br/>
    <!-- START Content -->
    <!-- START Record Table -->
    <table border="0" cellspacing="1" cellpadding="2">
      <!-- START Date -->
      <tr class="row1">
  <td class="label"><bean:message key="Date"/></td>
  <td><bean:write name="<%=PhysicianConstants.RECORD_BEAN%>" property="date"/></td>
      </tr>
      <!-- END Date -->
      <!-- START Reason for Visit -->
      <tr class="row2">
  <td class="label"><bean:message key="VisitReason"/></td>
  <td><bean:write name="<%=PhysicianConstants.RECORD_BEAN%>" property="symptoms"/></td>
      </tr>
      <!-- END Reason for Visit -->
      <tr>
        <td></td>
        <td>&nbsp;</td>
      </tr>
      <!-- START Vital Signs -->
      <tr class="row1">
        <td class="label"><bean:message key="VitalSigns"/></td>
        <td>
          <table border="0" cellspacing="1" cellpadding="3">
            <bean:define id="vitalSignsBean" name="<%=PhysicianConstants.RECORD_BEAN%>" property="vitalSignsBean" scope="request"/>
            <tr>
              <td ALIGN="right"><bean:message key="Temperature"/>:</td>
              <td><bean:write name="vitalSignsBean" property="temperature"/>&nbsp;F</td>
              <td ALIGN="right"><bean:message key="Weight"/>:</td>
              <td><bean:write name="vitalSignsBean" property="weight"/>&nbsp;lbs</td>
            </tr>
            <tr>
              <td ALIGN="right"><bean:message key="Pulse"/>:</td>
              <td><bean:write name="vitalSignsBean" property="pulse"/>&nbsp;bmp</td>
              <td ALIGN="right"><bean:message key="Height"/>:</td>
              <td><bean:write name="vitalSignsBean" property="height"/>&nbsp;inches</td>
            </tr>
            <tr>
              <td ALIGN="right">&nbsp;&nbsp;<bean:message key="BloodPressure"/>:</td>
              <td><bean:write name="vitalSignsBean" property="bloodPressure"/>&nbsp;</td>
              <td ALIGN="right">&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
          </table>
        <br/>
        </td>
      </tr>
      <!-- END Vital Signs -->
      <!-- START Exam Notes -->
      <tr class="row2">
        <td class="label" valign="top"><bean:message key="ExamNotes"/></td>
        <td>
          <bean:write name="<%=PhysicianConstants.RECORD_BEAN%>" property="notes"/>
          <br/>
          <br/>
        </td>
      </tr>
      <!-- END Exam Notes -->
      <!-- START Diagnosis -->
      <tr class="row1">
        <td class="label" vALIGN="top"><bean:message key="Diagnosis"/></td>
        <td>
          <bean:write name="<%=PhysicianConstants.RECORD_BEAN%>" property="diagnosis"/>
        </td>
      </tr>
      <!-- Diagnosis End -->
      <tr><td>&nbsp;</td></tr>
      <!-- START Medications Prescribed -->
      <tr class="row2">
        <td class="label"><bean:message key="MedicationsPrescribed"/></td>
        <td>
          <table border="0" cellspacing="0" cellpadding="2">
          <tr>
            <td>&nbsp;&nbsp;</td>
            <td>
              <table border="1" cellspacing="0" cellpadding="2">
                <tr>
                  <td class="patientbanner2"><bean:message key="DateStarted"/></td>
                  <td class="patientbanner2"><bean:message key="Drug"/></td>
                  <td class="patientbanner2"><bean:message key="Dosage"/></td>
                  <td class="patientbanner2"><bean:message key="Frequency"/></td>
                  <td class="patientbanner2"><bean:message key="Refills"/></td>
                  <td class="patientbanner2"><bean:message key="Instructions"/></td>
                </tr>
                <bean:define id="prescriptionBeans" name="<%=PhysicianConstants.RECORD_BEAN%>" property="prescriptionBeans" scope="request"/>
                <bean:size id="size" name="prescriptionBeans"/>
                <logic:equal name="size" value="0" >
                  <tr>
                   <td colspan="6"><bean:message key="message.no.prescriptions.prescribed"/></td>
                  </tr>
                </logic:equal>
                <logic:greaterThan name="size" value="0" >
                  <logic:iterate id="prescriptionBean" name="<%=PhysicianConstants.RECORD_BEAN%>" property="prescriptionBeans" type="com.bea.medrec.beans.PrescriptionBean">
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
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <!-- END Medications Prescribed -->
  </table>
  <!-- END Record Table -->
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

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.actions.PhysicianConstants,
                 java.util.Map,
                 java.util.HashMap" %>

<html:html>
<head>
  <title><bean:message key="title.MedRec"/> - <bean:message key="title.physician.app"/></title>
  <link rel="stylesheet" type="text/css" HREF="stylesheet.css">
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
  <sslext:link page="/search.do">
    <span class="pagetitle-md">
      <bean:message key="page.title.physcian.home"/>
    </span>
  </sslext:link> &gt;
  <span class="pagetitle-md"><bean:message key="page.title.patient.record"/></span>
  <!-- END Breadcrumbs -->

  <!-- START Content -->
  <br/><br/>
<%
  Map m = new HashMap();
  m.put(PhysicianConstants.ACTION, PhysicianConstants.NEW_VISIT);
  pageContext.setAttribute("params",m);
%>
  <sslext:link page="/medicalrecord.do">
    <bean:message key="link.refresh"/>
  </sslext:link>
  &nbsp;&nbsp;|&nbsp;&nbsp;
  <sslext:link page="/visit.do" name="params" scope="page">
    <bean:message key="link.new.visit"/>
  </sslext:link>
  &nbsp;&nbsp;|&nbsp;&nbsp;
  <sslext:link page="/search.do">
    <bean:message key="link.close.chart"/>
  </sslext:link>
  <br/><br/>
  <!-- START Visit Content -->
  <p class="title"><bean:message key="Visits"/></p>
  <table border="0" cellspacing="0" cellpadding="2">
  <tr>
    <td>&nbsp;&nbsp;</td>
    <td>
      <table border="1" cellspacing="0" cellpadding="2">
        <!-- Visit Title -->
        <!-- Note that the style 'coltitle-patient' should vary
            (coltitle-patient, coltitle-patient) for the user type -->
        <tr>
          <td class="patientbanner2"><bean:message key="Date"/></td>
          <td class="patientbanner2"><bean:message key="VisitReason"/></td>
          <td class="patientbanner2"><bean:message key="Physician"/></td>
        </tr>
        <!-- START Dynamic Record Content -->
        <bean:define id="recordBeans" name="<%=PhysicianConstants.RECORD_BEANS%>" scope="request"/>
        <bean:size id="recSize" name="recordBeans"/>
        <logic:equal name="recSize" value="0" >
          <tr><td colspan="3"><bean:message key="message.no.records.found"/></td></tr>
        </logic:equal>
        <logic:greaterThan name="recSize" value="0" >
          <logic:iterate id="recordBean" name="recordBeans" type="com.bea.medrec.beans.RecordBean">
            <td>
                <html:link page="/record.do" paramId="id"
                  paramName="recordBean" paramProperty="id">
                  <bean:write name="recordBean" property="date"/>
                </html:link>
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
  <table border="0" cellspacing="0" cellpadding="2"><tr><td>&nbsp;&nbsp;</td><td>
  <table border="1" cellspacing="0" cellpadding="2">
  <tr>
    <td class="patientbanner2"><bean:message key="DateStarted"/></td>
    <td class="patientbanner2"><bean:message key="Drug"/></td>
    <td class="patientbanner2"><bean:message key="Dosage"/></td>
    <td class="patientbanner2"><bean:message key="Frequency"/></td>
    <td class="patientbanner2"><bean:message key="Refills"/></td>
    <td class="patientbanner2"><bean:message key="Instructions"/></td>
  </tr>
   <!-- START Dynamic Prescription Content -->
  <bean:define id="prescriptionBeans" name="<%=PhysicianConstants.PRESCRIPTION_BEANS%>" scope="request"/>
  <bean:size id="prescriptionsSize" name="prescriptionBeans"/>
  <logic:equal name="prescriptionsSize" value="0" >
    <tr><td colspan="6"><bean:message key="message.no.prescriptions.prescribed"/></td></tr>
  </logic:equal>
  <logic:greaterThan name="prescriptionsSize" value="0" >
    <logic:iterate id="prescriptionBean" name="prescriptionBeans" type="com.bea.medrec.beans.PrescriptionBean">
    <tr>
      <td><bean:write name="prescriptionBean" property="datePrescribed"/></td>
      <td><bean:write name="prescriptionBean" property="drug"/></td>
      <td><bean:write name="prescriptionBean" property="dosage"/></td>
      <td><bean:write name="prescriptionBean" property="frequency"/></td>
      <td><bean:write name="prescriptionBean" property="refillsRemaining"/></td>
      <td><bean:write name="prescriptionBean" property="instructions"/>&nbsp</td>
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

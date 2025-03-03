<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.utils.MedRecWebAppUtils,
                 com.bea.medrec.actions.PhysicianConstants,
                 java.util.HashMap,
                 java.util.Map" %>

<html:html>
<head>
  <meta http-equiv="Pragma" content="no-cache" />
  <title><bean:message key="title.MedRec"/> - <bean:message key="title.physician.app"/></title>
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
    <!-- START Breadcrumbs -->
    <sslext:link page="/search.do" ><span class="pagetitle-md"><bean:message key="page.title.physcian.home"/></span></sslext:link> &gt;
    <sslext:link page="/medicalrecord.do" ><span class="pagetitle-md"><bean:message key="page.title.patient.record"/></span></sslext:link> &gt;
    <span class="pagetitle-md"><bean:message key="page.title.new.visit"/></span>
    <!-- END Breadcrumbs -->
    <br/><br/>

    <!-- START Form -->
    <sslext:form method="<%=MedRecWebAppUtils.getHttpMethod()%>" action="/visit.do" focus="symptoms">
      <table width="100%" border="0" cellspacing="1" cellpadding="1">
      <!-- START Date -->
      <tr>
        <td class="label"><bean:message key="Date"/></td>
        <td>
          <%=MedRecWebAppUtils.getCurrentDate()%>
        </td>
      </tr>
      <!-- END Date -->
      <!-- START Reason for Visit -->
      <tr>
        <td class="label"><bean:message key="VisitReason"/></td>
      <td>
        <html:text
          name="recordBean"
          property="symptoms"
          style="width: 420px; HEIGHT: 22px" size="77"
          maxlength="77"
          tabindex="1"/>
        </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;
          <html:messages id="error" property="symptoms">
            <bean:write name="error" filter="false"/>
          </html:messages>
        </td>
      </tr>
      <!-- END Reason for Visit -->
      <!-- START Vital Signs -->
      <tr>
        <td class="label"><bean:message key="VitalSigns"/></td>
        <td>
            <table border="0" cellspacing="1" cellpadding="1">
            <tr>
              <!-- START Temperature -->
              <td align="right" class="label">
                <bean:message key="Temperature"/>&nbsp;<font size=2pt>(F)</font>
                &nbsp;
              </td>
              <td><html:text
                   name="recordBean"
                   property="vitalSignsBean.temperature"
                   style="width: 55px; HEIGHT: 22px" size="7"
                   maxlength="5"
                   tabindex="2"/>
              </td>
              <!-- END Temperature -->
              <!-- START Weight -->
              <td align="right" class="label">
                <bean:message key="Weight"/>&nbsp;<font size=2pt>(lbs)</font>
                &nbsp;
              </td>
              <td><html:text
                   name="recordBean"
                   property="vitalSignsBean.weight"
                   style="width: 55px; HEIGHT: 22px" size="7"
                   maxlength="3"
                   tabindex="3"/>
              </td>
              <!-- END Weight -->
            </tr>
            <tr>
              <!-- START Temperature Errors -->
              <td colspan="2" align="right">&nbsp;
                <html:messages id="error" property="vitalSignsBean.temperature">
                  <bean:write name="error" filter="false"/>
                </html:messages>
              </td>
              <!-- END Temperature Errors -->
              <!-- START Weight Errors -->
              <td colspan="2" align="right">
                &nbsp;<html:messages id="error" property="vitalSignsBean.weight">
                  <bean:write name="error" filter="false"/>
                </html:messages>
              </td>
              <!-- END Weight Errors -->
            </tr>
            <tr>
              <!-- START Pulse -->
              <td align="right" class="label">
                <bean:message key="Pulse"/>&nbsp;<font size=2pt>(bpm)</font>
                &nbsp;
              </td>
              <td><html:text
                   name="recordBean"
                   property="vitalSignsBean.pulse"
                   style="width: 55px; HEIGHT: 22px" size="7"
                   maxlength="3"
                   tabindex="4"/>
              </td>
              <!-- END Pulse -->
              <!-- START Height -->
              <td align="right" class="label">
                <bean:message key="Height"/>&nbsp;<font size=2pt>(inches)</font>
                &nbsp;
              </td>
              <td><html:text
                   name="recordBean"
                   property="vitalSignsBean.height"
                   style="width: 55px; HEIGHT: 22px" size="7"
                   maxlength="2"
                   tabindex="5"/>
              </td>
              <!-- END Height -->
            </tr>
            <tr>
              <!-- START Pulse Errors -->
              <td colspan="2" align="right">&nbsp;
                <html:messages id="error" property="vitalSignsBean.pulse">
                  <bean:write name="error" filter="false"/>
                </html:messages>
              </td>
              <!-- END Pulse Errors -->
              <!-- START Height Errors -->
              <td colspan="2" align="right">&nbsp;
                <html:messages id="error" property="vitalSignsBean.height">
                  <bean:write name="error" filter="false"/>
                </html:messages>
              </td>
              <!-- END Height Errors -->
            </tr>
            <tr>
              <!-- START Blood Pressure -->
              <td align="right" class="label">
                <bean:message key="BloodPressure"/>&nbsp;<font size=2pt>(systolic/diastolic)</font>
                &nbsp;
              </td>
              <td><html:text
                   name="recordBean"
                   property="vitalSignsBean.bloodPressure"
                   style="width: 66px; HEIGHT: 22px" size="9s"
                   maxlength="7"
                   tabindex="6"/>
              </td>
              <!-- END Blood Pressure -->
              <td colspan="2">&nbsp;</td>
            <tr>
              <!-- START Blood Pressure Errors -->
              <td colspan="2" align="right">&nbsp;
                <html:messages id="error" property="vitalSignsBean.bloodPressure">
                  <bean:write name="error" filter="false"/>
                </html:messages>
              </td>
              <!-- END Blood Pressure Errors -->
              <td colspan="2">&nbsp;</td>
            </tr>
            </tr>
            </table>
        </td>
      </tr>
      <!-- END Vital Signs -->
      <!-- START Exam Notes -->
      <tr>
        <td class="label" valign="top"><bean:message key="ExamNotes"/></td>
        <td>
          <html:textarea
           name="recordBean"
           style="width: 420px; HEIGHT: 90px"
           rows="15" cols="77"
           property="notes"
           tabindex="7"/>
        </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;
          <html:messages id="error" property="notes">
            <bean:write name="error" filter="false"/>
          </html:messages>
        </td>
      </tr>
      <!-- END Exam Notes -->
      <!-- START Diagnosis -->
      <tr>
        <td class="label" valign="top"><bean:message key="Diagnosis"/></td>
        <td>
          <html:textarea
           name="recordBean"
           style="width: 420px; HEIGHT: 90px"
           rows="15" cols="77"
           property="diagnosis"
           tabindex="8"/>
        </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;
          <html:messages id="error" property="diagnosis">
            <bean:write name="error" filter="false"/>
          </html:messages>
        </td>
      </tr>
      <!-- END Diagnosis -->
      <tr>
        <td class="label"><bean:message key="MedicationsPrescribed"/></td>
        <td>
          <table border="1" cellspacing="0" cellpadding="2">
          <tr>
            <td class="patientbanner2"><bean:message key="DateStarted"/></td>
            <td class="patientbanner2"><bean:message key="Drug"/></td>
            <td class="patientbanner2"><bean:message key="Dosage"/></td>
            <td class="patientbanner2"><bean:message key="Frequency"/></td>
            <td class="patientbanner2"><bean:message key="Refills"/></td>
            <td class="patientbanner2"><bean:message key="Instructions"/></td>
            <td class="patientbanner2"><bean:message key="Keep"/></td>
          </tr>
          <bean:define id="prescriptionBeans" name="recordBean"
            property="prescriptionBeans" scope="session"/>
          <bean:size id="size" name="prescriptionBeans"/>
          <logic:equal name="size" value="0" >
            <tr>
              <td colspan="7"><bean:message key="message.no.prescriptions.prescribed"/></td>
            </tr>
          </logic:equal>
          <logic:greaterThan name="size" value="0" >
            <% int i=0; %>
            <logic:iterate id="prescriptionBean" name="recordBean"
              property="prescriptionBeans"
              type="com.bea.medrec.beans.PrescriptionBean" scope="session">
              <tr>
                <td><bean:write name="prescriptionBean" property="datePrescribed"/></td>
                <td><bean:write name="prescriptionBean" property="drug"/></td>
                <td><bean:write name="prescriptionBean" property="dosage"/></td>
                <td><bean:write name="prescriptionBean" property="frequency"/></td>
                <td><bean:write name="prescriptionBean" property="refillsRemaining"/></td>
                <td><bean:write name="prescriptionBean" property="instructions"/>&nbsp;</td>
<%
  Map m = new HashMap();
  m.put(PhysicianConstants.ACTION, PhysicianConstants.DELETE_PRESCRIPTION);
  m.put(PhysicianConstants.PRESCRIPTION_ID, String.valueOf(i++));
  pageContext.setAttribute("params",m);
%>
                <td><sslext:link page="/visit.do" name="params" scope="page">Delete</sslext:link></td>
              </tr>
            </logic:iterate>
          </logic:greaterThan>
          </table>
        </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>
          <br/>
          <html:submit property="action" tabindex="9">
            <bean:message key="button.Prescribe.Medication"/>
          </html:submit>
        </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>
          <br/>
          <html:submit property="action" tabindex="10" styleClass="graybutton">
            <bean:message key="button.Save"/>
          </html:submit>
          <html:submit property="action" tabindex="11" styleClass="graybutton">
            <bean:message key="button.Reset"/>
          </html:submit>
          <html:submit property="action" tabindex="12" styleClass="graybutton">
            <bean:message key="button.Cancel"/>
          </html:submit>

        </td>
      </tr>
    </table>
  </sslext:form>
  <!-- END Form -->

  </td>
</tr>
</table>
<br/>
<br/>
<!-- END Content -->

</body>
</html:html>

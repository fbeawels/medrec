<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.actions.PatientConstants" %>

<!-- Patient Header Begin -->
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
  <td bgcolor="#006666" align=left>
    <img src="images/patient_top_banner3.gif">
  </td>
  <td bgcolor="#006666">
    <div class="pageheaderpatient">
      <bean:message key="patient.info"/>
    </div>
  </td>
</tr>
<tr>
  <td bgcolor="#E9F4F0">
    <img src="images/patient_banner_photo.jpg">
  </td>
  <td bgcolor="#E9F4F0">
    <br/>
  </td>
</tr>
</table>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
  <td class="patientname" valign=middle align=left>
    <bean:message key="patient.info"/>:
    <bean:write name="<%=PatientConstants.PATIENT_BEAN%>" property="lastName" scope="session"/>,&nbsp;
    <bean:write name="<%=PatientConstants.PATIENT_BEAN%>" property="firstName" scope="session"/>&nbsp;
    <bean:write name="<%=PatientConstants.PATIENT_BEAN%>" property="middleName" scope="session"/>
  </td>
  <td class="patientdob"><bean:message key="DOB.header"/>:&nbsp;<bean:write name="<%=PatientConstants.PATIENT_BEAN%>" property="dob" scope="session"/></td>
  <td class="patientbanner" valign=middle>
    <table width="100%" cellpadding="0" cellspacing=4 border="0">
      <tr>
        <td><img src="images/clear.gif" width=10 height=1></td>
        <td><sslext:link page="/viewprofile.do" styleClass="profilebutton"><bean:message key="link.profile"/></sslext:link></td>
        <td><img src="images/clear.gif" width="5" height="1" alt="" border="0"></td>
        <td>
        <sslext:link page="/logout.do" styleClass="logoutbutton"><bean:message key="link.logout"/></sslext:link>
        </td>
        <td><img src="images/clear.gif" width="10" height="1" alt="" border="0"></td>
      </tr>
    </table>
  </td>
</tr>
</table>

<!-- Patient Header End -->

<table class="tab-color" width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><IMG SRC="images/transparent.gif" width="1" HEIGHT="4"/></td>
  </tr>
</table>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.actions.PhysicianConstants" %>

<!-- START Title Bar -->
<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <tr>
    <td bgcolor="#CC3300" align=left><img src="images/physician_top_banner3.gif"></td>
    <td bgcolor="#CC3300"><div class="pageheaderphysician">Dr.&nbsp;<bean:write name="<%=PhysicianConstants.PHYSICIAN_BEAN%>" property="firstName" scope="session"/>&nbsp;<bean:write name="<%=PhysicianConstants.PHYSICIAN_BEAN%>" property="lastName" scope="session"/></div></td>
  </tr>

  <tr>
    <td bgcolor="#FFFFCC"><img src="images/physician_banner_photo.jpg" width="354" height="65"></td>
    <td bgcolor="#FFFFCC"><br/></td>
  </tr>

  <tr>
    <td bgcolor="#FFFFFF" height=1 colspan=2><img src="images/clear.gif" width="100" height="1" alt="" border="0"></td>
  </tr>
</table>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <tr>
    <td class="patientname2" valign=middle align=left><bean:message key="patient.info"/>: <bean:write name="<%=PhysicianConstants.PATIENT_BEAN%>" property="lastName" scope="session"/>,
      <bean:write name="<%=PhysicianConstants.PATIENT_BEAN%>" property="firstName" scope="session"/>
      <bean:write name="<%=PhysicianConstants.PATIENT_BEAN%>" property="middleName" scope="session"/>
    </td>
    <td class="patientdob2"><bean:message key="DOB.header"/>: <bean:write name="<%=PhysicianConstants.PATIENT_BEAN%>" property="dob" scope="session"/></td>
    <td class="patientbanner2" valign=middle>
      <table width="100%" cellpadding="0" cellspacing=4 border="0">
        <tr>
          <td><img src="images/clear.gif" width="10" height="1" alt="" border="0"></td>
          <td><sslext:link page="/profile.do" styleClass="profilebutton"><bean:message key="link.profile"/></sslext:link></td>
          <td><img src="images/clear.gif" width="5" height="1" alt="" border="0"></td><td>
          <sslext:link styleClass="logoutbutton" page="/logout.do"><bean:message key="link.logout"/></sslext:link></td>
          <td><img src="images/clear.gif" width="10" height="1" alt="" border="0"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>

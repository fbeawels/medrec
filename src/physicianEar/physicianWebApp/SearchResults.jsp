<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page session="true"%>

<% response.setHeader("Expires", "0"); %>

<html:html>
<head>
  <title><bean:message key="title.MedRec"/> - <bean:message key="title.physician.app"/> - <bean:message key="title.patient.search.results"/></title>
  <link rel="stylesheet" type="text/css" href="stylesheet.css">
  <html:base/>
</meta>
</head>

<body bgcolor="white" topmargin="0" leftmargin="0">

<!-- START Header -->
<jsp:include page="PhysicianHeader.jsp" flush="true"/>
<!-- START Logout -->
<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <tr>
    <td class="physicianbanner3" valign=middle><img src="images/clear.gif" height=1 width=450></td>
    <td class="physicianbanner3"><img src="images/clear.gif"><table width="100%" cellpadding="0" cellspacing=4 border="0">
        <tr>
          <td><img src="images/clear.gif" width="10" height="1"></td>
          <td><img src="images/clear.gif" width="5" height="1" alt="" border="0"></td>
          <td><sslext:link page="/logout.do" styleClass="logoutbutton"><bean:message key="button.Logout"/></sslext:link></td>
          <td><img src="images/clear.gif" width="10" height="1" alt="" border="0"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<!-- END Logout -->
<!-- END Header -->

<!-- START Padding for Content -->
<table width="100%" border="0" cellspacing="1" cellpadding="10">
<tr>
  <td>
  <!-- START Breadcrumbs -->
  <sslext:link page="/search.do"><span class="pagetitle-md"><bean:message key="page.title.physcian.home"/></span></sslext:link> &gt;
  <span class="pagetitle-md"><bean:message key="page.title.seach.results"/></span>
  <!-- END Breadcrumbs -->
  <br/><br/><br/><br/>

<!-- START Content -->
<p class="title"><bean:message key="page.title.seach.results"/></p>
  <table border="0" cellspacing="0" cellpadding="2" ALIGN="CENTER">
  <tr><td>&nbsp;&nbsp;</td>
    <td>
      <table border="1" cellspacing="0" cellpadding="3">
        <tr>
          <td class="patientbanner2"><bean:message key="Result"/></td>
          <td class="patientbanner2"><bean:message key="LastName"/></td>
          <td class="patientbanner2"><bean:message key="FirstName"/></td>
          <td class="patientbanner2"><bean:message key="MiddleName"/></td>
          <td class="patientbanner2"><bean:message key="Gender"/></td>
          <td class="patientbanner2"><bean:message key="DOB"/></td>
          <td class="patientbanner2"><bean:message key="SSN"/></td>
        </tr>
        <bean:size id="size" name="patientCollection"/>
        <logic:equal name="size" value="0" >
          <tr>
            <td colspan=7><bean:message key="message.no.patients.found"/></td>
          </tr>
        </logic:equal>
        <logic:greaterThan name="size" value="0" >
          <% int i=0; %>
          <logic:iterate id="patientBean" name="patientCollection" type="com.bea.medrec.beans.PatientBean" scope="session">
            <tr>
              <td><center><%=++i%></center></td>
              <td>
                <html:link page="/medicalrecord.do" paramId="id"
                  paramName="patientBean" paramProperty="id">
                  <bean:write name="patientBean" property="lastName"/>
                </html:link>
              &nbsp;</td>
              <td><bean:write name="patientBean" property="firstName"/>&nbsp;</td>
              <td><bean:write name="patientBean" property="middleName"/>&nbsp;</td>
              <td><bean:write name="patientBean" property="gender"/>&nbsp;</td>
              <td><bean:write name="patientBean" property="dob"/>&nbsp;</td>
              <td><bean:write name="patientBean" property="ssn"/>&nbsp;</td>
            </tr>
          </logic:iterate>
         </logic:greaterThan>
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

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.utils.MedRecWebAppUtils" %>

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
    <!--  FIXME - Cancel alert needed.  -->
    <!-- START Breadcrumbs -->
    <sslext:link page="/search.do" ><span class="pagetitle-md"><bean:message key="page.title.physcian.home"/></span></sslext:link> &gt;
    <sslext:link page="/medicalrecord.do" ><span class="pagetitle-md"><bean:message key="page.title.patient.record"/></span></sslext:link> &gt;
    <sslext:link page="/visit.do" ><span class="pagetitle-md"><bean:message key="page.title.new.visit"/></span></sslext:link> &gt;
    <span class="pagetitle-md"><bean:message key="page.title.new.prescription"/></span>
    <!-- END Breadcrumbs -->
    <br/><br/>

    <!-- START Form -->
    <sslext:form method="<%=MedRecWebAppUtils.getHttpMethod()%>" action="prescription.do" focus="drug">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
      <tr>
        <td class="label" colspan="2" align="left"><bean:message key="all.fields.required"/><br/><br/></td>
      </tr>
      <!-- START Date -->
      <tr>
        <td class="label">Date</td>
        <td>
          <%=MedRecWebAppUtils.getCurrentDate()%>
        </td>
      </tr>
      <!-- END Date -->
      <!-- START Medication -->
      <tr>
        <td class="label"><bean:message key="Medication"/></td>
        <td>
          <html:text
            property="drug"
            style="width: 250px; HEIGHT: 22px" size="30"
            maxlength="30"
            tabindex="1"/>
        </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>
          <html:messages id="error" property="drug">
            <bean:write name="error" filter="false"/>
          </html:messages>&nbsp;
        </td>
      </tr>
      <!-- END Medication -->
      <!-- START Dosage -->
      <tr>
        <td class="label"><bean:message key="Dosage"/></td>
        <td>
          <html:text
            property="dosage"
            style="width: 250px; HEIGHT: 22px" size="30"
            maxlength="30"
            tabindex="2"/>
        </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>
          <html:messages id="error" property="dosage">
            <bean:write name="error" filter="false"/>
          </html:messages>&nbsp;
        </td>
      </tr>
      <!-- END Dosage -->
      <!-- START Frequency -->
      <tr>
        <td class="label"><bean:message key="Frequency"/></td>
        <td>
          <html:text
            property="frequency"
            style="width: 250px; HEIGHT: 22px" size="30"
            maxlength="30"
            tabindex="3"/>
        </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>
          <html:messages id="error" property="frequency">
            <bean:write name="error" filter="false"/>
          </html:messages>&nbsp;
        </td>
      </tr>
      <!-- END Frequency -->
      <!-- START Refills -->
      <tr>
        <td class="label"><bean:message key="Refills"/>&nbsp;(#)</td>
        <td>
          <html:text
            property="refillsRemaining"
            style="width: 50px; HEIGHT: 22px" size="5"
            maxlength="5"
            tabindex="4"/>
        </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>
          <html:messages id="error" property="refillsRemaining">
            <bean:write name="error" filter="false"/>
          </html:messages>
          &nbsp;
        </td>
      </tr>
      <!-- END Refills -->
      <!-- START Instructions -->
      <tr>
        <td class="label" valign="top"><bean:message key="Instructions"/>
          &nbsp;<font size=2pt>(<bean:message key="optional"/>)</font>
        </td>
        <td>
          <html:textarea
            style="width: 420px; HEIGHT: 90px"
            rows="15" cols="77"
            property="instructions"
            tabindex="5"/>
          <br/>
        </td>
      </tr>
      <!-- END Instructions -->
      <tr>
        <td>&nbsp;</td>
        <td>
          <br/><br/>
          <html:submit property="actionPrescription" tabindex="6" styleClass="graybutton">
            <bean:message key="button.Save"/>
          </html:submit>
          <html:submit property="actionPrescription" tabindex="7" styleClass="graybutton">
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
<!-- END Content -->

</body>
</html:html>

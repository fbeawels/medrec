<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.utils.MedRecWebAppUtils"%>

<html:html>
<head>
  <html:base/>
  <title><bean:message key="title.MedRec"/></title>
  <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>
<body bgColor="white" leftMargin="0" topMargin="0" rightMargin="0" MARGINHEIGHT="0" MARGINwidth="0">

<!-- Patient Header Begin -->
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
	<td bgcolor="#006666" align=left>
		<img src="images/patient_top_banner3.gif">
	</td>
	<td bgcolor="#006666">
		<div class="pageheaderpatient">
			<bean:message key="page.title.patient.registration"/>
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
<!-- Patient Header End -->

<!-- Padding for Content Start -->
<table width="100%" border="0" cellspacing="1" cellpadding="10">
<tr><td>

<!-- Content -->
<sslext:form method="<%=MedRecWebAppUtils.getHttpMethod()%>" action="/register.do" focus="userBean.username">
<table cellSpacing="0" cellPadding=10 border="1">
<tr>
  <td>
    <table cellSpacing="1" cellPadding="1" align="center" border="0">
    <tr>
      <td colspan="5"><b><bean:message key="message.all.fields.required"/></b><br/></td>
    </tr>
    <tr>
      <td class="label"><bean:message key="Email"/></td>
      <td>
        <html:text
          name="registrationBean"
          property="userBean.username"
          size="20"
          maxlength="45"
          redisplay="false"
          tabindex="1"/>
      </td>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
      <td class="label"><bean:message key="Address"/></td>
      <td></td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <html:messages id="error" property="userBean.username">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
    </tr>
    <tr>
      <td class="label"><bean:message key="Password"/> <br/><font size=1pt>(8-10 chars)</font></td>
      <td>
        <html:password
          name="registrationBean"
          property="userBean.password"
          size="20"
          maxlength="10"
          redisplay="false"
          tabindex="2"/>
      </td>
      <td></td>
      <td class="label"><bean:message key="Street"/></td>
      <td>
        <html:text
          name="registrationBean"
          property="patientBean.address.streetName1"
          size="20"
          maxlength="60"
          tabindex="10"/>
      </td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <html:messages id="error" property="userBean.password">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
      <td></td>
      <td></td>
      <td align="left">
        <html:messages id="error" property="patientBean.address.streetName1">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
    </tr>
    <tr>
      <td class="label"><bean:message key="FirstName"/></td>
      <td>
        <html:text
          name="registrationBean"
          property="patientBean.firstName"
          size="20"
          maxlength="60"
          tabindex="3"/>
      </td>
      <td></td>
      <td></td>
      <td>
        <html:text
          name="registrationBean"
          property="patientBean.address.streetName2"
          size="20"
          maxlength="60"
          tabindex="11"/>
      </td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <html:messages id="error" property="patientBean.firstName">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
      </td>
    </tr>
    <tr>
      <td class="label"><bean:message key="MiddleName"/></td>
      <td>
        <html:text
          name="registrationBean"
          property="patientBean.middleName"
          size="20"
          maxlength="60"
          tabindex="4"/>
      </td>
      <td></td>
      <td><b><bean:message key="City"/></b></td>
      <td>
        <html:text
          name="registrationBean"
          property="patientBean.address.city"
          size="20"
          maxlength="60"
          tabindex="12"/>
      </td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <html:messages id="error" property="patientBean.middleName">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
      </td>
      <td></td>
      <td></td>
      <td align="left">
        <html:messages id="error" property="patientBean.address.city">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
      </td>
    </tr>
    <tr>
      <td><b><bean:message key="LastName"/></b></td>
      <td>
        <html:text
          name="registrationBean"
          property="patientBean.lastName"
          size="20"
          maxlength="60"
          tabindex="5"/>
      </td>
      <td></td>
      <td class="label"><bean:message key="State"/></td>
      <td>
        <html:text
          name="registrationBean"
          property="patientBean.address.state"
          size="2"
          maxlength="2"
          tabindex="13"/>
      </td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <html:messages id="error" property="patientBean.lastName">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
      </td>
      <td></td>
      <td></td>
      <td align="left">
        <html:messages id="error" property="patientBean.address.state">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
      </td>
    </tr>
    <tr>
      <td class="label"><bean:message key="Gender"/></td>
      <td>
        <html:select name="registrationBean" property="patientBean.gender" tabindex="6">
          <html:option value=""><bean:message key="ChooseGender"/></html:option>
          <html:option value="dashes">-------------</html:option>
          <html:option value="Male" key="Male"/>
          <html:option value="Female" key="Female"/>
        </html:select>
      </td>
      <td></td>
      <td class="label"><bean:message key="Zip"/></td>
      <td>
        <html:text
          name="registrationBean"
          property="patientBean.address.zipCode"
          size="20"
          maxlength="10"
          tabindex="14"/>
      </td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <html:messages id="error" property="patientBean.gender">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
      </td>
      <td></td>
      <td></td>
      <td align="left">
        <html:messages id="error" property="patientBean.address.zipCode">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
      </td>
    </tr>
    <tr>
      <td class="label"><bean:message key="DOB"/><br/><font size=1pt>(mm/dd/yyyy)</font></td>
      <td>
        <html:text
          name="registrationBean"
          property="patientBean.dob"
          size="10"
          maxlength="10"
          tabindex="7"/>
      </td>
      <td></td>
      <td class="label"><bean:message key="Country"/></td>
      <td>
        <html:text
          name="registrationBean"
          property="patientBean.address.country"
          size="20"
          maxlength="60"
          tabindex="15"/>
      </td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <html:messages id="error" property="patientBean.dob">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
      </td>
      <td></td>
      <td></td>
      <td align="left">
        <html:messages id="error" property="patientBean.address.country">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
      </td>
    </tr>
    <tr>
      <td class="label"><bean:message key="SSN"/><br/><font size=1pt>(xxxxxxxxx)</font></td>
      <td>
        <html:text
          name="registrationBean"
          property="patientBean.ssn"
          size="9"
          maxlength="9"
          tabindex="8"/>
      </td>
      <td></td>
      <td></td>
      <td></td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <html:messages id="error" property="patientBean.ssn">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
      </td>
    </tr>
    <tr>
      <td class="label"><bean:message key="Phone"/><br/><font size=1pt>(xxx-xxx-xxxx)</font></td>
      <td>
        <html:text
          name="registrationBean"
          property="patientBean.phone"
          size="12"
          maxlength="12"
          tabindex="9"/>
      </td>
      <td></td>
      <td></td>
      <td></td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <html:messages id="error" property="patientBean.phone">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
      </td>
    </tr>
    <tr>
      <td></td>
      <td>
        <br/>
        <table>
          <tr><td>
            <html:submit property="action" tabindex="16" styleClass="profileButton">
              <bean:message key="button.Register"/>
            </html:submit>
          </td>
          <td>
            <html:link tabindex="17" styleClass="cancelbutton" forward="medrec.startpage">
              <bean:message key="button.Cancel"/>
            </html:link>
          </td></tr>
        </table>
      </td>
      <td></td>
      <td></td>
      <td></td>
    </tr>
    </table>
  </td>
</tr>
</table>
</sslext:form>
  <!-- Content END -->
</td></tr>
</table>
</body>
</HTML>
</html:html>

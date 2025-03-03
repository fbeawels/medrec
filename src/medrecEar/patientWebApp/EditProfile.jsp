<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.beans.AddressBean,
                 com.bea.medrec.utils.MedRecWebAppUtils"%>

<jsp:useBean id="patientBean" class="com.bea.medrec.beans.PatientBean" scope="session"/>
<%
  AddressBean theAddressBean = patientBean.getAddress();
%>

<html:html>
<head>
  <html:base/>
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

<!-- Content -->
<sslext:link page="/medicalrecord.do"><span class="pagetitle-patient"><bean:message key="link.home"/></span></sslext:link> &gt;
<span class="pagetitle-patient"><bean:message key="page.title.patient.profile.edit"/></span>
<br/><br/>

<sslext:form method="<%=MedRecWebAppUtils.getHttpMethod()%>" action="/editprofile.do" focus="firstName">
  <table border="0" cellspacing="0" cellpadding="3">
    <tr>
      <td class="label"><bean:message key="FirstName"/></td>
      <td><html:text
            name="patientBean"
            property="firstName"
            size="20"
            maxlength="60"
            value="<%=patientBean.getFirstName()%>"
            tabindex="1"
          />
      </td>
      <td class="label"><bean:message key="Address"/>:</td>
      <td></td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <html:messages id="error" property="firstName">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
    </tr>
    <tr>
      <td class="label"><bean:message key="LastName"/></td>
      <td><html:text
            name="patientBean"
            property="lastName"
            size="20"
            maxlength="60"
            value="<%=patientBean.getLastName()%>"
            tabindex="2"
          />
      </td>
      <td class="label"><bean:message key="Street"/></td>
      <td><html:text
            name="patientBean"
            property="address.streetName1"
            size="20"
            maxlength="60"
            value="<%=patientBean.getAddress().getStreetName1()%>"
            tabindex="9"
          />
      </td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <html:messages id="error" property="lastName">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
      <td></td>
      <td align="left">
        <html:messages id="error" property="address.streetName">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
    </tr>
    <tr>
      <td class="label"><bean:message key="MiddleName"/></td>
      <td><html:text
            name="patientBean"
            property="middleName"
            size="20"
            maxlength="60"
            value="<%=patientBean.getMiddleName()%>"
            tabindex="3"
          />
      </td>
      <td class="label"></td>
      <td><html:text
            name="patientBean"
            property="address.streetName2"
            size="20"
            maxlength="60"
            value="<%=theAddressBean.getStreetName2()%>"
            tabindex="10"
          />
      </td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <html:messages id="error" property="middleName">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
    </tr>
    <tr>
      <td class="label"><bean:message key="Gender"/></td>
      <td><html:select property="gender"
            value="<%=patientBean.getGender()%>"
            tabindex="4">
            <html:option value="1"><bean:message key="ChooseGender"/></html:option>
            <html:option value="2">-------------</html:option>
            <html:option value="Male"><bean:message key="Male"/></html:option>
            <html:option value="Female"><bean:message key="Female"/></html:option>
          </html:select>
      </td>
      <td class="label"><bean:message key="City"/></td>
      <td><html:text
            name="patientBean"
            property="address.city"
            size="20"
            maxlength="60"
            value="<%=theAddressBean.getCity()%>"
            tabindex="11"
          />
      </td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <html:messages id="error" property="gender">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
      <td></td>
      <td align="left">
        <html:messages id="error" property="address.city">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
    </tr>
    <tr>
      <td class="label"><bean:message key="DOB"/><br/><font size=1pt>(mm/dd/yyyy)</font></td>
      <td><html:text
            name="patientBean"
            property="dob"
            size="10"
            maxlength="10"
            value="<%=patientBean.getDob()%>"
            tabindex="5"
          />
      </td>
      <td class="label"><bean:message key="State"/></td>
      <td><html:text
            name="patientBean"
            property="address.state"
            size="20"
            maxlength="40"
            value="<%=theAddressBean.getState()%>"
            tabindex="12"
          />
      </td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <html:messages id="error" property="dob">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
      <td></td>
      <td align="left">
        <html:messages id="error" property="address.state">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
    </tr>
    <tr>
      <td class="label"><bean:message key="SSN"/><br/><font size=1pt>(xxxxxxxxx)</font></td>
      <td><html:text
            name="patientBean"
            property="ssn"
            size="9"
            maxlength="9"
            value="<%=patientBean.getSsn()%>"
            tabindex="6"
          />
      </td>
      <td class="label"><bean:message key="Zip"/></td>
      <td><html:text
            name="patientBean"
            property="address.zipCode"
            size="20"
            maxlength="10"
            value="<%=theAddressBean.getZipCode()%>"
            tabindex="13"
          />
      </td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <html:messages id="error" property="ssn">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
      <td></td>
      <td align="left">
        <html:messages id="error" property="address.zipCode">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
    </tr>
    <tr>
      <td class="label"><bean:message key="Phone"/><br/><font size=1pt>(xxx-xxx-xxxx)</font></td>
      <td><html:text
            name="patientBean"
            property="phone"
            size="12"
            maxlength="12"
            value="<%=patientBean.getPhone()%>"
            tabindex="7"
          />
      </td>
      <td class="label"><bean:message key="Country"/></td>
      <td><html:text
            name="patientBean"
            property="address.country"
            size="20"
            maxlength="40"
            value="<%=theAddressBean.getCountry()%>"
            tabindex="14"
          />
      </td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <html:messages id="error" property="phone">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
      <td></td>
      <td align="left">
        <html:messages id="error" property="address.country">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
    </tr>
    <tr>
      <td class="label"><bean:message key="Email"/></td>
      <td><html:text
              name="patientBean"
              property="email"
              size="20"
              maxlength="40"
              value="<%=patientBean.getEmail()%>"
              readonly="true"
              styleClass="row1"
              tabindex="8"

           />
        </td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <html:messages id="error" property="email">
          <bean:write name="error" filter="false"/>
        </html:messages>
      </td>
    </tr>
    <tr>
      <td></td>
      <td>
        <br/>
        <html:submit property="action" styleClass="profileButton">
          <bean:message key="button.Save"/>
        </html:submit>
        <html:submit property="action">
          <bean:message key="button.Cancel"/>
        </html:submit>
      </td>
    </tr>
  </table>
</sslext:form>

<!-- Content END -->
<!-- End Padding for Content -->
  </tr>
</table>
</body>
</html:html>

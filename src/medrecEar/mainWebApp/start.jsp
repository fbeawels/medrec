<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="java.util.Locale,
                 org.apache.struts.Globals,
                 com.bea.medrec.utils.MedRecWebAppUtils" %>

<%
  Locale locale = null;
  Cookie cookie = null;
  String country = null;
  String language = null;

  Cookie[] cookies = request.getCookies();
  Cookie languageCookie = null;
  Cookie countryCookie = null;

  if (cookies != null) {
    for (int i=0; i < cookies.length; i++) {
      cookie = cookies[i];
      if (cookie.getName().equals("Language")) language = cookie.getValue();
      else if (cookie.getName().equals("Country")) country = cookie.getValue();
    }
  }

  if (country != null && language != null) {
    locale = new Locale(language, country);
  } else {
    locale = request.getLocale();
    languageCookie = new Cookie("Language", locale.getLanguage());
    countryCookie = new Cookie("Country", locale.getCountry());
    languageCookie.setMaxAge(Integer.MAX_VALUE);
    countryCookie.setMaxAge(Integer.MAX_VALUE);
    response.addCookie(languageCookie);
    response.addCookie(countryCookie);
  }

  if (!(locale.getLanguage().equals("ja") ||
        locale.getLanguage().equals("ko") ||
        locale.getLanguage().equals("zh") ||  
        locale.getLanguage().equals("en") ||
        locale.getLanguage().equals("es"))) {
    locale = new Locale("en", "US");
    languageCookie = new Cookie("Language", "en");
    countryCookie = new Cookie("Country", "US");
    languageCookie.setMaxAge(Integer.MAX_VALUE);
    countryCookie.setMaxAge(Integer.MAX_VALUE);
    response.addCookie(languageCookie);
    response.addCookie(countryCookie);
  }

  Locale secondLocale = request.getLocale();
  String secondLocaleLabel = null;
  if(secondLocale.getLanguage().equals("ja")){
    secondLocaleLabel = "Japanese";
	}else if(secondLocale.getLanguage().equals("ko")){
    secondLocaleLabel = "Korean";
	}else if(secondLocale.getLanguage().equals("zh")){
    if(secondLocale.getCountry().equals("CN")){
      secondLocaleLabel = "SimplifiedChinese";
    }else if(secondLocale.getCountry().equals("TW") ||
            secondLocale.getCountry().equals("HK")){
      secondLocaleLabel = "TraditionalChinese";
    }else{
      secondLocaleLabel = "Japanese";
      secondLocale = new Locale("ja", "JA");
    }
	}else{
    secondLocaleLabel = "Japanese";
    secondLocale = new Locale("ja", "JA");
  }
  String secondLanguage = secondLocale.getLanguage();
  String secondCountry = secondLocale.getCountry();
  
  String localelink = "/changelocale.do?Language=" + secondLanguage + "&Country=" + secondCountry;
  request.getSession().setAttribute(Globals.LOCALE_KEY, locale);
%>

<html:html>
<head>
  <html:base/>
  <title><bean:message key="title.MedRec"/>:  <bean:message key="subtitle.MedRec"/></title>
  <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>
<body bgcolor="white" topmargin="0" leftmargin="0">


<!-- Title Bar Start -->
<table class="pagetop" width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr><td colspan="2"bgcolor="#02669A"><img src="images/banner_login.gif"></td></tr>
  <tr><td colspan="2"bgcolor="#FFFFFF"><img src="images/clear.gif" width="1" height="1"></td></tr>
  <tr><td class="pagetop" valign="middle">&nbsp;&nbsp;<bean:message key="subtitle.MedRec"/></td>
  <td valign="middle" align=left bgcolor="#C8C8C8">
    <table width="100%" cellpadding="0" cellspacing=6 border="0" align="center">
      <tr><td><sslext:link page="/changelocale.do?Language=en&Country=US" styleClass="languagebutton"><bean:message key="English"/></sslext:link></td>
        <td><img src="images/clear.gif" width="10"></td>
        <td><sslext:link page="<%=localelink%>" styleClass="languagebutton"><bean:message key="<%=secondLocaleLabel%>"/></sslext:link></td>
      </tr>
    </table>
  </td>
</tr>
<tr><td colspan="2"bgcolor="#8B8B8B"><img src="images/clear.gif" width="1" height="2"></td></tr>
<tr><td colspan="2"bgcolor="#FFFFFF"><img src="images/clear.gif" width="1" height="2"></td></tr>
</table>
<!-- Title Bar End -->


<br/>
<table width="90%" border="0" cellspacing="0" cellpadding="0" align="center" class="loginborder">
  <tr><td bgcolor="#E9F4F0" align=left><img src="images/login_patient.gif"></td>
  <td align=left class="pagetitlePatient"><bean:message key="Patients"/><img src="images/clear.gif" height="1" width="400"></td></tr>
  <tr><td colspan="2"bgcolor="#C8C8C8"><img src="images/clear.gif" width="1" height="2"></td></tr>
  <tr><td colspan="2"class="logincontent">
  <br/>
  <sslext:link page="/patient/register.do"><b><bean:message key="Register"/></b></sslext:link> - <bean:message key="descr.patient.register"/>
  <br/>
  <sslext:link page="/patient/login.do"><b><bean:message key="Login"/></b></sslext:link> <bean:message key="descr.patient.login"/>
  <br/><br/>
  </td></tr>
</table>

<br/>

<table width="90%" border="0" cellspacing="0" cellpadding="0" align="center" class="loginborder">
  <tr><td bgcolor="#D1FFFE" align=left><img src="images/login_admin.gif"></td>
  <td align=left valign="middle" class="pagetitleAdmin">
   <bean:message key="Administrators"/><img src="images/clear.gif" height="1" width="400"></td>
  </tr>
  <tr><td colspan="2"bgcolor="#C8C8C8"><img src="images/clear.gif" width="1" height="2"></td></tr>
  <tr><td colspan="2"class="logincontent">
  <br/>
  <sslext:link page="/admin/login.do"><b><bean:message key="Login"/></b></sslext:link>
  <bean:message key="descr.admin.login"/>
  <br/><br/><br/>
  </td></tr>
</table>

<br/>
<table width="90%" border="0" cellspacing="0" cellpadding="0" align="center" class="loginborder">
  <tr><td bgcolor="#FFFFCF" align=left><img src="images/login_physician.gif"></td>
  <td align=left valign="middle" class="pagetitleMd">
   <bean:message key="Physicians"/><img src="images/clear.gif" height="1" width="400"></td></tr>
  <tr><td colspan="2"bgcolor="#C8C8C8"><img src="images/clear.gif" width="1" height="2"></td></tr>
  <tr><td colspan="2"class="logincontent">
  <br/>
  <sslext:link page="/physician/login.do"><b> <bean:message key="Login"/></b></sslext:link> <bean:message key="descr.physician.login"/>
  <br/><br/><br/>
  </td></tr>
</table>

</body>
</html:html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<%@ page import="java.util.Locale,
                 java.net.URL,
                 java.net.HttpURLConnection"%>

<%--
 % $Id: index.jsp,v 1.10 2001/10/27 04:25:44 ro89390 Exp $
 % Copyright 2006 BEA Systems, Inc. All rights reserved.
--%>


<%
  Locale currentLocale = request.getLocale();
  String currentLanguage = currentLocale.getLanguage();
  String currentCountry = currentLocale.getCountry();

  if (currentLanguage != Locale.ENGLISH.getLanguage()) {
    try {
      String redirectPath = "";
      if (currentLanguage == Locale.CHINESE.getLanguage()) {
        redirectPath = "index" + "_" + currentLanguage + "_" + currentCountry + ".jsp";
      } else if (currentLanguage == Locale.JAPANESE.getLanguage() ||
              currentLanguage == Locale.KOREAN.getLanguage()) {
        redirectPath ="index" + "_" + currentLanguage + ".jsp";
      } else {
        // Do nothing.
      }

      if (redirectPath != "") {
        URL aURL = new URL("http", request.getLocalName(), request.getLocalPort(), redirectPath);
        HttpURLConnection conn = (HttpURLConnection)aURL.openConnection();
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
          response.sendRedirect(redirectPath);
        }
      }
    } catch (Exception ex) {
        // Do nothing.
    }
  }
%>


<html>
<head>
<title>Avitek Medical Records Application</title>
<link href="ootb.css" rel="stylesheet" title="Style" type="text/css" />

<style type="text/css">
<!--
.style1 {font-family: "Courier New", Courier, mono}
.style2 {font-style: italic}
.style4 {font-family: "Courier New", Courier, mono; font-style: italic; }
-->
</style>
</head>
<body>

<!-- header table begin -->

<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="470" background="images/splash_filler_short.gif">

	  <img src="images/splash_server_90_short.gif" width="410" height="153" border="0">

</td>

<td width=100% align="left" valign="top" background="images/splash_filler_short.gif" >
<nobr>
<p style="text-align:left;color:white; margin-top:95px;font-size:120%">&nbsp;|&nbsp;&nbsp;Avitek&#153; Medical Records Sample Application</p> 
</nobr></td>
<td align="left" valign="bottom">
<img src="images/spacer.gif" width="1" height="1"> 	
</td>
</tr>
</table>
<!-- header table end --> 
<!-- Nav Buttons --> 
<table width=100%>
<tr>
<td width=50%>

<nobr>
<p style="margin-top:2px">
	<a href="server/docs/index.html" target="viewer">More Samples</a>&nbsp;|&nbsp;
	<a href="http://e-docs.bea.com/wls/docs100/index.html" target="_blank">Documentation</a>&nbsp;|&nbsp;
	<a href="http://dev2dev.bea.com" target="_blank">dev2dev</a>
</p>

</td>
<td width=50% align="right">
<nobr>
<p style="margin-top:2px;margin-right:10px;">
<a href="start.jsp" target="_blank" class="button">&nbsp;Start using MedRec!&nbsp;</a>&nbsp;&nbsp;
<a href=../console target="_blank" class="button" >&nbsp;Start the Administration Console&nbsp;</a>&nbsp;
</p>
</nobr>
</td>
</tr>
</table>

<h3>Avitek&#153; Medical Records Sample Application</h3>
<table width="100%" cellpadding="10">
<tr>
<td valign="top" width="50%"><p>Avitek Medical Records (or MedRec) is a WebLogic Server sample application suite that  demonstrates all aspects of the Java Platform, Enterprise Edition (Java EE). MedRec is designed as an educational tool for all levels of Java EE developers.  It showcases the use of each Java EE component, and illustrates best-practice design patterns for component interaction and client development.  MedRec also illustrates best practices for developing and deploying applications with WebLogic Server.
</p>
  <p>
The MedRec suite consists of four separate Java EE applications that correspond to each user type:
</p>

  <ul>
<li>
<strong>Patient</strong>&nbsp;-&nbsp;The Patient application allows Patients to log in, edit their profile information, or request that their profile be added to the system. Patients can also view prior medical records of visits with their physician.
</li>

<li>
<strong>Controller</strong>&nbsp;-&nbsp;The Controller application provides all of the controller and business logic used by the MedRec application suite.
</li>

<li>
<strong>Admin</strong>&nbsp;-&nbsp;The Admin application allows an administrator to approve or deny new patient profile requests.
</li>

<li>
<strong>Physician</strong>&nbsp;-&nbsp;The Physician application allows physicians and nurses to log in, search and access patient profiles, create and review patient medical records, and prescribe medicine to patients.
</li>
</ul>
</td>
<td width="50%" valign="top">
<p align="left"><img
src="images/medrec_screen_shot.jpg" alt="Medical Records Patient Info Screen"
width=587 height=519 ><br>
<p>&nbsp;<strong>Avitek Medical Records Patient Info Screen</strong></p>
</p>
</td>

</tr></table>
<hr noshade >
<h3>Logging in to MedRec </h3>
<table cellpadding="10">
<tr>
<td width="50%" valign="top" >

<p>To begin using MedRec, click the <strong>Start Using MedRec</strong> button below.  From there, you can begin by registering as a new patient, or you can log in using one of the previously-defined roles listed to the right.
</p>
<p>The administration username and password is <strong>weblogic/weblogic</strong>.  Use this username when logging into the Administration Console.</p>
<p><a href="start.jsp" target="_blank" class="button">&nbsp;Start using MedRec!&nbsp;</a></p>

</td>

<td width="50%" valign="top">

<table border="1" cellspacing="0" cellpadding="0" >
 <tr>
  <td width="103" valign="top" bgcolor="#EEEEEE"><b>&nbsp;Role</b></td>
  <td width="168" valign="top" bgcolor="#EEEEEE"><b>&nbsp;Username</b></td>
  <td width="96" valign="top" bgcolor="#EEEEEE"><b>&nbsp;Password</b></td>
 </tr>
 <tr>
  <td width="103" valign="top">&nbsp;Admin</td>
  <td width="168" valign="top">&nbsp;admin@avitek.com</td>
  <td width="96" valign="top">&nbsp;weblogic</td>
 </tr>
 <tr>
  <td width="103" valign="top">&nbsp;Patient</td>
  <td width="168" valign="top">&nbsp;charlie@star.com</td>
  <td width="96" valign="top">&nbsp;weblogic</td>
 </tr>
 <tr>
  <td width="103" valign="top">&nbsp;&nbsp;</td>
  <td width="168" valign="top">&nbsp;fred@golf.com</td>
  <td width="96" valign="top">&nbsp;weblogic</td>
 </tr>
 <tr>
  <td width="103" valign="top">&nbsp;</td>
  <td width="168" valign="top">&nbsp;larry@bball.com</td>
  <td width="96" valign="top">&nbsp;weblogic</td>
 </tr>
 <tr>
  <td width="103" valign="top">&nbsp;</td>
  <td width="168" valign="top">&nbsp;page@fish.com</td>
  <td width="96" valign="top">&nbsp;weblogic</td>
 </tr>
 <tr>
  <td width="103" valign="top">&nbsp;</td>
  <td width="168" valign="top">&nbsp;volley@ball.com</td>
  <td width="96" valign="top">&nbsp;weblogic</td>
 </tr>
  <tr>
  <td width="103" valign="top">&nbsp;Physician</td>
  <td width="168" valign="top">&nbsp;mary@md.com</td>
  <td width="96" valign="top">&nbsp;weblogic</td>
 </tr>
</table>

</td></tr></table>

<hr noshade >

<h3>MedRec Documentation and Source Code</h3>
<p style="margin-left:10px">Use these links to learn more about technologies used in the MedRec application and to view the application's source code: </p>
<table width="100%"  border="0" cellpadding="10">
  <tr>
    <td width="50%" valign="top"><ul>
      <li><a href="server/medrec/doc/medrec_overview_doc.html" target="viewer">Overview</a></li>
      <li><a href="server/medrec/doc/medrec_starter_doc.html" target="viewer">Getting Started Using MedRec </a></li>
      <li><a href="server/medrec/doc/dev_Deploy_Best_Practices.html" target="viewer">Developer Best Practices</a></li>
      <li><a href="server/medrec/doc/diag_log4j.html" target="viewer">Diagnostics</a></li>
      <li><a href="server/medrec/doc/ejb_erc.html" target="viewer">Enterprise JavaBeans</a></li>
    </ul>
	
	
	</td>
    <td width="50%" valign="top"><ul>
      <li><a href="server/medrec/doc/jms_Connection_Fact.html" target="viewer">Messaging (JMS) </a></li>
      <li><a href="server/medrec/doc/jdbc_rowsets.html" target="viewer">Database Connectivity (JDBC) </a></li>
      <li><a href="server/medrec/doc/ws_Access_EJB_Swing_dotNET.html" target="viewer">Web Services </a></li>
      <li><a href="server/medrec/doc/Struts_3rd_Party_Util.html" target="viewer">Struts</a> </li>
      <li><a href="server/medrec/doc/xmlbeans.html" target="viewer">XMLBeans</a></li>
    </ul></td>
  </tr>
</table>


<hr noshade >
<h3>Additional Resources</h3>
<p style="margin-left:10px">Use these resources to learn more about the Avitek Medical Records sample application:</p>
<ul>
  <li><a href="http://e-docs.bea.com/wls/docs100/medrec_arch/index.html" target="_blank">MedRec Architecture Guide</a><br>
    A discussion of the design patterns used in the MedRec application. </li>
  <li><a href="http://e-docs.bea.com/wls/docs100/samples.html" target="_blank">MedRec Tutorials</a><br>
    As they become available, tutorials that demonstrate development practices and technologies using MedRec will be listed on this page. </li>
  <li><a href="http://dev2dev.bea.com/pub/a/2005/09/spring_integration_weblogic_server.html" target="_blank">Spring Integration With WebLogic Server</a><br>
A redesigned MedRec application that replaces core Java EE components with Spring components.</li>
  <li><a href="http://forums.bea.com/bea/forum.jspa?forumID=2061" target="_blank">Newsgroups</a><br>
    You can post and read questions, comments, or suggestions about MedRec in these newsgroups.</li>
  <li><a href="http://e-docs.bea.com/wls/docs100/index.html" target="_blank">WebLogic Server Documentation</a><br>
    Complete documentation for developers and system administrators. </li>
</ul>
<hr >
<h3>Using Web Services Stand-alone Clients
</h3>
<p style="margin-left:10px">Several clients are provided to demonstrate Web Services interoperability features.  The Physician application, a Web Application client, communicates with Medical Records completely via Web Services.  In addition, two stand-alone clients are provided: a Java Swing client and .NET C# client.
<table cellpadding="10">
<tr><td width="50%" valign="top">
<h4>Java Swing Client </h4>
<p>To run the Java Swing client:</p>
<ol>
  <li>Open a CMD shell.</li>
  <li>Set up your MedRec environment with the <font face="Courier New" >setDomainEnv.cmd</font> script, located in <font face="Courier New" ><em>BEA_HOME</em>/weblogic100/samples/domains/medrec/bin</font>.</li>
  <li>Change to the <font face="Courier New" ><em>BEA_HOME</em>/weblogic100/samples/server/medrec/src/clients</font> directory.</li>
  <li>Run the client by typing <font face="Courier New" ><b><br>
    ant run</b></font>.</li>
</ol>
</td><td width="50%" valign="top">

<h4>.NET C# Client </h4>
<p>To run the .NET C# client:</p>
<ol>
  <li>Make sure you have the Microsoft .NET Framework Redistributable.  If not, download it from <a href="http://msdn.microsoft.com/netframework/downloads/howtoget.asp" target="_blank">Microsoft</a>.</li>
  <li>In Windows Explorer, browse to the <br>
    <span class="style2"><font face="Courier New" >BEA_HOME/weblogic100/</font></span><font face="Courier New" >samples/server/medrec/src/clients/<br>
    CSharpClient/bin/Release</font> directory.</li>
  <li>Run the client by double-clicking on <font face="Courier New" ><b>CSharpClient.exe</b></font>.</li>
</ol>

<p>To use either of the stand-alone clients, enter a valid Social Security Number (SSN) in the Patient ID search field and click Submit to retrieve the patient's profile.</p>

</td></tr></table>
<p style="margin-left:10px">(Where <span class="style4">BEA_HOME</span>  is the directory containing your WebLogic Server installation.) </p>
<hr >
<h3>Database Connectivity </h3>
<p style="margin-left:10px">Data for the MedRec applications is stored in a PointBase database installed with WebLogic Server.  You can view the PointBase data directly by running the <font face="Courier New" >startPointBaseConsole.cmd</font> or <font face="Courier New" >startPointBaseConsole.sh</font> scripts located in the <font face="Courier New" class="style1">WL_HOME/common/eval/pointbase/tools</font> subdirectory of the WebLogic Server installation directory.  The login for the demo database is medrec/medrec. DDL files and other scripts for using PointBase are located in the <span class="style1">WL_HOME/samples/server/medrec/setup/db</span> directory. </p>

<!--<p>
For more information about MedRec, see the WebLogic Server <b><a
href="http://edocs.bea.com/wls/docs81/medrec_tutorials/index.html">Tutorials</a></b> page.
</p>

<p>
For more information about the MedRec application itself, see the <b>
<b><a href="javadoc/index.html" target="_blank">MedRec Javadocs</a></b>.
</p>-->




<hr noshade="noshade"  >
<p class="smaller"><a href="server/examples/src/examples/copyright.html" target="_blank">Copyright</a> &copy; 2006 BEA Systems,
Inc. All Rights Reserved.</p

>
<p class="smaller">&nbsp;</p

>
</body>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ page import="com.bea.medrec.actions.AdminConstants"%>

<!-- Title Bar Start -->
<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td bgcolor="#669900" align="left"><img src="images/admin_top_banner3.gif"></td>
		<td bgcolor="#669900"><div class="pageheaderadmin"><bean:message key="Administrator"/></div></td>
	</tr>
	<tr>
		<td bgcolor="#D1FFFE"><img src="images/admin_banner_photo.jpg"></td>
		<td bgcolor="#D1FFFE">&nbsp;</td>
	</tr>
	<tr>
		<td bgcolor="#FFFFFF" height="1" colspan=2><img src="images/clear.gif" width="100" height="1" alt="" border="0"></td>
	</tr>
</table>
<table class="adminname" width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td>
      <bean:write name="<%=AdminConstants.ADMIN_BEAN%>" property="username" scope="session"/>
      <img src="images/clear.gif" height="1" width="450"/>
    </td>
		<td>
      <img src="images/clear.gif"/>
      <table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td><img src="images/clear.gif" width="10" height="1"></td>
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
<!-- Title Bar End -->

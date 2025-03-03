<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import="com.bea.medrec.actions.PhysicianConstants" %>

<!-- START Title Bar  -->
<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <tr>
    <td bgcolor="#CC3300" align=left><img src="images/physician_top_banner3.gif"></td>
    <td bgcolor="#CC3300"><div class="pageheaderphysician">          Dr.&nbsp;
          <bean:write name="<%=PhysicianConstants.PHYSICIAN_BEAN%>" property="firstName" scope="session"/>&nbsp;
          <bean:write name="<%=PhysicianConstants.PHYSICIAN_BEAN%>" property="lastName" scope="session"/></div></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFCC"><img src="images/physician_banner_photo.jpg" width="354" height="65"></td>
    <td bgcolor="#FFFFCC"><br/></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF" height=1 colspan=2><img src="images/clear.gif" width="100" height="1" alt="" border="0"></td>
  </tr>
</table>
<!-- Title Bar END -->

package com.bea.medrec.actions;

import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.utils.MedRecWebAppUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import weblogic.servlet.security.ServletAuthentication;

/**
 * <p>Login controller.  Handles all request during the login
 * process.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class PhysLogoutAction extends PhysBaseAction {

  private static Logger logger =
      MedRecLog4jFactory.getLogger(PhysLogoutAction.class.getName());

 /**
  * <p>Process the specified HTTP request, and create the corresponding HTTP
  * response (or forward to another web component that will create it).
  * Return an <code>ActionForward</code> instance describing where and how
  * control should be forwarded.
  * <br>
  * Invalidates session and redirects to MedRec homepage.
  * </p>
  *
  * @param mapping  The ActionMapping used to select this instance
  * @param form  The optional ActionForm bean for this request (if any)
  * @param request  The HTTP request we are processing
  * @param response  The HTTP response we are creating
  */
  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
    throws Exception
  {
    // Minimal check for valid session.
    //super.checkSession(request, mapping);

    logger.info("Logging out user.");
    logger.debug("Invalidating session.");

    ServletAuthentication.invalidateAll(request);

    // Return to MedRec start page.
    return mapping.findForward("medrec.startpage");
  }
}

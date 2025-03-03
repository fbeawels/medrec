package com.bea.medrec.actions;

import com.bea.medrec.beans.AdminBean;
import com.bea.medrec.beans.UserBean;
import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.utils.MedRecWebAppUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import weblogic.servlet.security.ServletAuthentication;

import java.util.Locale;

/**
 * <p>Admin Login controller.  Handles all request during the Admin login
 * process.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class AdminLoginAction extends BaseAction implements AdminConstants {

  private static Logger logger =
      MedRecLog4jFactory.getLogger(AdminLoginAction.class.getName());

 /**
  * <p>Process the specified HTTP request, and create the corresponding HTTP
  * response (or forward to another web component that will create it).
  * Return an <code>ActionForward</code> instance describing where and how
  * control should be forwarded.
  * <br>
  * Handles incoming login requests.
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
    // Set user's locale.
    setupLocale(request);

    // Declare and initial local variables.
    UserBean user = (UserBean)form;
    String action = request.getParameter(ACTION);
    String loginSubmit = null;

    // Cancel login.  Redirect to start page.
    if (isCancelled(request)) {
      logger.info("Cancel login.");
      form.reset(mapping, request);
      ServletAuthentication.invalidateAll(request);
      // Return to MedRec start page.
      return new ActionForward("medrec.startpage", true);
    }

    // Login processing.
    loginSubmit = getMessage(request, "button.Login");
    logger.debug("Action: "+action);
    logger.debug("Button Message: "+loginSubmit);
    if (MedRecWebAppUtils.isNotEmpty(action) && action.equals(loginSubmit)) {
      try {
        return authenticate(user, mapping, request, response);
      }
      catch(Exception e) {
        throwClientException(e,mapping,"login.home.redirect");
      }
    }

    // First time thru.
    return mapping.findForward("login.home");
  }

 /**
  * <p>This method authenticates a given user containg a username and password.
  * Since MedRec contains an Admin and Patient appplication and
  * each application has its own specific authentication provider. The Admin
  * application uses WebLogic's DefaultAuthenticator which stores usernames
  * and passwords within an LDAP database.</p>
  */
  private ActionForward authenticate(UserBean user,
                                     ActionMapping mapping,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
    throws Exception {

    // Declare local variables.
    ActionForward forward = null;

    Locale currentLocale = this.getLocale(request);
    ServletAuthentication.invalidateAll(request);
    setLocale(request, currentLocale);

    // Returns an int value for AUTHENTICATED or FAILED_AUTHENTICATION
    // after using the username and password to authenticate the user
    // and setting that user information into the session.
    int auth = ServletAuthentication.weak(user.getUsername(),
        user.getPassword(), request, response);

    // Check auth return value.
    if (auth == ServletAuthentication.AUTHENTICATED
        && request.isUserInRole(MEDREC_ADMIN_ROLE)) {
      logger.info("Authentication success!");

      // Create new session.
      HttpSession session = request.getSession(true);

      // Set user on session to be used throughout the app.
      AdminBean admin = new AdminBean(user.getUsername());
      session.setAttribute(ADMIN_BEAN, admin);

      forward = getRedirectPage(request, mapping);
    }
    else {
      logger.info("Authentication failed!");

      // Reset login values.
      user.reset();

      // Create action error - invalid username and/or password.
      ActionErrors errors = new ActionErrors();
      errors.add("invalidLogin", new ActionError("invalid.username.password"));
      saveErrors(request, errors);

      // Set redirect to login page.
      forward = mapping.findForward("login.failure");
    }

    // Log where we are going next.
    logger.info("Redirecting to: "+forward.getPath());
    return forward;
  }
}

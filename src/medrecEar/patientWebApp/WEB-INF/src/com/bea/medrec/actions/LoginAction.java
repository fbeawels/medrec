package com.bea.medrec.actions;

import com.bea.medrec.value.*;
import com.bea.medrec.entities.*;
import com.bea.medrec.beans.PatientBean;
import com.bea.medrec.beans.UserBean;
import com.bea.medrec.utils.ClientException;
import com.bea.medrec.utils.ErrorConstants;
import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.utils.MedRecWebAppUtils;
import com.bea.medrec.value.Patient;
import javax.ejb.FinderException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import weblogic.servlet.security.ServletAuthentication;

import java.util.Locale;

/**
 * <p>Login controller.  Handles all request during the login
 * process.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class LoginAction extends PatientBaseAction {

  private static Logger logger = MedRecLog4jFactory.getLogger(LoginAction.class.getName());

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
      throws Exception {
    // Set user's locale.
    setupLocale(request);

    // Declare and initial local variables.
    UserBean user = (UserBean) form;
    String action = request.getParameter(ACTION);
    String loginSubmit = null;

    // Cancel login.  Redirect to start page.
    if (isCancelled(request)) {
      logger.info("Cancel login.");
      form.reset(mapping, request);
      // Return to MedRec start page.
      return new ActionForward("medrec.startpage", true);
    }

    // Login processing.
    loginSubmit = getMessage(request, "button.Login");
    logger.debug("Action: " + action);
    logger.debug("Button Message: " + loginSubmit);
    if (isNotEmpty(action) && action.equals(loginSubmit)) {
      try {
        // Process login.
        return authenticate(user, mapping, request, response);
      } catch (Exception e) {
        throwClientException(e, mapping, "login.home.redirect");
      }
    }

    // First time thru.
    return mapping.findForward("login.home");
  }

  /**
   * <p>This method authenticates a given user containg a username and password.
   * Since MedRec contains an Admin and Patient appplication and
   * each application has its own specific authentication provider,
   * authentication is a two step process.  The first step the server validates
   * the username and password by using a authentication provider.  The
   * second step checks that meta-data is found within MedRec's database.</p>
   */
  private ActionForward authenticate(UserBean user,
                                     ActionMapping mapping,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
      throws Exception {

    // Delcare local variables.
    ActionForward forward = null;

    Locale currentLocale = this.getLocale(request);
    ServletAuthentication.invalidateAll(request);
    setLocale(request, currentLocale);

    // Returns an int value for AUTHENTICATED or FAILED_AUTHENTICATION
    // after using the username and password to authenticate the user
    // and setting that user information into the session.
    int auth = ServletAuthentication.weak(user.getUsername(),
        user.getPassword(), request, response);

    // check auth return value
    if (auth == ServletAuthentication.AUTHENTICATED
        && request.isUserInRole(PATIENT_ROLE)) {
      logger.info("Login found.");
      logger.info("Looking up user data.");

      // Retrieve patient properties.
      Patient patient =
          getPatientSession().findPatientByEmail(user.getUsername());

      // Patient user found, but no meta-data found.
      // Disallow login.
      if (patient == null) {
        ServletAuthentication.invalidateAll(request);
        throw new ClientException(ErrorConstants.PATIENT_NOT_FOUND);
      }

      logger.info("Authentication success!");

      // Create new session.
      HttpSession session = request.getSession(true);

      // Set user on session to be used throughout the app.
      session.setAttribute(PATIENT_BEAN, new PatientBean(patient));

      // Determine redirection.
      forward = forward = getRedirectPage(request, mapping);
    } else {
      User userLocal = null;
      String userStatus = null;
      try {
        userLocal = getAdminSession().getUserByUsername(user.getUsername());
      }catch (FinderException ex){
        logger.debug("Can't find the user with the given username " + user.getUsername());
        userStatus = "NOT EXISTED";
      }
      if (userLocal != null)userStatus = userLocal.getStatus();
      logger.debug("Status is " + userStatus);
      if (userStatus.equals("NEW")){
        logger.debug("Not yet approved by admin!");
        // Create action error - User not yet approved.
        ActionErrors errors = new ActionErrors();
        errors.add("invalidLogin", new ActionError("invalid.user"));
        saveErrors(request, errors);
        // Return back to login page.
        forward = mapping.findForward("login.failure");
      } else if (userStatus.equals("DENIED")){
        logger.debug("Registration denied by admin!");
        // Create action error - User registration denied.
        ActionErrors errors = new ActionErrors();
        errors.add("invalidLogin", new ActionError("denied.user"));
        saveErrors(request, errors);
        // Return back to login page.
        forward = mapping.findForward("login.failure");
      } else {
        logger.debug("Authentication failed!");

        // Reset login values.
        user.reset();

        // Create action error - invalid username and/or password.
        ActionErrors errors = new ActionErrors();
        errors.add("invalidLogin", new ActionError("invalid.username.password"));
        saveErrors(request, errors);

        // Return back to login page.
        forward = mapping.findForward("login.failure");
      }
    }

    // Log where we are going next.
    logger.info("Redirecting to: " + forward.getPath());

    return forward;
  }
}

package com.bea.medrec.beans;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.bea.medrec.utils.MedRecLog4jFactory;
import org.apache.log4j.Logger;
import org.apache.struts.validator.Resources;

/**
 * Form bean for the user login page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>password</b> - Entered password value
 * <li><b>username</b> - Entered username value
 * </ul>
 *
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public final class LoginBean extends BaseBean {
  private static Logger logger =
          MedRecLog4jFactory.getLogger(LoginBean.class.getName());

  // Instance Variables
  private String j_username = "";
  private String j_password = "";

  // Constructors
  public LoginBean() {
  }

  // Getters
  public String getJ_username() {
    return (this.j_username);
  }

  public String getJ_password() {
    return (this.j_password);
  }

  // Setters
  public void setJ_username(String j_username) {
    this.j_username = j_username;
  }

  public void setJ_password(String j_password) {
    this.j_password = j_password;
  }

  // Public Methods
  public void reset() {
    this.j_password = "";
    this.j_username = "";
  }

  /**
   * <p>Validate login.</p>
   *
   * @param mapping
   * @param request
   *
   * @return ActionErrors
   */
  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {
    ActionErrors errors = new ActionErrors();
    // only validate if the user has clicked "Login"
    String loginSubmit = Resources.getMessage(request, "button.Login");
    if (loginSubmit.equals(request.getParameter("action"))) {
      errors = super.validate(mapping, request);
    }
    return errors;
  }

  public String toString() {
    StringBuffer str = new StringBuffer();
    str.append("LoginBean [");
    str.append("Username: " + j_username);
    str.append(" | Password: " + printPassword());
    str.append("]");

    return str.toString();
  }

  private String printPassword() {
    String pwd = "";
    for (int i = 0; i < j_password.length(); i++) pwd += "*";
    return pwd;
  }
}

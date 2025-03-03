package com.bea.medrec.beans;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.Resources;

/**
 * <p>Form bean containing create administrator user data.  Used in
 * creating Admin user.
 * </p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class CreateAdminBean extends AdminBean {
  // Instance Variables
  protected String password = "";
  protected String confirmpassword = "";

  // Constructors
  public CreateAdminBean() {
  }

  public CreateAdminBean(String pUsername,
                         String pPassword,
                         String pConfirmpassword) {
    this.username = pUsername;
    this.password = pPassword;
    this.confirmpassword = pConfirmpassword;
  }

  // Getters
  public String getPassword() {
    return this.password;
  }

  public String getConfirmpassword() {
    return this.confirmpassword;
  }

  // Setters
  public void setPassword(String pPassword) {
    this.password = pPassword;
  }

  public void setConfirmpassword(String pConfirmpassword) {
    this.confirmpassword = pConfirmpassword;
  }


  /**
   * <p>Validate Create New Admin.</p>
   *
   * @param mapping
   * @param request
   * @return ActionErrors
   */
  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {
    ActionErrors errors = new ActionErrors();
    // only validate if the user has clicked "Login"
    String loginSubmit = Resources.getMessage(request, "button.Create");
    if (loginSubmit.equals(request.getParameter("action"))) {
      errors = super.validate(mapping, request);
    }
    return errors;
  }

}

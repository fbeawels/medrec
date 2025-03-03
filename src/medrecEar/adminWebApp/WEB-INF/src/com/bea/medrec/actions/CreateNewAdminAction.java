package com.bea.medrec.actions;

import com.bea.medrec.beans.CreateAdminBean;
import com.bea.medrec.utils.ClientException;
import com.bea.medrec.utils.MedRecLog4jFactory;
import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.naming.NameNotFoundException;
import java.util.HashMap;
import java.util.Map;

import weblogic.management.utils.AlreadyExistsException;

/**
 * <p>Action class encapsulating creating new admin functionality.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class CreateNewAdminAction extends AdminBaseLookupDispatchAction {

  private static Logger logger =
          MedRecLog4jFactory.getLogger(CreateNewAdminAction.class.getName());

  public ActionForward execute(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
          throws ClientException, Exception {
    logger.debug("method mapping=" +
            request.getParameter(mapping.getParameter()));
    if (request.getParameter(mapping.getParameter()) == null) {
      return defaultMethod(mapping, form, request, response);
    } else
      return super.execute(mapping, form, request, response);
  }

  /**
   * <p>Determines method calling based on select submit button.</p>
   */
  protected Map getKeyMethodMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("button.Create", "createNewAdmin");
    map.put("button.Cancel", "cancel");
    return map;
  }

  /**
   * <p>Default action method.</p>
   *
   * @return ActionForward
   */
  public ActionForward defaultMethod(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
          throws Exception {
    logger.debug("defaultMethod");
    return loadCreateNewAdminPage(mapping, form, request, response);
  }

  public ActionForward loadCreateNewAdminPage(ActionMapping mapping,
                                           ActionForm form,
                                           HttpServletRequest request,
                                           HttpServletResponse response)
          throws ClientException, Exception {
    logger.debug("loadCreateNewAdminPage");
    // local variables
    CreateAdminBean createAdminBean = (CreateAdminBean) form;
    if (createAdminBean == null) createAdminBean = new CreateAdminBean();

    // Redirect to home view.
    return mapping.findForward("create.new.admin");
  }


  /**
   * <p>User selected cancel button.</p>
   *
   * @return ActionForward
   */
  public ActionForward cancel(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
          throws Exception {
    logger.info("Cancel create new admin.");
    ActionForward forward = null;
    try {
      forward = mapping.findForward("home");
    } catch (Exception e) {
      return handleException(e, request, mapping);
    }
    return forward;
  }

  public ActionForward createNewAdmin(ActionMapping mapping,
                                      ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response)
          throws ClientException, Exception {
    logger.info("Create New Admin");
    CreateAdminBean user = (CreateAdminBean) form;
    logger.debug(user.toString());

    MBeanServerConnection mBeanServerConnection = this.getDomainMBeanServerConnection(request);
    ObjectName service = new ObjectName("com.bea:Name=DomainRuntimeService,"+
        "Type=weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean");
    ObjectName domainMBean
            = (ObjectName) mBeanServerConnection.getAttribute(service, "DomainConfiguration");
    ObjectName securityConfiguration
            = (ObjectName) mBeanServerConnection.getAttribute(domainMBean, "SecurityConfiguration");
    ObjectName defaultRealm
            = (ObjectName) mBeanServerConnection.getAttribute(securityConfiguration, "DefaultRealm");
    ObjectName[] atnProviders
            = (ObjectName[]) mBeanServerConnection.getAttribute(defaultRealm, "AuthenticationProviders");

    ObjectName defaultAtnProvider = null;

    for (int i = 0; i < atnProviders.length; i++) {
        String name = (String) mBeanServerConnection.getAttribute(atnProviders[i], "Name");
        if (name.equals("DefaultAuthenticator")) {
          logger.debug("Found the Default authenticator");
          defaultAtnProvider = atnProviders[i];
      }
    }

    try {
      mBeanServerConnection.invoke(
              defaultAtnProvider,
              "createUser",
              new Object[] {user.getUsername(), user.getPassword(), "MedRec Admininistator"},
              new String[] {"java.lang.String", "java.lang.String", "java.lang.String"}
      );
    } catch (MBeanException ex) {
      Exception e = ex.getTargetException();
      if (e instanceof AlreadyExistsException) {
        logger.info("User, " + user.getUsername() + ", already exists.");
        ActionErrors errors = new ActionErrors();
        errors.add("invalidUserName",
                new ActionError("invalid.username.already.exists"));
        saveErrors(request, errors);
        return mapping.findForward("create.new.admin");
      } else {
        logger.debug(e);
        return mapping.findForward("create.new.admin");
      }
    }

    try {
      mBeanServerConnection.invoke(
              defaultAtnProvider,
              "addMemberToGroup",
              new Object[] {"Administrators", user.getUsername()},
              new String [] {"java.lang.String", "java.lang.String"}
      );

      mBeanServerConnection.invoke(
              defaultAtnProvider,
              "addMemberToGroup",
              new Object[] {"MedRecAdmins", user.getUsername()},
              new String [] {"java.lang.String", "java.lang.String"}
      );
    } catch (MBeanException ex) {
      Exception e = ex.getTargetException();
      if (e instanceof NameNotFoundException) {
        logger.info("Invalid Group Name.");
        ex.printStackTrace();
        return mapping.findForward("create.new.admin");
      } else {
        logger.debug(e);
        return mapping.findForward("create.new.admin");
      }
    }
    logger.info("MedRec Administrator successfully created.");
    return mapping.findForward("create.new.admin.successful");
  }
}

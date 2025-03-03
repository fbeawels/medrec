package com.bea.medrec.actions;

import com.bea.medrec.beans.ErrorBean;
import com.bea.medrec.utils.ClientException;
import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.utils.MedRecMessageProperties;
import com.bea.medrec.utils.MedRecWebAppUtils;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import weblogic.servlet.security.ServletAuthentication;

/**
 * <p>Base servlet encapsulating common servlet functionality.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public abstract class BaseAction extends Action {
  private static Logger logger =
      MedRecLog4jFactory.getLogger(BaseAction.class.getName());

  /**
   * <p>Process the specified HTTP request, and create the corresponding HTTP
   * response (or forward to another web component that will create it).
   * Return an <code>ActionForward</code> instance describing where and how
   * control should be forwarded.
   * <br>
   * All subclasses override this method with specific action functionality.
   * </p>
   *
   * @param mapping  The ActionMapping used to select this instance
   * @param form  The optional ActionForm bean for this request (if any)
   * @param request  The HTTP request we are processing
   * @param response  The HTTP response we are creating
   */
  public abstract ActionForward executeAction(ActionMapping mapping,
                                              ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
      throws ClientException, Exception;

  /**
   * <p>Process the specified HTTP request, and create the corresponding HTTP
   * response (or forward to another web component that will create it).
   * Return an <code>ActionForward</code> instance describing where and how
   * control should be forwarded.
   * <br>
   * Encapsulates common action functionality including error handling.
   * </p>
   *
   * @param mapping  The ActionMapping used to select this instance
   * @param form  The optional ActionForm bean for this request (if any)
   * @param request  The HTTP request we are processing
   * @param response  The HTTP response we are creating
   */
  public ActionForward execute(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
      throws Exception {
    ActionForward forward;
    try {
      forward = executeAction(mapping, form, request, response);
      if (logger.isDebugEnabled()) printSessionContents(request);
    } catch (Exception e) {
      return handleException(e, request, mapping);
    }
    return forward;
  }

  /**
   * <p>Removes an attribute from HttpSession.</p>
   *
   * @param req  The HTTP request we are processing
   * @param name  The name of the attribute to be removed.
   */
  protected void removeSessionAttribute(HttpServletRequest req, String name) {
    logger.debug("Removing "+name+" from session.");
    HttpSession session = req.getSession(false);
    if (session != null) session.removeAttribute(name);
  }

  /**
   * <p>Sets an object to the HttpSession</p>
   *
   * @param req  The HTTP request we are processing
   * @param name  The name key of object.
   * @param obj  The object to be set on HttpSession.
   */
  protected void setSessionAttribute(HttpServletRequest req,
                                     String name,
                                     Object obj) {
    logger.debug("Setting "+name+" of type "
       +obj.getClass().getName()+" on session.");
    HttpSession session = req.getSession(false);
    if (session != null) session.setAttribute(name, obj);
  }

  /**
   * <p>Retrieves an object from HttpSession.</p>
   *
   * @param req  The HTTP request we are processing
   * @param name  The name of the attribute to be retrieved.
   */
  protected Object getSessionAttribute(HttpServletRequest req, String name) {
    logger.debug("Getting "+name+" from session.");
    Object obj = null;
    HttpSession session = req.getSession(false);
    if (session != null) obj = session.getAttribute(name);
    return obj;
  }

  /**
   * <p>Prints values set on the current HttpSession.</p>
   *
   * @param req  The HTTP request we are processing
   */
  protected void printSessionContents(HttpServletRequest req) {
    HttpSession session = req.getSession(false);
    if (session != null) {
      Enumeration enum_ = session.getAttributeNames();
      StringBuffer strBuf = new StringBuffer();
      strBuf.append("Session contents:");
      int i = 0;
      while (enum_.hasMoreElements()) {
        String name = (String)enum_.nextElement();
        Object obj = session.getAttribute(name);
        strBuf.append("\n  "+(++i)+") name="+name+"; type="+
            obj.getClass().getName());
      }
      logger.debug(strBuf.toString());
    }
  }

  /**
   * <p>Sets user's locale.</p>
   *
   * @param request  The HTTP request we are processing
   */
  protected void setupLocale(HttpServletRequest request) {
    logger.debug("Setup locale.");
    Locale locale = MedRecWebAppUtils.getLocaleFromCookie(request);
    if (locale == null) locale = getLocale(request);
    if (!MedRecWebAppUtils.isValidLocale(locale))
      locale = new Locale("en", "US");
    logger.debug("Locale: "+locale);
    setLocale(request, locale);
  }

  /**
   * <p>Get localize message.</p>
   *
   * @param key  The HTTP request we are processing
   */
  protected String getMessage(HttpServletRequest request, String key) {
    Locale locale = getLocale(request);
    return getResources(request).getMessage(locale, key);
  }

  /**
   * <p>Get instance of message properties.</p>
   *
   * @param request  The HTTP request we are processing
   */
  protected MedRecMessageProperties getMessageProps(HttpServletRequest request) {
    Locale locale = getLocale(request);
    return MedRecMessageProperties.getInstance(locale, getResources(request));
  }

  /**
   * <p>Formulates and throws client exception.<p>
   *
   * @param th
   * @param mapping
   * @param redirect
   * @throws ClientException
   */
  protected void throwClientException(Throwable th,
                                      ActionMapping mapping,
                                      String redirect)
      throws ClientException {
    if (logger.isDebugEnabled()) th.printStackTrace();
    else logger.error(th.getMessage());
    String errorLink = MedRecWebAppUtils.getServletName(mapping, redirect);
    logger.debug("errorLink: "+errorLink);
    throw new ClientException(th, errorLink);
  }

  /**
   * <p>Uniform way of handling exceptions.<p>
   *
   * @param th
   * @param mapping
   * @param request
   *
   * @return ActionForward
   */
  protected ActionForward handleException(Throwable th,
                                          HttpServletRequest request,
                                          ActionMapping mapping) {
    if (logger == null || logger.isDebugEnabled())
      th.printStackTrace();
    else
      logger.error(th);

    if (th instanceof ClientException) {
      String redirectLink = ((ClientException) th).getLink();
      logger.info("Redirect link: "+redirectLink);
      ErrorBean errorBean = new ErrorBean(MedRecWebAppUtils.getRootErrMsg(th),
          redirectLink);
      request.setAttribute("errorBean", errorBean);
      return mapping.findForward("error");
    } else {
      String link = MedRecWebAppUtils.getServletName(mapping, "home");
      logger.info("Redirect link: "+link);
      ErrorBean errorBean = new ErrorBean(MedRecWebAppUtils.getRootErrMsg(th),
          link);
      request.setAttribute("errorBean", errorBean);
      return mapping.findForward("error");
    }
  }

  /**
   * <p>This method determines the next page to which a successful login
   * should be redirected too. If a user navigates to a secure page, security
   * will redirect page to the login page.  If that user provides accurrate
   * login credentials then they are redirect page to their initial page. This
   * is done by ServletAuthentication.getTargetURLForFormAuthentication().</p>
   */
  protected ActionForward getRedirectPage(HttpServletRequest request,
                                          ActionMapping mapping)
  {
    logger.debug("Getting next redirect page.");
    // Declare local variables.
    String urlRedirect = null;
    ActionForward forward = null;

    // Return user to originating page.
    urlRedirect = ServletAuthentication.getTargetURLForFormAuthentication(
        request.getSession());

    // Determine redirection.
    // Check to see if use came from logout page.
    // If so, redirect to login.success.
    if (MedRecWebAppUtils.isNotEmpty(urlRedirect) 
        && urlRedirect.contains("logout")) {
      logger.debug("Redirecting to originating page: "+urlRedirect);
      forward = new ActionForward(urlRedirect, true);
    } else {
      forward = mapping.findForward("login.success");
    }
    return forward;
  }

  /**
   * <p>String null check.</p>
   *
   * @param str
   */
  public boolean isNotEmpty(String str) {
    return str != null && str.length() > 0;
  }

  /**
   * <p>String null check.</p>
   *
   * @param str
   */
  public boolean isEmpty(String str) {
    return !(isNotEmpty(str));
  }

  /**
   * <p>Converts a array to array of given class.</p>
   *
   * @param pObjArray Array of objects
   * @param pClazz Class of newly transformed array
   * @return Object Array of given Class objects
   */
  public Object toBeanArray(Object[] pObjArray, Class pClazz) {
    if (pObjArray != null && pObjArray.length > 0) {
      Class cl = pObjArray.getClass().getComponentType();
      logger.debug("Converting incoming "+cl.getName()+" array (len="+
          pObjArray.length+") to array of "+pClazz.getName());
      Object newObjArray = Array.newInstance(pClazz, pObjArray.length);
      for (int i=0; i<pObjArray.length; i++) {
        try {
          Constructor constr = pClazz.getConstructor(new Class[]{cl});
          logger.debug("Calling: "+constr.getName()+"("+cl+")");
          Array.set(newObjArray, i,
              constr.newInstance(new Object[]{pObjArray[i]}));
        } catch (Exception e) {
          logger.error("Unable to transform value object array.", e);
        }
      }
      return newObjArray;
    } else {
      logger.debug("Incoming array null or len=0");
      return Array.newInstance(pClazz, 0);
    }
  }

  /**
   * <p>Converts a array to array of given class.</p>
   *
   * @param pObjArray Array of objects
   * @param pClazz Class of newly transformed array
   * @return Collection Collection of given Class objects
   */
  public Collection toCollectionBean(Object[] pObjArray, Class pClazz) {
    if (pObjArray != null && pObjArray.length > 0) {
      Class cl = pObjArray.getClass().getComponentType();
      logger.debug("Converting incoming "+cl.getName()+" array (len="+
          pObjArray.length+") to array of "+pClazz.getName());
      Collection<Object> newCollection = new ArrayList<Object>();
      for (int i=0; i<pObjArray.length; i++) {
        try {
          Constructor constr = pClazz.getConstructor(new Class[]{cl});
          logger.debug("Calling: "+constr.getName()+"("+cl+")");
          newCollection.add(constr.newInstance(new Object[]{pObjArray[i]}));
        } catch (Exception e) {
          logger.error("Unable to transform value object array.", e);
        }
      }
      return newCollection;
    } else {
      logger.debug("Incoming array null or len=0");
      return new ArrayList();
    }
  }

  /**
   * <p>Converts a Collection to array of given class.</p>
   *
   * @param objCol Collection of objects
   * @param pClazz Class of newly transformed array
   * @return Object Array of given Class objects
   */
  protected Object toArray(Collection objCol, Class pClazz) {
    if (objCol != null && objCol.size() > 0) {
      Object newObjArray = null;
      Constructor constr = null;
      try {
        newObjArray = Array.newInstance(pClazz, objCol.size());
        Iterator itr = objCol.iterator();
        Object obj = (Object) itr.next();
        Class cl = obj.getClass();
        logger.debug("Converting incoming "+cl.getName()+" array (len="+
          objCol.size()+") to array of "+pClazz.getName());
        logger.debug("Calling: "+constr.getName()+"("+cl+")");
        constr = pClazz.getConstructor(new Class[]{cl});
        int i = 0;
        while (itr.hasNext()) {
          obj = (Object) itr.next();
          Array.set(newObjArray, i, constr.newInstance(new Object[]{obj}));
          i++;
        }
      } catch (Exception e) {
          logger.error("Unable to transform value object array", e);
      }
      return newObjArray;
    } else {
      logger.debug("Incoming array null or len=0");
      return Array.newInstance(pClazz, 0);
    }
  }
}

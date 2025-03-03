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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

/**
 * <p>Base Action encapsulating common lookup dispatch functionality.
 *    Use when form page contains mulitple submit buttons.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public abstract class BaseLookupDispatchAction extends LookupDispatchAction {
  private static Logger logger =
      MedRecLog4jFactory.getLogger(BaseLookupDispatchAction.class.getName());

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
      logger.debug("Mapping param = "+mapping.getParameter());
      logger.debug("Button selected = "+request.getParameter(mapping.getParameter()));
      if (request.getParameter(mapping.getParameter()) == null) {
        logger.debug("Default method call.");
        forward = defaultMethod(mapping, form, request, response);
      } else {
        logger.debug("Executing LookupDispatchAction.");
        forward = super.execute(mapping, form, request, response);
        if (logger.isDebugEnabled()) printSessionContents(request);
      }
    } catch (Exception e) {
      return handleException(e, request, mapping);
    }
    return forward;
  }

  /**
   * <p>All sub-classes must implement this method where if
   * not action is supplied the defaultMethod implementation
   * will be executed.</p>
   *
   * @param mapping  The ActionMapping used to select this instance
   * @param form  The optional ActionForm bean for this request (if any)
   * @param request  The HTTP request we are processing
   * @param response  The HTTP response we are creating
   */
  public abstract ActionForward defaultMethod(ActionMapping mapping,
                                              ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
      throws Exception;


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
   * <p>Get localize message.</p>
   *
   * @param key  The HTTP request we are processing
   */
  protected String getMessage(HttpServletRequest request,
                              Locale locale,
                              String key) {
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
    if (th instanceof ClientException) {
      logger.error(th);
      if (logger.isDebugEnabled()) th.printStackTrace();
      String redirectLink = ((ClientException) th).getLink();
      logger.info("Redirect link: "+redirectLink);
      ErrorBean errorBean = new ErrorBean(MedRecWebAppUtils.getRootErrMsg(th),
          redirectLink);
      request.setAttribute("errorBean", errorBean);
      return mapping.findForward("error");
    } else {
      logger.error(th);
      if (logger.isDebugEnabled()) th.printStackTrace();
      String link = MedRecWebAppUtils.getServletName(mapping, "home");
      logger.info("Redirect link: "+link);
      ErrorBean errorBean = new ErrorBean(MedRecWebAppUtils.getRootErrMsg(th),
          link);
      request.setAttribute("errorBean", errorBean);
      return mapping.findForward("error");
    }
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
  public Object toObjectBeanArray(Object[] pObjArray, Class pClazz) {
    logger.debug("Converting incoming array to array of "+pClazz.getName());
    if (pObjArray != null && pObjArray.length > 0) {
      Class cl = pObjArray.getClass().getComponentType();
      logger.debug("Incoming array contains the ojbects of type: "+
          cl.getName());
      Object newObjArray = Array.newInstance(pClazz, pObjArray.length);
      for (int i=0; i<pObjArray.length; i++) {
        try {
          Constructor constr = pClazz.getConstructor(new Class[]{cl});
          logger.debug("Calling the following constructor: "+constr.getName());
          Array.set(newObjArray, i, constr.newInstance(new Object[]{pObjArray[i]}));
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
    logger.debug("Converting incoming array to Collection of "+pClazz.getName());
    if (pObjArray != null && pObjArray.length > 0) {
      Class cl = pObjArray.getClass().getComponentType();
      logger.debug("Incoming array contains the ojbects of type: "+
          cl.getName());
      Collection<Object> newCollection = new ArrayList<Object>();
      for (int i=0; i<pObjArray.length; i++) {
        try {
          Constructor constr = pClazz.getConstructor(new Class[]{cl});
          logger.debug("Calling the following constructor: "+constr.getName());
          newCollection.add(constr.newInstance(new Object[]{pObjArray[i]}));
        } catch (Exception e) {
          logger.error("Unable to transform value object array.", e);
        }
      }
      return newCollection;
    } else {
      logger.debug("Incoming array null or len=0");
      return new ArrayList<Object>();
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
    logger.debug("Converting incoming collection to array of "+pClazz.getName());
    if (objCol != null && objCol.size() > 0) {
      Object newObjArray = null;
      Constructor constr = null;
      try {
        newObjArray = Array.newInstance(pClazz, objCol.size());
        Iterator itr = objCol.iterator();
        Object obj = (Object) itr.next();
        Class cl = obj.getClass();
        logger.debug("Incoming collection contains the ojbects of type: "+
            cl.getName());
        logger.debug("Calling the following constructor: "+constr.getName());
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


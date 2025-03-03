package com.bea.medrec.actions;

import com.bea.medrec.utils.MedRecLog4jFactory;
import java.util.Locale;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>Controller to handle setting of user's locale.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class ChangeLocaleAction extends BaseAction {
  private static Logger logger = MedRecLog4jFactory.getLogger(ChangeLocaleAction.class.getName());

 /**
  * <p>Process the specified HTTP request, and create the corresponding HTTP
  * response (or forward to another web component that will create it).
  * Return an <code>ActionForward</code> instance describing where and how
  * control should be forwarded.
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

    String language = (String)request.getParameter("Language");
    String country = (String)request.getParameter("Country");

    logger.debug("Changing to " + language + ", " + country);

    Locale newLocale = new Locale(language, country);
    setLocale(request, newLocale);

    Cookie languageCookie = new Cookie("Language", language);
    Cookie countryCookie = new Cookie("Country", country);
    languageCookie.setMaxAge(Integer.MAX_VALUE);
    countryCookie.setMaxAge(Integer.MAX_VALUE);
    response.addCookie(languageCookie);
    response.addCookie(countryCookie);

    return mapping.findForward("medrec.startpage");
  }
}

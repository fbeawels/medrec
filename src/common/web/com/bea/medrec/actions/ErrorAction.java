package com.bea.medrec.actions;

import com.bea.medrec.beans.ErrorBean;
import com.bea.medrec.utils.ClientException;
import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.utils.MedRecWebAppUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>Handles error presentation.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class ErrorAction extends BaseAction {
  private static Logger logger =
      MedRecLog4jFactory.getLogger(ErrorAction.class.getName());

  /**
   * <p>Process the specified HTTP request, and create the corresponding HTTP
   * response (or forward to another web component that will create it).
   * Return an <code>ActionForward</code> instance describing where and how
   * control should be forwarded.
   * <br>
   * Handles presentation of error messages.
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
      throws ClientException {
    logger.info("Error action excution");
    ErrorBean error = (ErrorBean) request.getAttribute("errorBean");
    error = (error == null ? new ErrorBean() : error);

    if (MedRecWebAppUtils.isEmpty(error.getErrMessage())) {
      error.setErrMessage("Error unknown.  Please contact system admin.");
      error.setLink("home");
    }

    request.setAttribute("errorBean", error);
    return mapping.findForward("error.page");
  }
}

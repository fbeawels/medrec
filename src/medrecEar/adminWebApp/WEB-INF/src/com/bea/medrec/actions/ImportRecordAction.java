package com.bea.medrec.actions;

import com.bea.medrec.utils.ClientException;
import com.bea.medrec.utils.MedRecLog4jFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>Import XML controller.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class ImportRecordAction extends AdminBaseAction {
  private static Logger logger =
      MedRecLog4jFactory.getLogger(ImportRecordAction.class.getName());

 /**
  * <p>Process the specified HTTP request, and create the corresponding HTTP
  * response (or forward to another web component that will create it).
  * Return an <code>ActionForward</code> instance describing where and how
  * control should be forwarded.
  * <br>
  * Handles incoming XML medical record requests.
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
    throws ClientException, Exception
  {
    // Declare local variables.
    String action = request.getParameter(ACTION);
    String filename = null;
    logger.debug("Action: "+action);

    if (isNotEmpty(action)
      && action.equals(XML_UPLOAD)) {
      filename = request.getParameter(XML_FILE);
      logger.debug("XML filename: "+filename);
      if (isEmpty(filename)) {
        throw new ClientException("File "+filename+" not found.");
      }

      try {
        // Send registration to be processed.
        // FIXME - cwall 3/30/04
        // Until the following CR is resolved, XML upload functionality is inactive.
        // CR175281 dev2dev XMLBeans schema upload produces 500 - "User has no access to upload files"
        logger.info("***************************");
        logger.info("Until the following CR is resolved, XML upload functionality is inactive.");
        logger.info("CR175281 dev2dev XMLBeans schema upload produces 500 - \"User has no access to upload files\"");
        logger.info("***************************");
        getAdminSession().processXMLUpload(filename);
      }
      catch(Exception e) {
        throwClientException(e, mapping, "view.import.files");
      }
    }
    return mapping.findForward("confirm.import");
  }
}

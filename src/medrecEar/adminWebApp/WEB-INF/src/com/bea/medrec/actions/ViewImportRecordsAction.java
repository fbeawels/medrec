package com.bea.medrec.actions;

import com.bea.medrec.utils.BeanHelper;
import com.bea.medrec.utils.ClientException;
import com.bea.medrec.xml.MedRecXMLProcessor;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>Controller that retrieves all pending XML medical records.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class ViewImportRecordsAction extends AdminBaseAction {

  /**
   * <p>Process the specified HTTP request, and create the corresponding HTTP
   * response (or forward to another web component that will create it).
   * Return an <code>ActionForward</code> instance describing where and how
   * control should be forwarded.
   * <br>
   * View pending incoming XML medical records..
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
      throws ClientException, Exception {
    Collection<Object> xmlFiles = null;
    Collection<Object> importBeans = null;
    MedRecXMLProcessor xmlProcessor = null;

    try {
      // Get XML processor, then all pending xml files.
      xmlProcessor = MedRecXMLProcessor.getInstance();
      xmlFiles = xmlProcessor.getIncomingXMLFiles();
      importBeans = BeanHelper.toImportBeanCollection(xmlFiles);
    } catch (Exception e) {
      throwClientException(e, mapping, "home");
    }
    // Set on request to be display by view.
    request.setAttribute(IMPORT_BEANS, importBeans);

    return mapping.findForward("view.import.files");
  }
}

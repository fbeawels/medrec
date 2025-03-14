package com.bea.medrec.actions;

import com.bea.medrec.beans.SearchBean;
import com.bea.medrec.utils.ClientException;
import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.value.Patient;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>Search controller.  Handles all search requests.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class SearchResultsAction extends PhysBaseLookupDispatchAction {

  private static Logger logger =
      MedRecLog4jFactory.getLogger(SearchResultsAction.class.getName());

  /**
   * <p>Mapping of action to method.</p>
   *
   * @return Map
   */
  protected Map getKeyMethodMap() {
    Map<String,String> map = new HashMap<String,String>();
    map.put("button.Search", "search");
    return map;
  }

  /**
   * <p>Default behavior.</p>
   *
   * @return ActionForward
   */
  public ActionForward defaultMethod(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
      throws Exception {
    logger.info("Default action method.");
    SearchBean search = (SearchBean) super.getSessionAttribute(request,
        PREVIOUS_SEARCH);

    // If search null, then new search.
    if (search == null) return mapping.findForward("search.home");

    // Process search.
    try {
      processSearch(request, search);
    } catch (Exception e) {
      throwClientException(e, mapping, "search.home");
    }
    return mapping.findForward("search.results");
  }

  /**
   * <p>Searches based on user's criteria.</p>
   *
   * @return ActionForward
   */
  public ActionForward search(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
      throws ClientException, Exception {
    logger.info("New search.");
    SearchBean search = (SearchBean) form;

    // Validate search.
    // Report any errors we have discovered back to the original form.
    ActionErrors errors = search.validate();
    if (!errors.isEmpty()) {
      saveErrors(request, errors);
      return mapping.findForward("search.invalid");
    }

    // Process search.
    try {
      processSearch(request, search);
    } catch (Exception e) {
      throwClientException(e, mapping, "search.home");
    }
    return mapping.findForward("search.results");
  }

  /**
   * <p>Search for patients.  Once retrieved, store the results to the
   * session.  Also save search criteria to handle when user returns to
   * search results page.</p>
   */
  private void processSearch(HttpServletRequest request, SearchBean search)
      throws Exception {
    logger.info("Process search.");

    // pass transformed search value object.
    Patient[] patients = getPhysicianSession().searchPatients(
        search.toSearch());

    logger.debug("Removing old patient.");
    super.removeSessionAttribute(request, PATIENT_BEAN);

    logger.debug("Setting patient result collection.");
    super.setSessionAttribute(request, PATIENT_BEANS,
        toObjectBeanArray(patients, com.bea.medrec.beans.PatientBean.class));

    logger.debug("Setting search criteria.");
    super.setSessionAttribute(request, PREVIOUS_SEARCH, search);

  }
}

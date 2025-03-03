package com.bea.medrec.actions;

import com.bea.medrec.beans.PatientBean;
import com.bea.medrec.beans.PhysicianBean;
import com.bea.medrec.beans.RecordBean;
import com.bea.medrec.utils.ClientException;
import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.utils.MedRecWebAppUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.*;

/**
 * <p>Controller that handles the creation of a visit
 * containing a new record and, if applicable, prescriptions.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class CreateVisitAction extends PhysBaseLookupDispatchAction {
  private static Logger logger =
      MedRecLog4jFactory.getLogger(CreateVisitAction.class.getName());

  /**
   * <p>The following is done because this Controller has three functions:
   * <ul>
   * <li>new visit (fresh form)
   * <li>delete existing prescription
   * <li>submitting visit
   * </ul>
   * New visit and deleting an existing prescription actions are triggered
   * by query params within a link, not through a form submit button,
   * thus need manual method redirection. Submitting
   * a new visit follows the standard dispatch action.</p>
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
      throws ClientException, Exception {
    logger.debug("Create visit execute.");
    // Check action param.
    String action = request.getParameter(mapping.getParameter());
    // If action is new visit or delete prescription, redirect appropriately.
    if (NEW_VISIT.equals(action))
      return newVisit(mapping, form, request, response);
    else if (DELETE_PRESCRIPTION.equals(action))
      return deletePrescription(mapping, form, request, response);
    else
      return super.execute(mapping, form, request, response);
  }

  /**
   * <p>Mapping of action to method.</p>
   *
   * @return Map
   */
  protected Map getKeyMethodMap() {
    Map<String,String> map = new HashMap<String,String>();
    map.put("button.Prescribe.Medication", "prescribePrescription");
    map.put("button.Save", "save");
    map.put("button.Reset", "reset");
    map.put("button.Cancel", "cancel");
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
    // Set new token for dup form submission check.
    logger.info("Default action method.");
    logger.debug("Setting token.");
    saveToken(request);
    return mapping.findForward("create.visit");
  }

  /**
   * <p>Cancel new visit creation.</p>
   *
   * @return ActionForward
   */
  public ActionForward cancel(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
      throws Exception {
    logger.info("Cancel create visit.");
    ActionForward forward = null;
    try {
      // Remove record from session.
      logger.info("Cleaning session of recordBean.");
      super.removeSessionAttribute(request, RECORD_BEAN);
      forward = mapping.findForward("cancel.record.success");
    } catch (Exception e) {
      return handleException(e, request, mapping);
    }
    return forward;
  }

  /**
   * <p>Delete prescribed prescription.</p>
   *
   * @return ActionForward
   */
  public ActionForward deletePrescription(ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
      throws Exception {
    logger.info("Remove specified prescription.");

    // Declare local variables
    String id = null;
    RecordBean recordBean = null;

    // Remove prescribe prescription from record.
    id = (String) request.getParameter(PRESCRIPTION_ID);
    if (isNotEmpty(id)) {
      // Get record from session.
      recordBean = (RecordBean) getSessionAttribute(request,
          RECORD_BEAN);
      // Remove prescriptions from record.
      recordBean.removePrescription(id);
    }
    return mapping.findForward("create.visit");
  }

  /**
   * <p>Start creating new visit.</p>
   *
   * @return ActionForward
   */
  public ActionForward newVisit(ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
      throws Exception {
    logger.info("Create new visit.");

    // Make sure session is clean.
    super.removeSessionAttribute(request, RECORD_BEAN);

    // Set new token for dup form submission check.
    logger.debug("Setting token.");
    saveToken(request);

    return mapping.findForward("create.visit");
  }

  /**
   * <p>Prescribe medication during this visit.</p>
   *
   * @return ActionForward
   */
  public ActionForward prescribePrescription(ActionMapping mapping,
                                   ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
      throws IOException {
    ActionForward forward = null;
    try {
      forward = prescribePrescriptionMethod(mapping, request);
    } catch (Exception e) {
      return handleException(e, request, mapping);
    }
    return forward;
  }

  /**
   * <p>Prescribe medication during this visit implementation.</p>
   *
   * @return ActionForward
   */
  private ActionForward prescribePrescriptionMethod(ActionMapping mapping,
                                          HttpServletRequest request)
      throws Exception {
    // Declare and initialize local variables
    RecordBean recordBean = (RecordBean) getSessionAttribute(request,
        RECORD_BEAN);

    // Prescribe prescription saves visit to session.
    logger.info("Setting recordBean to session then redirect to create "+
        "prescription.");
    logger.debug(recordBean.toString());

    // Save to session.
    setSessionAttribute(request, RECORD_BEAN, recordBean);
    return mapping.findForward("create.prescription");
  }

  /**
   * <p>Reset visit information.</p>
   *
   * @return ActionForward
   */
  public ActionForward reset(ActionMapping mapping,
                             ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response)
      throws Exception {
    logger.info("Reset visit page.");
    // Reset record.
    super.removeSessionAttribute(request, RECORD_BEAN);
    return mapping.findForward("create.visit");
  }

  /**
   * <p>Saves visit.</p>
   *
   * @return ActionForward
   */
  public ActionForward save(ActionMapping mapping,
                            ActionForm form,
                            HttpServletRequest request,
                            HttpServletResponse response)
      throws ClientException, Exception {
    logger.info("Saving record.");

    // Declare and initialize local variables
    RecordBean recordBean = (RecordBean) getSessionAttribute(request,
        RECORD_BEAN);
    ActionErrors errors = new ActionErrors();

    if (!isTokenValid(request)) {
      logger.warn("Token not valid.");
      errors.add("duplicate.submit", new ActionError("duplicate.submit"));
    }

    resetToken(request);
    if (!errors.isEmpty()) {
      saveErrors(request, errors);
      saveToken(request);
      return mapping.findForward("validate.record.failure");
    }

    // Save record to MedRec.
    try {
      // Set date, and patient and physician ids.
      PatientBean patient =
          (PatientBean) getSessionAttribute(request, PATIENT_BEAN);
      PhysicianBean physician =
          (PhysicianBean) getSessionAttribute(request, PHYSICIAN_BEAN);

      recordBean.setPatientId(patient.getId());
      recordBean.setDate(MedRecWebAppUtils.getCurrentDate());

      // Let's see the record.
      logger.debug(recordBean.toString());

      // Make sure session is clean.
      super.removeSessionAttribute(request, RECORD_BEAN);

      // Pass transformed record value object.
      getPhysicianSession().addRecord(physician.toPhysician(),
          recordBean.toRecord());

      // Clean session of record.
      super.removeSessionAttribute(request, RECORD_BEAN);
    } catch (Exception e) {
      throwClientException(e, mapping, "validate.record.failure");
    }
    return mapping.findForward("save.record.success");
  }
}

package com.bea.medrec.actions;

import com.bea.medrec.beans.PatientBean;
import com.bea.medrec.beans.PhysicianBean;
import com.bea.medrec.beans.PrescriptionBean;
import com.bea.medrec.beans.RecordBean;
import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.utils.MedRecWebAppUtils;
import com.bea.medrec.value.Prescription;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>Controller to handle the creation of a prescriptions.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class CreatePrescriptionAction extends PhysBaseLookupDispatchAction {

  private static Logger logger =
      MedRecLog4jFactory.getLogger(CreatePrescriptionAction.class.getName());

 /**
  * <p>Mapping of action to method.</p>
  *
  * @return Map
  */
  protected Map getKeyMethodMap() {
    Map<String,String> map = new HashMap<String,String>();
    map.put("button.Save", "save");
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
    throws Exception
  {
    // First timers
    // Set new token for dup form submission check.
    logger.info("Default action method.");
    logger.debug("setting token.");
    saveToken(request);
    return mapping.findForward("create.prescription");
  }


 /**
  * <p>Cancel prescribe medication.</p>
  *
  * @return ActionForward
  */
  public ActionForward cancel(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    throws Exception
  {
    logger.info("Cancel create prescription.");
    ActionForward forward = null;
    try {
      forward = mapping.findForward("cancel.prescription");
    }
    catch(Exception e) {
      return handleException(e, request, mapping);
    }
    return forward;
  }

 /**
  * <p>Save prescription to be recorded.</p>
  *
  * @return ActionForward
  */
  public ActionForward save(ActionMapping mapping,
                            ActionForm form,
                            HttpServletRequest request,
                            HttpServletResponse response)
    throws Exception
  {
    logger.info("Save prescription.");
    PrescriptionBean prescriptionBean = (PrescriptionBean)form;
    RecordBean recordBean = null;
    Prescription[] prescriptionVOs = null;

    // Set patient and physician ids and current date.
    setPrescriptionValues(request, prescriptionBean);

    // Every's okay.  Get record from session,
    // add prescription, then reattach to session.
    recordBean = (RecordBean)getSessionAttribute(request, RECORD_BEAN);

    // Add new prescription to record.
    logger.debug("Adding : "+prescriptionBean.toString());
    recordBean.addPrescription(prescriptionBean);
    logger.debug("Num of prescriptions: "+recordBean.getNumOfPrescriptions());

    // Set record to session.
    setSessionAttribute(request, RECORD_BEAN, recordBean);
    return mapping.findForward("save.prescription.success");
  }

 /**
  * Sets known values from the session.
  */
  private void setPrescriptionValues(HttpServletRequest request,
                                     PrescriptionBean prescriptionBean) {
    logger.info("Setting patient and physician ids from session.");

    PatientBean patient =
      (PatientBean)getSessionAttribute(request, PATIENT_BEAN);
    PhysicianBean physician =
      (PhysicianBean)getSessionAttribute(request, PHYSICIAN_BEAN);

    prescriptionBean.setPatientId(patient.getId());
    prescriptionBean.setDatePrescribed(MedRecWebAppUtils.getCurrentDate());
  }
}

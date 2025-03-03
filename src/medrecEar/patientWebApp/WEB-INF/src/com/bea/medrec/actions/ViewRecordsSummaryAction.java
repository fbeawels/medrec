package com.bea.medrec.actions;

import com.bea.medrec.beans.PatientBean;
import com.bea.medrec.beans.RecordBean;
import com.bea.medrec.beans.PrescriptionBean;
import com.bea.medrec.utils.BeanHelper;
import com.bea.medrec.utils.ClientException;
import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.utils.MedRecWebAppUtils;
import com.bea.medrec.value.RecordsSummary;
import com.bea.medrec.value.Record;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>Retrieves all records for a patient.  Records are returned
 * in abbreviated form in order to display a summary of
 * each record.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class ViewRecordsSummaryAction extends PatientBaseAction {

  private static Logger logger =
      MedRecLog4jFactory.getLogger(ViewRecordsSummaryAction.class.getName());

 /**
  * <p>Process the specified HTTP request, and create the corresponding HTTP
  * response (or forward to another web component that will create it).
  * Return an <code>ActionForward</code> instance describing where and how
  * control should be forwarded.
  * <br>
  * Retrieves a patients entire medical record history.
  * Information includes summarized views of records and prescriptions.
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
    logger.debug("Getting patients record summary.");
    PatientBean patientBean = (PatientBean)getSessionAttribute(request,
      PATIENT_BEAN);
    Integer patientId = null;
    RecordsSummary recordsSummary = null;

    if (MedRecWebAppUtils.isNotEmpty(patientBean.getId())) {
      logger.debug("Patient Id: "+patientBean.getId());

      try {
        patientId = Integer.valueOf(patientBean.getId());
        recordsSummary = getRecordSession().getRecordsSummary(patientId);
      }
      catch(Exception e) {
        throwClientException(e, mapping, "search.results");
      }
      logger.debug("Found records: "+recordsSummary.toString());
      logger.debug("Setting recordBeans on request");
      request.setAttribute(RECORD_BEANS,
          toBeanArray(recordsSummary.getRecords(),
              com.bea.medrec.beans.RecordBean.class));
      logger.debug("Setting prescriptionBeans on request");
      request.setAttribute(PRESCRIPTION_BEANS,
          toBeanArray(recordsSummary.getPrescriptions(),
              com.bea.medrec.beans.PrescriptionBean.class));
    }

    return mapping.findForward("view.records.summary");
  }
}

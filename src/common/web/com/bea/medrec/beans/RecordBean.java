package com.bea.medrec.beans;

import com.bea.medrec.utils.MedRecWebAppUtils;
import com.bea.medrec.value.Prescription;
import com.bea.medrec.value.Record;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.Resources;

/**
 * <p>Form bean for the user record pages.
 * This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>patientId</b> - Hidden patientId value
 * <li><b>physicianId</b> - Hidden firstName value
 * <li><b>symptoms</b> - Entered symptoms value
 * <li><b>diagnosis</b> - Entered diagnosis value
 * <li><b>notes</b> - Entered notes value
 * </ul>
 * </p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public final class RecordBean extends BaseBean {
  // Instance Variables
  private String patientId = "";
  private String physicianName = "";
  private String date = "";
  private String symptoms = "";
  private String diagnosis = "";
  private String notes = "";
  private VitalSignsBean vitalSignsBean = new VitalSignsBean();
  private ArrayList<Object> prescriptionBeans = new ArrayList<Object>();

  // Constuctors
  public RecordBean() {  }

  public RecordBean(Record record) {
    if (record == null) return;
    super.setId(record.getId());
    this.patientId = toStr(record.getPatientId());
    this.physicianName = record.getPhysicianName();
    this.date = MedRecWebAppUtils.getDisplayDate(record.getDate());
    this.symptoms = record.getSymptoms();
    this.diagnosis = record.getDiagnosis();
    this.notes = record.getNotes();
    if (record.getVitalSigns() != null)
      this.vitalSignsBean = new VitalSignsBean(record.getVitalSigns());
    this.prescriptionBeans = (ArrayList<Object>)toCollectionBean(
        record.getPrescriptions(), com.bea.medrec.beans.PrescriptionBean.class);
  }

  public RecordBean(Integer id,
                    String patientId,
                    String physicianName,
                    String date,
                    String symptoms,
                    String diagnosis,
                    String notes,
                    VitalSignsBean vitalSignsBean,
                    ArrayList<Object> prescriptionBeans) {
    super.setId(id);
    this.patientId = patientId;
    this.physicianName = physicianName;
    this.date = date;
    this.symptoms = symptoms;
    this.diagnosis = diagnosis;
    this.notes = notes;
    this.vitalSignsBean = vitalSignsBean;
    this.prescriptionBeans = prescriptionBeans;
  }

  public RecordBean(Integer id,
                    Integer patientId,
                    String physicianName,
                    String date,
                    String symptoms,
                    String diagnosis,
                    String notes,
                    VitalSignsBean vitalSignsBean,
                    ArrayList<Object> prescriptionBeans) {
    super.setId(id);
    this.patientId = toStr(patientId);
    this.physicianName = physicianName;
    this.date = date;
    this.symptoms = symptoms;
    this.diagnosis = diagnosis;
    this.notes = notes;
    this.vitalSignsBean = vitalSignsBean;
    this.prescriptionBeans = prescriptionBeans;
  }

  public RecordBean(String id,
                    String physicianName,
                    String date,
                    String symptoms,
                    String diagnosis) {
    super.setId(id);
    this.physicianName = physicianName;
    this.date = date;
    this.symptoms = symptoms;
    this.diagnosis = diagnosis;
  }

  public RecordBean(Integer id,
                    String physicianName,
                    String date,
                    String symptoms,
                    String diagnosis) {
    super.setId(id);
    this.physicianName = physicianName;
    this.date = date;
    this.symptoms = symptoms;
    this.diagnosis = diagnosis;
  }

  // Getters
  public String getPatientId() {
    return this.patientId;
  }

  public String getPhysicianName() {
    return "Dr. "+this.physicianName;
  }

  public String getDate() {
    return this.date;
  }

  public String getSymptoms() {
    return this.symptoms;
  }

  public String getDiagnosis() {
    return this.diagnosis;
  }

  public String getNotes() {
    return this.notes;
  }

  public VitalSignsBean getVitalSignsBean() {
    if (vitalSignsBean == null) vitalSignsBean = new VitalSignsBean();
    return vitalSignsBean;
  }

  public Collection getPrescriptionBeans() {
    return this.prescriptionBeans;
  }

  // Setters
  public void setPatientId(String pPatientId) {
    this.patientId = pPatientId;
  }

  public void setPhysicianName(String pPhysicianName) {
    this.physicianName = pPhysicianName;
  }

  public void setDate(String pDate) {
    this.date = pDate;
  }

  public void setSymptoms(String pSymptoms) {
    this.symptoms = pSymptoms;
  }

  public void setDiagnosis(String pDiagnosis) {
    this.diagnosis = pDiagnosis;
  }

  public void setNotes(String pNotes) {
    this.notes = pNotes;
  }

  public void setVitalSignsBean(VitalSignsBean pVitalSignsBean) {
    this.vitalSignsBean = pVitalSignsBean;
  }

  public void setPrescriptions(ArrayList<Object> pPrescriptionBeans) {
    this.prescriptionBeans = pPrescriptionBeans;
  }

  // Update method
  public void update(RecordBean recordBean) {
    this.symptoms = recordBean.getSymptoms();
    this.diagnosis = recordBean.getDiagnosis();
    this.notes = recordBean.getNotes();
    this.vitalSignsBean.update(recordBean.getVitalSignsBean());
  }

  // utility
  public int getNumOfPrescriptions() {
    return (this.prescriptionBeans == null ? 0 : this.prescriptionBeans.size());
  }

  public void addPrescription(PrescriptionBean pPrescriptionBean) {
    this.prescriptionBeans.add(pPrescriptionBean);
  }

  public void removePrescription(String pPrescriptionId) {
    if (getNumOfPrescriptions() > 1)
      this.prescriptionBeans.remove(Integer.parseInt(pPrescriptionId));
    else
      this.prescriptionBeans.clear();
  }

  /**
   * <p>Validate record.</p>
   *
   * @param mapping
   * @param request
   *
   * @return ActionErrors
   */
  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {
    ActionErrors errors = new ActionErrors();
    // only validate if the user has clicked "Login"
    String loginSubmit = Resources.getMessage(request, "button.Save");
    if (loginSubmit.equals(request.getParameter("action"))) {
      errors = super.validate(mapping, request);
    }
    return errors;
  }

  /**
   * <p>Converts record presentation bean to record value object.</p>
   *
   * @return Record
   */
  public Record toRecord() {
    return new Record(getPatientId(),
        str2Calendar(getDate()),
        getSymptoms(),
        getDiagnosis(),
        getNotes(),
        null, /* FIXME physician */
        getVitalSignsBean().toVitalSigns(),
        toPrescriptionArray()
        );
  }

  private Prescription[] toPrescriptionArray() {
    Prescription[] prescriptionVOs = null;
    if (this.prescriptionBeans != null || this.prescriptionBeans.size() > 0)
      prescriptionVOs = new Prescription[this.prescriptionBeans.size()];
    else
      prescriptionVOs = new Prescription[0];

    int i = 0;
    for (Iterator iterator = prescriptionBeans.iterator(); iterator.hasNext();
         i++) {
      PrescriptionBean prescriptionBean = (PrescriptionBean) iterator.next();
      prescriptionVOs[i] = prescriptionBean.toPrescription();
    }
    return prescriptionVOs;
  }

  // Utility methods
  public String toString() {
    StringBuffer str = new StringBuffer();
    str.append("Record [");
    str.append("PatId: "+patientId);
    str.append(" | PhysName: "+physicianName);
    str.append(" | DOB: "+date);
    str.append(" | Syms: "+symptoms);
    str.append(" | Diag: "+diagnosis);
    str.append(" | Notes: "+notes);
    str.append(" | Vitals: "+
        (vitalSignsBean == null ? "null" : vitalSignsBean.toString()));
    str.append(" | "+MedRecWebAppUtils.col2Str(prescriptionBeans));
    str.append("]");
    return str.toString();
  }

}

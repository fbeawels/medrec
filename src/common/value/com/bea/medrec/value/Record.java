package com.bea.medrec.value;

import java.util.Calendar;

/**
 * <p>Represents information about a record.  This includes
 * the record's vital signs.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public final class Record extends BaseVO {

  // Attributes
  private Integer patientId;
  private Calendar date;
  private String symptoms;
  private String diagnosis;
  private String notes;
  private Physician physician;
  private VitalSigns vitalSigns;
  private Prescription[] prescriptions;

  // Constructors
  public Record() {
  }

  public Record(String patientId,
                Calendar date,
                String symptoms,
                String diagnosis,
                String notes,
                Physician physician,
                VitalSigns vitalSigns,
                Prescription[] prescriptions) {
    this.patientId = str2Integer(patientId);
    this.date = date;
    this.symptoms = symptoms;
    this.diagnosis = diagnosis;
    this.notes = notes;
    this.physician = physician;
    this.vitalSigns = vitalSigns;
    this.prescriptions = prescriptions;
  }

  public Record(Integer id,
                Integer patientId,
                Calendar date,
                String symptoms,
                String diagnosis,
                String notes,
                Physician physician,
                VitalSigns vitalSigns,
                Prescription[] prescriptions) {
    super.setId(id);
    this.patientId = patientId;
    this.date = date;
    this.symptoms = symptoms;
    this.diagnosis = diagnosis;
    this.notes = notes;
    this.physician = physician;
    this.vitalSigns = vitalSigns;
    this.prescriptions = prescriptions;
  }

  // Getters
  public Integer getPatientId() {
    return this.patientId;
  }

  public Calendar getDate() {
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

  public Physician getPhysician() {
    return this.physician;
  }

  public VitalSigns getVitalSigns() {
    return this.vitalSigns;
  }

  public Prescription[] getPrescriptions() {
    return (this.prescriptions == null ? new Prescription[0] : this.prescriptions);
  }

  public Integer getPhysicianId() {
    return getPhysician().getId();
  }

  public String getPhysicianName() {
    return this.physician.getFirstName()+" "+this.physician.getMiddleName() +
        " "+this.physician.getLastName();
  }

  // Setters
  public void setPatientId(Integer pPatientId) {
    this.patientId = pPatientId;
  }

  public void setDate(Calendar pDate) {
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

  public void setPhysician(Physician pPhysician) {
    this.physician = pPhysician;
  }

  public void setVitalSigns(VitalSigns pVitalSigns) {
    this.vitalSigns = pVitalSigns;
  }

  public void setPrescriptions(Prescription[] pPrescriptionVOs) {
    this.prescriptions = pPrescriptionVOs;
  }

  public int getNumOfPrescriptions() {
    return (this.prescriptions == null ? 0 : this.prescriptions.length);
  }

  // Utitily
  public String toString() {
    StringBuffer str = new StringBuffer();
    str.append("RECORD [Id: "+super.getId());
    str.append(" | PatId: "+toStr(this.patientId));
    str.append(" | DOB: " +
        (this.date == null ? "null" : getDisplayDate(this.date)));
    str.append(" | Syms: "+this.symptoms);
    str.append(" | Diag: "+this.diagnosis);
    str.append(" | Notes: "+this.notes);
    str.append(" | " +
        (this.physician == null ? "PHYSICIAN: [null]" : this.physician.toString()));
    str.append(" | " +
        (this.vitalSigns == null ? "VITALS: [null]" : this.vitalSigns.toString()));
    str.append(" | "+prescriptionsToString());
    str.append("]");

    return str.toString();
  }

  private String prescriptionsToString() {
    StringBuffer str = new StringBuffer();
    if (getNumOfPrescriptions() > 0) {
      str.append(" Num of prescriptions: "+getNumOfPrescriptions()+" |");
      for (int i = 0; i < getNumOfPrescriptions(); i++) {
        str.append(" "+(prescriptions[i].toString()));
      }
    } else {
      str.append("PRESCRIPTIONS: [null]");
    }
    return str.toString();
  }

}

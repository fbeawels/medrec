/**
 * Generated from schema type t=Record@java:com.bea.medrec.value
 */
package com.bea.medrec.value;

public class Record extends com.bea.medrec.value.BaseVO {

  private java.lang.Integer id;

  public java.lang.Integer getId() {
    return this.id;
  }

  public void setId(java.lang.Integer id) {
    this.id = id;
  }

  private java.lang.Integer patientId;

  public java.lang.Integer getPatientId() {
    return this.patientId;
  }

  public void setPatientId(java.lang.Integer patientId) {
    this.patientId = patientId;
  }

  private java.util.Calendar date;

  public java.util.Calendar getDate() {
    return this.date;
  }

  public void setDate(java.util.Calendar date) {
    this.date = date;
  }

  private java.lang.String symptoms;

  public java.lang.String getSymptoms() {
    return this.symptoms;
  }

  public void setSymptoms(java.lang.String symptoms) {
    this.symptoms = symptoms;
  }

  private java.lang.String diagnosis;

  public java.lang.String getDiagnosis() {
    return this.diagnosis;
  }

  public void setDiagnosis(java.lang.String diagnosis) {
    this.diagnosis = diagnosis;
  }

  private java.lang.String notes;

  public java.lang.String getNotes() {
    return this.notes;
  }

  public void setNotes(java.lang.String notes) {
    this.notes = notes;
  }

  private com.bea.medrec.value.Physician physician;

  public com.bea.medrec.value.Physician getPhysician() {
    return this.physician;
  }

  public void setPhysician(com.bea.medrec.value.Physician physician) {
    this.physician = physician;
  }

  private com.bea.medrec.value.VitalSigns vitalSigns;

  public com.bea.medrec.value.VitalSigns getVitalSigns() {
    return this.vitalSigns;
  }

  public void setVitalSigns(com.bea.medrec.value.VitalSigns vitalSigns) {
    this.vitalSigns = vitalSigns;
  }

  private com.bea.medrec.value.Prescription[] prescriptions;

  public com.bea.medrec.value.Prescription[] getPrescriptions() {
    return this.prescriptions;
  }

  public void setPrescriptions(com.bea.medrec.value.Prescription[] prescriptions) {
    this.prescriptions = prescriptions;
  }

}

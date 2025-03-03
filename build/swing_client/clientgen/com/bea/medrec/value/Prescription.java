/**
 * Generated from schema type t=Prescription@java:com.bea.medrec.value
 */
package com.bea.medrec.value;

public class Prescription extends com.bea.medrec.value.BaseVO {

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

  private java.lang.Integer recordId;

  public java.lang.Integer getRecordId() {
    return this.recordId;
  }

  public void setRecordId(java.lang.Integer recordId) {
    this.recordId = recordId;
  }

  private java.util.Calendar datePrescribed;

  public java.util.Calendar getDatePrescribed() {
    return this.datePrescribed;
  }

  public void setDatePrescribed(java.util.Calendar datePrescribed) {
    this.datePrescribed = datePrescribed;
  }

  private java.lang.String drug;

  public java.lang.String getDrug() {
    return this.drug;
  }

  public void setDrug(java.lang.String drug) {
    this.drug = drug;
  }

  private java.lang.String dosage;

  public java.lang.String getDosage() {
    return this.dosage;
  }

  public void setDosage(java.lang.String dosage) {
    this.dosage = dosage;
  }

  private java.lang.String frequency;

  public java.lang.String getFrequency() {
    return this.frequency;
  }

  public void setFrequency(java.lang.String frequency) {
    this.frequency = frequency;
  }

  private java.lang.Integer refillsRemaining;

  public java.lang.Integer getRefillsRemaining() {
    return this.refillsRemaining;
  }

  public void setRefillsRemaining(java.lang.Integer refillsRemaining) {
    this.refillsRemaining = refillsRemaining;
  }

  private java.lang.String instructions;

  public java.lang.String getInstructions() {
    return this.instructions;
  }

  public void setInstructions(java.lang.String instructions) {
    this.instructions = instructions;
  }

}

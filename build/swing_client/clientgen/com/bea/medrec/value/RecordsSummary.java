/**
 * Generated from schema type t=RecordsSummary@java:com.bea.medrec.value
 */
package com.bea.medrec.value;

public class RecordsSummary implements java.io.Serializable {

  private com.bea.medrec.value.Record[] records;

  public com.bea.medrec.value.Record[] getRecords() {
    return this.records;
  }

  public void setRecords(com.bea.medrec.value.Record[] records) {
    this.records = records;
  }

  private com.bea.medrec.value.Prescription[] prescriptions;

  public com.bea.medrec.value.Prescription[] getPrescriptions() {
    return this.prescriptions;
  }

  public void setPrescriptions(com.bea.medrec.value.Prescription[] prescriptions) {
    this.prescriptions = prescriptions;
  }

}

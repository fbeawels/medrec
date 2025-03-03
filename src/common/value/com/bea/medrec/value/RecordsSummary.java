package com.bea.medrec.value;

import java.io.Serializable;

/**
 * <p>Encapsulates patient's medical record summary.
 * Includes List of abbreviated records and
 * List of current and recent prescriptions.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public final class RecordsSummary implements Serializable {

  private Record[] records;
  private Prescription[] prescriptions;

  public RecordsSummary() {
  }

  public RecordsSummary(Record[] records, Prescription[] prescriptions) {
    this.records = records;
    this.prescriptions = prescriptions;
  }

  public Record[] getRecords() {
    return this.records;
  }

  public Prescription[] getPrescriptions() {
    return this.prescriptions;
  }

  public void setRecords(Record[] records) {
    this.records = records;
  }

  public void setPrescriptions(Prescription[] prescriptions) {
    this.prescriptions = prescriptions;
  }

  // Utility methods
  public int recordsSize() {
    if (records == null)
      return 0;
    else
      return records.length;
  }

  public int prescriptionSize() {
    if (prescriptions == null)
      return 0;
    else
      return prescriptions.length;
  }

  public String toString() {
    StringBuffer str = new StringBuffer();
    str.append("RecordsSummary [");
    str.append("Num of Records: "+recordsSize());
    str.append(" | Num of Prescriptions: "+prescriptionSize());
    str.append("]");

    return str.toString();
  }
}

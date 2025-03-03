package com.bea.medrec.value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>Encapsulates patient's medical record summary.
 * Includes List of abbreviated records and
 * List of current and recent prescriptions.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public final class MedicalRecord implements Serializable {

  private Integer srcId;
  private String srcName;
  private Patient patient;
  private List<Record> records;
  private User user;

  public MedicalRecord() {
  }

  public MedicalRecord(Integer srcId, String srcName) {
    this.srcId = srcId;
    this.srcName = srcName;
  }

  public MedicalRecord(Integer srcId,
                       String srcName,
                       Patient patient,
                       List<Record> records) {
    this.srcId = srcId;
    this.srcName = srcName;
    this.patient = patient;
    this.records = records;
  }

  public MedicalRecord(Integer srcId,
                       String srcName,
                       Patient patient,
                       List<Record> records,
                       User user) {
    this.srcId = srcId;
    this.srcName = srcName;
    this.patient = patient;
    this.records = records;
    this.user = user;
  }

  public MedicalRecord(Patient patient, List<Record> records) {
    this.patient = patient;
    this.records = records;
  }

  public Integer getSrcId() {
    return this.srcId;
  }

  public String getSrcName() {
    return this.srcName;
  }

  public Patient getPatient() {
    return this.patient;
  }

  public List<Record> getRecords() {
    return this.records == null ? new ArrayList<Record>() : this.records;
  }

  public User getUser() {
    return this.user;
  }

  public void setSrcId(Integer srcId) {
    this.srcId = srcId;
  }

  public void setSrcName(String srcName) {
    this.srcName = srcName;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  public void setRecords(List<Record> records) {
    this.records = records;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void addRecord(Record record) {
    if (records == null) records = new ArrayList<Record>();
    records.add(record);
  }

  public int numOfRecords() {
    return (records != null ? records.size() : 0);
  }

  public String toString() {
    StringBuffer str = new StringBuffer();
    str.append("MedicalRecord [");
    str.append("SrcId: "+srcId);
    str.append(" | SrcName: "+srcName);
    str.append(" | User: "+user.toString());
    str.append(" | Patient: "+patient.toString());
    str.append(" | Num of Records: "+getRecordCount());
    if (numOfRecords() > 0) {
      Iterator patItr = records.iterator();
      while (patItr.hasNext()) {
        Record record = (Record) patItr.next();
        str.append(" | "+record.toString());
      }
    }
    str.append("]");
    return str.toString();
  }

  private String getRecordCount() {
    return (this.records == null ? "0" : String.valueOf(records.size()));
  }
}

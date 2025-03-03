package com.bea.medrec.entities;

import com.bea.medrec.utils.MedRecUtils;
import com.bea.medrec.value.Prescription;
import java.sql.Date;
import java.util.Calendar;
import javax.ejb.CreateException;
import weblogic.ejb.GenericEntityBean;
import weblogic.ejbgen.*;

/**
 * <p>PrescriptionEJB is an Container Managed EntityBean that
 * manipulates record persisted data.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
@AutomaticKeyGeneration(name = "PRESCRIPTION_SEQ",
                        type = AutomaticKeyGeneration.AutomaticKeyGenerationType.SEQUENCE_TABLE,
                        cacheSize = "10")
@CreateDefaultDbmsTables(value = "Disabled")
@Entity(maxBeansInCache = "1000",
        dataSourceName = "jdbc/MedRecTxDataSource",
        transTimeoutSeconds = "0",
        ejbName = "PrescriptionEJB",
        reentrant = Constants.Bool.FALSE,
        concurrencyStrategy = Constants.ConcurrencyStrategy.DATABASE,
        delayDatabaseInsertUntil = Entity.DelayDatabaseInsertUntil.EJB_POST_CREATE,
        tableName = "prescription",
        readTimeoutSeconds = "600",
        primKeyClass = "java.lang.Integer",
        defaultTransaction = Constants.TransactionAttribute.MANDATORY,
        abstractSchemaName = "PrescriptionEJB")
@FileGeneration(localClass = Constants.Bool.TRUE,
                localHome = Constants.Bool.TRUE,
                valueClass = Constants.Bool.FALSE)
@Finders({
    @Finder(signature = "java.util.Collection findByPatientId(java.lang.Integer n0)",
            ejbQl = "SELECT OBJECT(o) FROM PrescriptionEJB AS o WHERE o.patientId = ?1 ORDERBY o.datePrescribed",
            groupName = "liteWeight")
})
@Relations({
    @Relation(cascadeDelete = Constants.Bool.FALSE,
              cmrField = "record",
              name = "Record-Prescriptions",
              roleName = "Prescriptions-Has-Record",
              multiplicity = Relation.Multiplicity.MANY,
              targetEjb = "RecordEJB",
              fkColumn = "record_id")
})
public abstract class PrescriptionEJB extends GenericEntityBean {
  // Container managed fields
  @CmpField(column = "id",
      orderingNumber = "1")
  @LocalMethod()
  @PrimkeyField()
  public abstract Integer getId();

  @LocalMethod()
  public abstract void setId(Integer id);

  @CmpField(column = "pat_id",
            orderingNumber = "2")
  @LocalMethod()
  public abstract Integer getPatientId();

  @LocalMethod()
  public abstract void setPatientId(Integer patientId);

  @CmpField(column = "record_id",
            orderingNumber = "4")
  @LocalMethod()
  public abstract Integer getRecordId();

  @LocalMethod()
  public abstract void setRecordId(Integer recordId);

  @CmpField(column = "date_prescribed",
            orderingNumber = "5",
            groupNames = "liteWeight, prescriptions-group")
  @LocalMethod()
  public abstract java.sql.Date getDatePrescribed();

  @LocalMethod()
  public abstract void setDatePrescribed(java.sql.Date datePrescribed);

  @CmpField(column = "drug",
            orderingNumber = "6",
            groupNames = "liteWeight, prescriptions-group")
  @LocalMethod()
  public abstract String getDrug();

  @LocalMethod()
  public abstract void setDrug(String drug);

  @CmpField(column = "dosage",
            orderingNumber = "7",
            groupNames = "liteWeight, prescriptions-group")
  @LocalMethod()
  public abstract String getDosage();

  @LocalMethod()
  public abstract void setDosage(String dosage);

  @CmpField(column = "frequency",
            orderingNumber = "8",
            groupNames = "liteWeight, prescriptions-group")
  @LocalMethod()
  public abstract String getFrequency();

  @LocalMethod()
  public abstract void setFrequency(String frequency);

  @CmpField(column = "refills_remaining",
            orderingNumber = "9",
            groupNames = "liteWeight, prescriptions-group")
  @LocalMethod()
  public abstract Integer getRefillsRemaining();

  @LocalMethod()
  public abstract void setRefillsRemaining(Integer refillsRemaining);

  @CmpField(column = "instructions",
            orderingNumber = "10",
            groupNames = "liteWeight, prescriptions-group")
  @LocalMethod()
  public abstract String getInstructions();

  @LocalMethod()
  public abstract void setInstructions(String instructions);


  // Container managed relation fields
  @CmrField(orderingNumber = "11")
  @LocalMethod()
  public abstract RecordLocal getRecord();

  @LocalMethod()
  public abstract void setRecord(RecordLocal record);

  /**
   * <p>Returns a value object representation of the bean.</p>
   *
   * @return Record
   */
  @LocalMethod()
  public Prescription getPrescription() {
    Prescription prescription = new Prescription();
    Calendar cal = MedRecUtils.convertSqlDate2Calendar(getDatePrescribed());
    prescription.setDatePrescribed(cal);
    prescription.setDrug(getDrug());
    prescription.setDosage(getDosage());
    prescription.setFrequency(getFrequency());
    prescription.setRefillsRemaining(getRefillsRemaining());
    prescription.setInstructions(getInstructions());
    return prescription;
  }

  /**
   * <p>Returns an abbreviated value object representation of the bean.
   * Fields returned are:<br>
   * id<br>
   * date<br>
   * symptoms<br>
   * diagnois</p>
   *
   * @return Prescription
   *         <p/>
   */
  @LocalMethod()
  public Prescription getPrescriptionLite() {
    Prescription prescription = new Prescription();
    prescription.setId(getId());
    Calendar cal = MedRecUtils.convertSqlDate2Calendar(getDatePrescribed());
    prescription.setDatePrescribed(cal);
    prescription.setDrug(getDrug());
    prescription.setFrequency(getFrequency());
    return prescription;
  }

  // Home methods
  /**
   * <p>Prescription create.</p>
   */
  public Object ejbCreate(Integer patientId,
                          Calendar datePrescribed,
                          String drug,
                          String dosage,
                          String frequency,
                          Integer refillsRemaining,
                          String instructions,
                          RecordLocal record) throws CreateException {
    setPatientId(patientId);
    Date sqlDate = MedRecUtils.getDate(datePrescribed);
    setDatePrescribed(sqlDate);
    setDrug(drug);
    setDosage(dosage);
    setFrequency(frequency);
    setRefillsRemaining(refillsRemaining);
    setInstructions(instructions);
    return null;
  }

  public void ejbPostCreate(Integer patientId,
                            Calendar datePrescribed,
                            String drug,
                            String dosage,
                            String frequency,
                            Integer refillsRemaining,
                            String instructions,
                            RecordLocal record) throws CreateException {
    setRecord(record);
  }

  /**
   * <p>Prescription create.</p>
   */
  public Object ejbCreate(Prescription prescription, RecordLocal record)
      throws CreateException {
    setPatientId(prescription.getPatientId());
    Date sqlDate = MedRecUtils.getDate(prescription.getDatePrescribed());
    setDatePrescribed(sqlDate);
    setDrug(prescription.getDrug());
    setDosage(prescription.getDosage());
    setFrequency(prescription.getFrequency());
    setRefillsRemaining(prescription.getRefillsRemaining());
    setInstructions(prescription.getInstructions());
    return null;
  }

  public void ejbPostCreate(Prescription prescription, RecordLocal record)
      throws CreateException {
    /* Delaying the database insert until after ejbPostCreate is required
      when a cmr-field is mapped to a foreign-key column that doesn't allow
      null values.  In this case, the cmr-field must be set to a non-null
      value in ejbPostCreate before the bean is inserted into the database.
      Note that cmr-fields may not be set during ejbCreate, before the
      primary key of the bean is known. */
    setRecord(record);
  }

  @LocalMethod()
  public String toString() {
    StringBuffer str = new StringBuffer();
    str.append("PRESCRIPTIONS [Id: "+getId());
    str.append(" | PatId: " +getPatientId());
    str.append(" | RecId: "+getRecordId());
    str.append(" | Calendar: "+getDatePrescribed());
    str.append(" | Drug: "+getDrug());
    str.append(" | Dosage: "+getDosage());
    str.append(" | Freq: "+getFrequency());
    str.append(" | Refills: "+getRefillsRemaining());
    str.append(" | Instructions: "+getInstructions());
    str.append("]");
    return str.toString();
  }
}
package com.bea.medrec.entities;

import com.bea.medrec.utils.JNDINames;
import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.utils.MedRecUtils;
import com.bea.medrec.utils.ServiceLocator;
import com.bea.medrec.value.Physician;
import com.bea.medrec.value.Prescription;
import com.bea.medrec.value.Record;
import com.bea.medrec.value.VitalSigns;
import java.sql.Date;
import java.util.*;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import weblogic.ejb.GenericEntityBean;
import weblogic.ejbgen.*;

/**
 * <p>RecordEJB is an Container Managed EntityBean that
 * manipulates record persisted data.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
@AutomaticKeyGeneration(name = "RECORD_SEQ",
                        type = AutomaticKeyGeneration.AutomaticKeyGenerationType.SEQUENCE_TABLE,
                        cacheSize = "10")
@CreateDefaultDbmsTables(value = "Disabled")
@EjbLocalRefs({
    @EjbLocalRef(name = "ejb/local/physician",
                 home = "com.bea.medrec.entities.PhysicianLocalHome",
                 local = "com.bea.medrec.entities.PhysicianLocal",
                 type = Constants.RefType.ENTITY,
                 link = "PhysicianEJB"),
    @EjbLocalRef(name = "ejb/local/prescription",
                 home = "com.bea.medrec.entities.PrescriptionLocalHome",
                 local = "com.bea.medrec.entities.PrescriptionLocal",
                 type = Constants.RefType.ENTITY,
                 link = "PrescriptionEJB"),
    @EjbLocalRef(name = "ejb/local/vitalsigns",
                 home = "com.bea.medrec.entities.VitalSignsLocalHome",
                 local = "com.bea.medrec.entities.VitalSignsLocal",
                 type = Constants.RefType.ENTITY,
                 link = "VitalSignsEJB")
})
@Entity(maxBeansInCache = "1000",
        dataSourceName = "jdbc/MedRecTxDataSource",
        transTimeoutSeconds = "0",
        ejbName = "RecordEJB",
        reentrant = Constants.Bool.FALSE,
        concurrencyStrategy = Constants.ConcurrencyStrategy.DATABASE,
        delayDatabaseInsertUntil = Entity.DelayDatabaseInsertUntil.EJB_POST_CREATE,
        tableName = "record",
        readTimeoutSeconds = "600",
        primKeyClass = "java.lang.Integer",
        defaultTransaction = Constants.TransactionAttribute.MANDATORY,
        abstractSchemaName = "RecordEJB",
        databaseType = Entity.DatabaseType.POINTBASE)
@FileGeneration(localClass = Constants.Bool.TRUE,
                localHome = Constants.Bool.TRUE,
                valueClass = Constants.Bool.FALSE)
@Finders({
    @Finder(signature = "com.bea.medrec.entities.RecordLocal findByRecordId(java.lang.Integer n0)",
            ejbQl = "not used in favor of weblogic-ejb-ql for relationship caching",
            weblogicEjbQl = "SELECT OBJECT(o) FROM RecordEJB AS o WHERE o.id = ?1",
            cachingName = "recordCache"),
    @Finder(signature = "java.util.Collection findByPatientId(java.lang.Integer n0)",
            ejbQl = "not used in favor of weblogic-ejb-ql for relationship caching",
            weblogicEjbQl = "SELECT OBJECT(o) FROM RecordEJB AS o WHERE o.patientId = ?1 ORDERBY o.date",
            groupName = "liteWeight",
            cachingName =  "recordSummaryCache")
})
@Relations({
    @Relation(cascadeDelete = Constants.Bool.FALSE,
              cmrField = "physician",
              name = "Record-Physician",
              roleName = "Record-Has-Physician",
              multiplicity = Relation.Multiplicity.MANY,
              targetEjb = "PhysicianEJB",
              fkColumn = "phys_id"),
    @Relation(cascadeDelete = Constants.Bool.FALSE,
              cmrField = "prescriptions",
              name = "Record-Prescriptions",
              roleName = "Record-Has-Prescriptions",
              multiplicity = Relation.Multiplicity.ONE,
              targetEjb = "PrescriptionEJB"),
    @Relation(cascadeDelete = Constants.Bool.TRUE,
              cmrField = "vitalSigns",
              name = "Record-VitalSigns",
              roleName = "Record-Has-VitalSigns",
              multiplicity = Relation.Multiplicity.ONE,
              targetEjb = "VitalSignsEJB",
              fkColumn = "vital_id")
})
@RelationshipCachingElements({
    @RelationshipCachingElement(cachingName = "recordSummaryCache",
                                cmrField = "physician",
                                groupName = "physician-group"),
    @RelationshipCachingElement(cachingName = "recordCache",
                                cmrField = "vitalSigns",
                                groupName = "vitalSigns-group"),
    @RelationshipCachingElement(cachingName = "recordCache",
                                cmrField = "prescriptions",
                                groupName = "prescriptions-group")
})
public abstract class RecordEJB extends GenericEntityBean {
    private static Logger logger = MedRecLog4jFactory.getLogger(
        RecordEJB.class.getName());

  // Attributes
  private EntityContext ctx;
  private VitalSignsLocalHome vitalsHome;
  private PhysicianLocalHome physicianHome;

  // Container managed fields

  @CmpField(column = "id",
            orderingNumber = "1")
  @LocalMethod()
  @PrimkeyField()
  public abstract Integer getId();

  @LocalMethod()
  public abstract void setId(Integer id);

  @CmpField(column = "pat_id",
            orderingNumber = "2",
            groupNames = "liteWeight")
  @LocalMethod()
  public abstract Integer getPatientId();

  @LocalMethod()
  public abstract void setPatientId(Integer patientId);

  @CmpField(orderingNumber = "3",
            column = "phys_id")
  @LocalMethod()
  public abstract Integer getPhysicianId();

  @LocalMethod()
  public abstract void setPhysicianId(Integer physicianId);

  @CmpField(column = "record_date",
            orderingNumber = "4",
            groupNames = "liteWeight")
  @LocalMethod()
  public abstract Date getDate();

  @LocalMethod()
  public abstract void setDate(Date date);

  @CmpField(column = "symptoms",
            orderingNumber = "5",
            groupNames = "liteWeight")
  @LocalMethod()
  public abstract String getSymptoms();

  @LocalMethod()
  public abstract void setSymptoms(String symptons);

  @CmpField(orderingNumber = "6",
            column = "diagnosis")
  @LocalMethod()
  public abstract String getDiagnosis();

  @LocalMethod()
  public abstract void setDiagnosis(String diagnosis);

  @CmpField(orderingNumber = "7",
            column = "notes")
  @LocalMethod()
  public abstract String getNotes();

  @LocalMethod()
  public abstract void setNotes(String notes);

  @CmpField(orderingNumber = "8",
            column = "vital_id")
  @LocalMethod()
  public abstract Integer getVitalSignsId();

  @LocalMethod()
  public abstract void setVitalSignsId(Integer vitalSignsId);

  // Container managed relation fields
  @CmrField(orderingNumber = "10",
            groupNames = "liteWeight")
  @LocalMethod()
  public abstract PhysicianLocal getPhysician();

  @LocalMethod()
  public abstract void setPhysician(PhysicianLocal physician);

  @CmrField(orderingNumber = "11")
  @LocalMethod()
  public abstract VitalSignsLocal getVitalSigns();

  @LocalMethod()
  public abstract void setVitalSigns(VitalSignsLocal vitalSigns);

  @CmrField(orderingNumber = "12")
  @LocalMethod()
  public abstract Collection getPrescriptions();

  @LocalMethod()
  public abstract void setPrescriptions(Collection prescriptions);

     // Lifecycle method
  /**
  * <p>Sets the entity context.  Get handles for all
  * required entity beans.</p>
  *
  * @param ctx  EntityContext for entity
  */
  public void setEntityContext(EntityContext ctx) {
    this.ctx = ctx;
    try {
      // Entity bean homes.
      vitalsHome = getVitalSignsLocalHome();
      physicianHome = getPhysicianLocalHome();
    } catch (NamingException ne) {
      throw new EJBException(ne);
    }
  }

  // Home methods
  /**
   * <p>Record create.</p>
   */
  public Object ejbCreate() throws CreateException {
    return null;
  }

  public void ejbPostCreate() throws CreateException {
    /* not implemented */
  }

  /**
   * <p>Record create.</p>
   */
  public Object ejbCreate(Record record)
      throws NamingException, CreateException {
    logger.debug("Creating record: "+record.toString());
    setPatientId(record.getPatientId());
    Date sqlDate = MedRecUtils.getDate(record.getDate());
    setDate(sqlDate);
    setSymptoms(record.getSymptoms());
    setDiagnosis(record.getDiagnosis());
    setNotes(record.getNotes());
    return null;
  }

  /**
   * <p>Record create.</p>
   */
  public void ejbPostCreate(Record record)
      throws NamingException, CreateException {
    logger.debug("Record post creating.");
    // Vital Signs relationship
    VitalSignsLocal vitalSigns = vitalsHome.create(record.getVitalSigns());
    setVitalSigns(vitalSigns);
    // Physician relationship
    PhysicianLocal phyicianLocal = getPhysicianEntity(record.getPhysician());
    logger.debug("Just before cmr field set. " +
        phyicianLocal.getPhysician().toString());
    setPhysician(phyicianLocal);
  }

  /**
   * <p>Record create.</p>
   */
  public Object ejbCreate(Integer patientId,
                          Calendar date,
                          String symptoms,
                          String diagnosis,
                          String notes,
                          Physician physician,
                          VitalSigns vitalSigns)
      throws NamingException, CreateException {
    setPatientId(patientId);
    java.sql.Date sqlDate = MedRecUtils.getDate(date);
    setDate(sqlDate);
    setSymptoms(symptoms);
    setDiagnosis(diagnosis);
    setNotes(notes);
    return null;
  }

  public void ejbPostCreate(Integer patientId,
                            Calendar date,
                            String symptoms,
                            String diagnosis,
                            String notes,
                            Physician physician,
                            VitalSigns vitalSigns)
      throws NamingException, CreateException {
    VitalSignsLocal vitalsLocal = vitalsHome.create(vitalSigns.getTemperature(),
        vitalSigns.getBloodPressure(), vitalSigns.getPulse(),
        vitalSigns.getWeight(), vitalSigns.getHeight());
    setVitalSigns(vitalsLocal);
    PhysicianLocal phyicianLocal = getPhysicianEntity(physician);
    setPhysician(phyicianLocal);
  }

  /**
  * Returns handle to Physician entity.
  */
  private PhysicianLocal getPhysicianEntity(Physician physician)
      throws NamingException, CreateException {
    logger.debug("Getting physician entity.");
    PhysicianLocal physicanLocal = null;
    try {
      logger.debug("Finding physician name "+physician.getFirstName()+" "+
          physician.getLastName());
      physicanLocal = physicianHome.findByFirstLastName(
          physician.getFirstName(), physician.getLastName());
    } catch (FinderException fe) {
      logger.debug("Physician not found.  Will create new physician.");
      physicanLocal = physicianHome.create(physician);
    }
    return physicanLocal;
  }

    // Remote methods
  /**
  * Returns serializable List of Prescriptions.
  */
  private Prescription[] toPrescriptionVOArray(Collection pPrescriptions) {
    // Declare local variables.
    Prescription[] prescriptionVOs = null;
    if (pPrescriptions != null) {
      prescriptionVOs = new Prescription[pPrescriptions.size()];
      Iterator itr = pPrescriptions.iterator();
      int i = 0;
      while (itr.hasNext()) {
        PrescriptionLocal prescriptionLocal = (PrescriptionLocal) itr.next();
        prescriptionVOs[i] = prescriptionLocal.getPrescription();
        i++;
      }
    }
    return prescriptionVOs;
  }

  /**
 * <p>Returns a value object representation of the bean.</p>
 *
 * @return Record
 */
  @LocalMethod()
  public Record getRecord() {
    Record record = new Record();
    record.setId(getId());
    record.setPatientId(getPatientId());
    record.setPhysician(getPhysicianLite());
    Calendar cal = MedRecUtils.convertSqlDate2Calendar(getDate());
    record.setDate(cal);
    record.setSymptoms(getSymptoms());
    record.setDiagnosis(getDiagnosis());
    record.setNotes(getNotes());
    record.setVitalSigns(getVitalSigns().getVitalSigns());
    record.setPrescriptions(toPrescriptionVOArray(getPrescriptions()));
    logger.debug(record.toString());
    return record;
  }

  /**
 * <p>Returns an abbreviated value object representation of the bean.
 *    Fields returned are:<br>
 *      id<br>
 *      date<br>
 *      symptoms<br>
 *      diagnois</p>
 *
 * @return Record Lite Record value object
 */
  @LocalMethod()
  public Record getRecordLite() {
    Record record = new Record();
    record.setId(getId());
    record.setPhysician(getPhysicianLite());
    Calendar cal = MedRecUtils.convertSqlDate2Calendar(getDate());
    record.setDate(cal);
    record.setSymptoms(getSymptoms());
    record.setDiagnosis(getDiagnosis());
    return record;
  }

  // Utility methods
  private Physician getPhysicianLite() {
    return new Physician(getPhysician().getFirstName(),
        getPhysician().getMiddleName(), getPhysician().getLastName());
  }

  /**
   * Get Vital Signs Entity EJB local home
   */
  public VitalSignsLocalHome getVitalSignsLocalHome() throws NamingException {
    return (VitalSignsLocalHome)lookUpLocalHome(JNDINames.VITALSIGNS_LOCAL_HOME,
        VitalSignsLocalHome.class);
  }

  /**
  * Get Physician Entity EJB local home
  */
  public PhysicianLocalHome getPhysicianLocalHome() throws NamingException {
    return (PhysicianLocalHome)lookUpLocalHome(JNDINames.PHYSICIAN_LOCAL_HOME,
        PhysicianLocalHome.class);
  }

  /**
  * Get local home
  */
  public Object lookUpLocalHome(String pHome, Class pClazz)
      throws NamingException {
    ServiceLocator locator = ServiceLocator.getInstance();
    Object obj = locator.lookupLocalHome(pHome, pClazz);
    return obj;
  }

  @LocalMethod()
  public String toString() {
      StringBuffer str = new StringBuffer();
      str.append("RECORD [Id: "+getId());
      str.append(" | PatId: "+getPatientId());
      str.append(" | DOB: "+getDate());
      str.append(" | Syms: "+getSymptoms());
      str.append(" | Diag: "+getDiagnosis());
      str.append(" | Notes: "+getNotes());
      str.append("\n" +
        (getPhysician() == null ? "PHYSICIAN: [null]" : getPhysician().toString()));
      str.append("\n" +
        (getVitalSigns() == null ? "VITALS: [null]" : getVitalSigns().toString()));
      str.append("\n"+prescriptionsToString());
      str.append("]");
      return str.toString();
  }

  private String prescriptionsToString() {
    StringBuffer str = new StringBuffer();
    if (getPrescriptions() != null && getPrescriptions().size() > 0) {
      str.append(" Num of prescriptions: " +
          getPrescriptions().size() +
          " |");
      Iterator itr = getPrescriptions().iterator();
      while (itr.hasNext()) {
        Object obj = (Object) itr.next();
        str.append("\n" +
            obj.toString());
      }
    } else {
      str.append("PRESCRIPTIONS: [null]");
    }
    return str.toString();
  }
}
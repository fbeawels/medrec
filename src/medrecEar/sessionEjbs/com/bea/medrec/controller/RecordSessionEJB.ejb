package com.bea.medrec.controller;

import com.bea.medrec.entities.*;
import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.value.Physician;
import com.bea.medrec.value.Prescription;
import com.bea.medrec.value.Record;
import com.bea.medrec.value.RecordsSummary;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.SessionContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import weblogic.ejb.GenericSessionBean;
import weblogic.ejbgen.*;

/**
 * <p>Session facade for all MedRec medical record functionality.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
@EjbLocalRefs({
    @EjbLocalRef(name = "ejb/local/prescription",
                 home = "com.bea.medrec.entities.PrescriptionLocalHome",
                 local = "com.bea.medrec.entities.PrescriptionLocal",
                 type = Constants.RefType.ENTITY,
                 link = "PrescriptionEJB"),
    @EjbLocalRef(name = "ejb/local/record",
                 home = "com.bea.medrec.entities.RecordLocalHome",
                 local = "com.bea.medrec.entities.RecordLocal",
                 type = Constants.RefType.ENTITY,
                 link = "RecordEJB"),
    @EjbLocalRef(name = "ejb/local/physician",
                 home = "com.bea.medrec.entities.PhysicianLocalHome",
                 local = "com.bea.medrec.entities.PhysicianLocal",
                 type = Constants.RefType.ENTITY,
                 link = "PhysicianEJB")
})
@FileGeneration(remoteClass = Constants.Bool.TRUE,
                remoteHome = Constants.Bool.TRUE)
@JndiName(remote = "RecordSessionEJB.RecordSessionHome")
@Session(maxBeansInFreePool = "1000",
         initialBeansInFreePool = "0",
         transTimeoutSeconds = "0",
         type = Session.SessionType.STATELESS,
         defaultTransaction = Constants.TransactionAttribute.REQUIRED,
         enableCallByReference = Constants.Bool.TRUE,
         ejbName = "RecordSessionEJB")

public class RecordSessionEJB extends GenericSessionBean {
  private static Logger logger =
    MedRecLog4jFactory.getLogger(RecordSessionEJB.class.getName());
  private SessionContext ctx;
  private RecordLocalHome recordHome;
  private PrescriptionLocalHome prescriptionHome;
  private PhysicianLocalHome physicianHome;

  /**
 * <p>Sets the session context.  Get handles for all
 * session and entity and JMS connections used throughout
 * this session bean.</p>
 *
 * @param ctx SessionContext Context for session
 */
  public void setSessionContext(SessionContext ctx) {
    this.ctx = ctx;
    try {
      // Entity bean homes.
      recordHome = JNDILookupUtils.getRecordLocalHome();
      prescriptionHome = JNDILookupUtils.getPrescriptionLocalHome();
      physicianHome = JNDILookupUtils.getPhysicianLocalHome();
    } catch (NamingException ne) {
      throw new EJBException(ne);
    }
  }

  // Public Methods

  //  A D D   R E C O R D
  /**
 * <p>Adds a record, including vital signs and prescriptions, by
 * calling the appropriate entity beans.</p>
 *
 * @param pRecordVO Complete record value object to be added
 * @return Record
 * @throws RemoteException
 */
  @RemoteMethod()
  public Record addRecord(Record pRecordVO)
      throws NamingException, RemoteException, Exception {
    logger.debug("Adding record: "+pRecordVO.toString());

    // Declare local variables.
    RecordLocal recordLocal = null;
    Collection prescriptions = null;
    try {
      recordLocal = recordHome.create(pRecordVO);
      if (pRecordVO.getNumOfPrescriptions() > 0) {
        prescriptions = addPrescriptions(pRecordVO.getPrescriptions(),
            recordLocal);
        recordLocal.setPrescriptions(prescriptions);
      }
    } catch (CreateException ce) {
      logger.error(ce);
      throw new EJBException(ce);
    }
    return recordLocal.getRecord();
  }

  /**
 * <p>Add prescriptions and increment a counter property in
 * RecordSessionEJBMBean.</p>
 *
 * @param pPrescriptionVOs Array of Prescription value objects
 * @param recordLocal Record entity
 * @return Collection
 * @throws CreateException
 * @throws Exception
 */
  private Collection<Object> addPrescriptions(Prescription[] pPrescriptionVOs,
                                      RecordLocal recordLocal)
      throws CreateException, Exception {
    // Declare local variables.
    Collection<Object> prescriptions = new ArrayList<Object>();
    if (pPrescriptionVOs != null) {
      logger.debug("Number of prescriptions: "+pPrescriptionVOs.length);
      com.bea.medrec.controller.RecordSessionEJBMBeanImpl.incrementTotalRx();
      for (int i = 0; i < pPrescriptionVOs.length; i++) {
        Prescription prescriptionVO = pPrescriptionVOs[i];
        prescriptions.add(prescriptionHome.create(prescriptionVO, recordLocal));
      }
    }
    return prescriptions;
  }

  //  G E T   P H Y S I C I A N
  /**
 * <p>Retrieves a patient.</p>
 *
 * @param pPhysicianVO Physician value object with first name and last name.
 * @return Physician  Found physician value oject.
 * @throws NamingException
 * @throws NamingException
 * @throws CreateException
 */
  @RemoteMethod()
  public Physician getPhysician(Physician pPhysicianVO)
      throws NamingException, CreateException {
    logger.debug("Getting physician entity.");
    PhysicianLocal physicanLocal = null;
    try {
      physicanLocal = physicianHome.findByFirstLastName(
          pPhysicianVO.getFirstName(), pPhysicianVO.getLastName());
      logger.debug("Found physician id "+physicanLocal.getId());
    } catch (FinderException fe) {
      logger.debug("Physician not found.  Will create new physician");
      physicanLocal = physicianHome.create(pPhysicianVO.getFirstName(),
          pPhysicianVO.getMiddleName(), pPhysicianVO.getLastName());
      logger.debug("Created physician id "+physicanLocal.getId());
    }
    return physicanLocal.getPhysician();
  }

  //  G E T   P R E S C R I P T I O N S
  /**
 * <p>Retrieves all prescriptions for a patient.</p>
 *
 * @param pPatientId Id of the prescription.
 * @return Collection  Collection of prescription value objects.
 * @throws NamingException
 * @throws RemoteException
 * @throws Exception
 */
  @RemoteMethod()
  public Prescription[] getPrescriptions(Integer pPatientId)
      throws NamingException, RemoteException, Exception {
    // Declare local variables.
    Collection prescriptions = null;
    try {
      // get all prescriptions for a patient
      prescriptions = prescriptionHome.findByPatientId(pPatientId);
    } catch (FinderException fe) {
      logger.warn(fe.getClass().getName()+" - "+fe.getMessage());
    }
    logger.debug("Num of Prescriptions: "+
        (prescriptions != null ? String.valueOf(prescriptions.size()) : "0"));

    // convert collection of prescription entities to
    // prescription vo array and return array
    return toPrescriptionVOArray(prescriptions);
  }

  private Prescription[] toPrescriptionVOArray(Collection pPrescriptionLocals) {
    // declare local variables
    Prescription[] prescriptionVOs = null;

    // convert prescriptions to prescription vo array
    if (pPrescriptionLocals != null && pPrescriptionLocals.size() > 0) {
      prescriptionVOs = new Prescription[pPrescriptionLocals.size()];
      int i = 0;
      for (Iterator iterator = pPrescriptionLocals.iterator();
           iterator.hasNext(); i++) {
        PrescriptionLocal prescriptionLocal =
            (PrescriptionLocal) iterator.next();
        prescriptionVOs[i] = prescriptionLocal.getPrescription();
      }
    } else {
      prescriptionVOs = new Prescription[0];
    }
    return prescriptionVOs;
  }

  //  G E T   A L L   R E C O R D S   F O R   A   P A T I E N T
  /**
 * <p>Retrieves a records for a patient.  Records return are lite weight.</p>
 *
 * @param pPatientId Id of the patient.
 * @return Collection  Collection of lite record value objects.
 * @throws NamingException
 * @throws RemoteException
 * @throws Exception
 */
  @RemoteMethod()
  public Record[] getRecords(Integer pPatientId)
      throws NamingException, RemoteException, Exception {
    // Declare local variables.
    Collection records = null;
    try {
      records = recordHome.findByPatientId(pPatientId);
    } catch (FinderException fe) {
      logger.warn(fe.getClass().getName()+" - "+fe.getMessage());
    }
    logger.debug("Num of records: " +
        (records != null ? String.valueOf(records.size()) : "0"));

    // convert collection of record entities to record vo array and return array
    return toRecordVOArray(records);
  }

  private Record[] toRecordVOArray(Collection pRecordLocals) {
    // Declare local variables.
    Record[] recordVOs = null;

    // convert collection of record entities to record vo array
    if (pRecordLocals != null && pRecordLocals.size() > 0) {
      recordVOs = new Record[pRecordLocals.size()];
      int i = 0;
      for (Iterator iterator = pRecordLocals.iterator(); iterator.hasNext();
           i++) {
        RecordLocal recordLocal = (RecordLocal) iterator.next();
        logger.debug("Record retrieved: " +
            recordLocal.toString());
        recordVOs[i] = recordLocal.getRecordLite();
      }
    } else {
      recordVOs = new Record[0];
    }
    return recordVOs;
  }

  //  G E T   A   R E C O R D
  /**
 * <p>Retrieves all information for a visit given a record id.
 * This includes vital signs, the record, and any prescriptions.</p>
 *
 * @param pRecordId Id of a record.
 * @return Record  Record value object.
 * @throws NamingException
 * @throws RemoteException
 * @throws Exception
 */
  @RemoteMethod()
  public Record getRecord(Integer pRecordId)
      throws NamingException, RemoteException, Exception {
    logger.debug("Getting record: "+pRecordId);

    // Declare local variables.
    Record recordVO = null;
    RecordLocal recordLocal = null;
    try {
      recordLocal = recordHome.findByRecordId(pRecordId);
      recordVO = recordLocal.getRecord();
      logger.debug(recordVO.toString());
    } catch (FinderException fe) {
      logger.warn(fe.getClass().getName()+" - "+fe.getMessage());
    }
    return recordVO;
  }

  //  G E T   R E C O R D S   S U M M A R Y
  /**
 * <p>Retrieves all medical record summary.
 * This includes a collection of abbreviated records, and
 * a collection of current and recent prescriptions.</p>
 *
 * @param pPatientId Id of the patient.
 * @return RecordsSummary
 * @throws NamingException
 * @throws RemoteException
 * @throws Exception
 */
  @RemoteMethod()
  public RecordsSummary getRecordsSummary(Integer pPatientId)
      throws NamingException, RemoteException, Exception {
    logger.debug("Patient id: "+pPatientId.toString());
    RecordsSummary recSummaryVO = new RecordsSummary();
    recSummaryVO.setRecords(getRecords(pPatientId));
    recSummaryVO.setPrescriptions(getPrescriptions(pPatientId));
    return recSummaryVO;
  }
}
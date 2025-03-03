package com.bea.medrec.controller;

import com.bea.medrec.entities.*;
import com.bea.medrec.exceptions.DuplicateAccountException;
import com.bea.medrec.exceptions.MedRecException;
import com.bea.medrec.utils.ErrorConstants;
import com.bea.medrec.utils.MedRecConstants;
import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.utils.MedRecUtils;
import com.bea.medrec.value.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.ejb.*;
import javax.jms.*;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import weblogic.ejb.GenericSessionBean;
import weblogic.ejbgen.*;
import weblogic.ejbgen.Session;

/**
 * <p>Session facade for all MedRec adminstrator functionality.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
@EjbLocalRefs({
    @EjbLocalRef(name = "ejb/local/group",
                 home = "com.bea.medrec.entities.GroupLocalHome",
                 local = "com.bea.medrec.entities.GroupLocal",
                 type = Constants.RefType.ENTITY,
                 link = "GroupEJB"),
    @EjbLocalRef(name = "ejb/local/patient",
                 home = "com.bea.medrec.entities.PatientLocalHome",
                 local = "com.bea.medrec.entities.PatientLocal",
                 type = Constants.RefType.ENTITY,
                 link = "PatientEJB"),
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
    @EjbLocalRef(name = "ejb/local/user",
                 home = "com.bea.medrec.entities.UserLocalHome",
                 local = "com.bea.medrec.entities.UserLocal",
                 type = Constants.RefType.ENTITY,
                 link = "UserEJB")
})
@FileGeneration(remoteClass = Constants.Bool.TRUE,
                remoteHome = Constants.Bool.TRUE)
@JndiName(remote = "AdminSessionEJB.AdminSessionHome")
@ResourceRefs({
    @ResourceRef(name = "jms/MedRecQueueConnectionFactory",
                 type = "javax.jms.QueueConnectionFactory",
                 auth = ResourceRef.Auth.APPLICATION,
                 sharingScope = ResourceRef.SharingScope.SHAREABLE,
                 jndiName = "jms/MedRecQueueConnectionFactory")
})
@ResourceEnvRefs({
    @ResourceEnvRef(name = "jms/REGISTRATION_MDB_QUEUE",
                    type = "javax.jms.Queue",
                    jndiName = "jms/REGISTRATION_MDB_QUEUE"),
    @ResourceEnvRef(name = "jms/XML_UPLOAD_MDB_QUEUE",
                    type = "javax.jms.Queue",
                    jndiName = "jms/XML_UPLOAD_MDB_QUEUE")
})
@Session(maxBeansInFreePool = "1000",
         initialBeansInFreePool = "0",
         transTimeoutSeconds = "0",
         type = Session.SessionType.STATELESS,
         defaultTransaction = Constants.TransactionAttribute.REQUIRED,
         enableCallByReference = Constants.Bool.TRUE,
         ejbName = "AdminSessionEJB")

public class AdminSessionEJB extends GenericSessionBean {
  private static Logger logger =
    MedRecLog4jFactory.getLogger(AdminSessionEJB.class.getName());

// Member variables
  private SessionContext ctx;
  private GroupLocalHome groupHome;
  private MailSession mailSession;
  private PatientLocalHome patientHome;
  private PrescriptionLocalHome prescriptionHome;
  private RecordLocalHome recordHome;
  private UserLocalHome userHome;
  private QueueConnectionFactory qcFactory;
  private QueueSession qSession;
  private QueueConnection qConnection;
  private QueueSender qSender;
  private Queue queReg;
  private Queue queXml;

  /**
  * <p>Sets the session context.  Get handles for all
  * session and entity and JMS connections used throughout
  * this session bean.</p>
  *
  * @param ctx  SessionContext Context for session
  */
  public void setSessionContext(SessionContext ctx) {
    this.ctx = ctx;
    try {
      // Session bean homes.
      mailSession = JNDILookupUtils.getMailSession();
      // Entity bean homes.
      groupHome = JNDILookupUtils.getGroupLocalHome();
      patientHome = JNDILookupUtils.getPatientLocalHome();
      recordHome = JNDILookupUtils.getRecordLocalHome();
      prescriptionHome = JNDILookupUtils.getPrescriptionLocalHome();
      userHome = JNDILookupUtils.getUserLocalHome();
      // JMS connections and queues.
      queReg = JNDILookupUtils.getRegQueue();
      queXml = JNDILookupUtils.getXMLQueue();
      qcFactory = JNDILookupUtils.getQCFactory();
      qConnection = qcFactory.createQueueConnection();
      qSession = qConnection.createQueueSession(false, 0);
      qConnection.start();
    } catch (JMSException jmse) {
      throw new EJBException(jmse);
    } catch (NamingException ne) {
      throw new EJBException(ne);
    }
  }

    //  A C T I V A T E   A C C O U N T   S T A T U S
  /**
  * <p>Activates a patients account for general access. A email notification
  * is sent to the patients.</p>
  *
  * @param pUsername    Username of account to be approved.
  * @exception MedRecException
  * @exception Exception
  */
  @RemoteMethod()
  public void activateAccountStatus(String pUsername, Mail pMail)
      throws MedRecException, Exception {
    // Declare local variables.
    User user = null;

    // Update account status. Set status to active.
    updateAccountStatus(pUsername, MedRecConstants.USER_ACTIVE);

    // Set from email.
    pMail.setFrom(MedRecConstants.MEDREC_CUSTOMER_SUPPORT);

    // Get password to be displayed in email.
    user = getUserByUsername(pUsername);

    // Provide substituted message values.
    Object[] arguments = {pMail.getTo(), user.getPassword()};

    // Send mail object to be processed.
    mailSession.composeAndSendMail(pMail, arguments);
  }

    //  A D D   P A T I E N T
  /**
  * <p>Presists a new patient if patient does not already exist.</p>
  *
  * @param pPatient    Patient value objects.
  * @return Integer
  * @exception NamingException
  * @exception RemoteException
  * @exception Exception
  */
  public Integer addPatient(Patient pPatient, UserLocal userLocal)
      throws NamingException, RemoteException, Exception {
    logger.debug("Add Patient: " +
        pPatient.toString());

    // Declare local variables.
    Integer patId = null;
    PatientLocal patientLocal = null;
    try {
      // Check to see if user already exists.  Is so, return id.
      patientLocal = patientHome.findByEmail(pPatient.getEmail());
      patId = patientLocal.getId();
      logger.debug("Patient exists, id: " +
          patId);
    } catch (FinderException fe) {
      logger.debug("Patient not found. Creating new patient.");
      patientLocal = patientHome.create(pPatient.getFirstName(),
          pPatient.getMiddleName(), pPatient.getLastName(),
          pPatient.getDateOfBirth(), pPatient.getGender(),
          pPatient.getSsn(), pPatient.getPhone(), pPatient.getEmail(),
          pPatient.getAddress(), userLocal);
      patId = patientLocal.getId();
    }
    return patId;
  }

    //  A D D   R E C O R D
  /**
  * <p>Presists a new record.</p>
  *
  * @param pRecord    Patient value objects.
  * @return RecordLocal
  * @exception NamingException
  * @exception RemoteException
  * @exception Exception
  */
  public RecordLocal addRecord(Record pRecord)
      throws NamingException, RemoteException, Exception {
    logger.debug("Add Record: "+pRecord.toString());
    RecordLocal recordLocal = recordHome.create(pRecord.getPatientId(),
        pRecord.getDate(), pRecord.getSymptoms(), pRecord.getDiagnosis(),
        pRecord.getNotes(), pRecord.getPhysician(), pRecord.getVitalSigns());
    return recordLocal;
  }

    //  A D D   R X
  /**
* <p>Presists a new prescription.</p>
*
* @param pPrescription    Patient value objects.
* @param pRecord   Record to be inserted.
* @return Integer
* @exception NamingException
* @exception RemoteException
* @exception Exception
*/
  public Integer addPrescription(Prescription pPrescription,
                                 RecordLocal pRecord)
      throws NamingException, RemoteException, Exception {
    logger.debug("Add Prescription: "+pPrescription.toString());
    // Declare local variables.
    PrescriptionLocal prescriptionLocal = prescriptionHome.create(
        pPrescription.getPatientId(), pPrescription.getDatePrescribed(),
        pPrescription.getDrug(), pPrescription.getDosage(),
        pPrescription.getFrequency(), pPrescription.getRefillsRemaining(),
        pPrescription.getInstructions(), pRecord);
    return prescriptionLocal.getId();
  }

    //  A D D   U S E R
  /**
  * <p>Creates new user account assign a temporary password.
  * Used by the XML upload feature where passwords are not supplied.</p>
  *
  * @param pUser    Patient value objects.
  *
  * @exception NamingException
  * @exception RemoteException
  * @exception DuplicateAccountException
  * @exception Exception
  */
  public UserLocal addUser(User pUser)
      throws NamingException, RemoteException, DuplicateAccountException,
      Exception {
    logger.debug("Add: "+pUser.toString());
    // Declare local variables
    UserLocal userLocal = null;
    try {
    // Check to see if user already exists.  Is so, return id.
      userLocal = userHome.findByUsername(pUser.getUsername());
      logger.warn("User " +
          pUser.getUsername() +
          " already exists");
      throw new DuplicateAccountException("User " +
          pUser.getUsername() +
          " already exists");
    } catch (FinderException fe) {
      logger.debug("User not found. Creating new user account.");
      if (pUser.getPassword() == null)
        pUser.setPassword(MedRecConstants.TEMP_PASSWORD);
      pUser.setStatus(MedRecConstants.USER_NEW);
      userLocal = userHome.create(pUser);
      return userLocal;
    }
  }

    //  A S S I G N   G R O U P S
  /**
  * <p>Assigns given groups for a given user.</p>
  *
  * @param pUsername  Username
  * @param pGroupNames  Group name
  * @exception NamingException
  * @exception RemoteException
  * @exception Exception
  */
  @RemoteMethod()
  public void assignGroups(String pUsername, String[] pGroupNames)
      throws NamingException, RemoteException, Exception {
    logger.debug("Assigning groups to user, "+pUsername);
    for (int i = 0; i < pGroupNames.length; i++) {
      try {
        groupHome.create(pGroupNames[i], pUsername);
      } catch (DuplicateKeyException dupe) {
        logger.debug("User "+pUsername+" already assign to group "+
            pGroupNames[i]+".");
      }
    }
  }

   //  A S S I G N   P A T I E N T   G R O U P S
  /**
  * <p>Assigns given groups for a given user.</p>
  *
  * @param pUsername  Username
  * @exception NamingException
  * @exception RemoteException
  * @exception Exception
  */
  @RemoteMethod()
  public void assignPatientGroups(String pUsername)
      throws NamingException, RemoteException, Exception {
    String[] groupNames = MedRecUtils.getGroupArray(
        MedRecConstants.PATIENT_GROUP_TYPE);
    assignGroups(pUsername, groupNames);
  }

  //  C R E A T E   P A T I E N T   &   L O G I N
  /**
  * <p>Create new patient account, login and groups,
  * and store revelent patient data.</p>
  *
  * @param pPatient    Patient value objects.
  * @exception RemoteException
  */
  @RemoteMethod()
  public Integer createPatientAccount(User pUser, Patient pPatient)
      throws NamingException, RemoteException, DuplicateAccountException,
      Exception {
    logger.info("Creating new patient account- login and patient data.");

    // Declare local variables.
    Integer patId = null;
    UserLocal userLocal = null;

    // Add patient to users.  Assign patient groups.
    userLocal = addUser(pUser);

    // Assign patient to groups.
    assignPatientGroups(userLocal.getUsername());

    // Add patient.
    patId = addPatient(pPatient, userLocal);
    return patId;
  }

    //  D E N Y   A C C O U N T   S T A T U S
  /**
  * <p>Deny new user registration.  Sets account status to "DENY".</p>
  *
  * @param pUsername    Username of account to be denied.
  * @exception MedRecException
  * @exception Exception
  */
  @RemoteMethod()
  public void denyAccountStatus(String pUsername, Mail pMail)
      throws MedRecException, Exception {
    updateAccountStatus(pUsername, MedRecConstants.USER_DENIED);
    pMail.setFrom(MedRecConstants.MEDREC_CUSTOMER_SUPPORT);
    mailSession.composeAndSendMail(pMail);
    // REVIEWME perhaps log user to approval list.
    // mail notification here
  }

    //  F I N D   A D M I N   B Y   S T A T U S
  /**
* <p>Finds patients by status "NEW".</p>
*
* @return Collection  Collection of patient value objects.
* @exception NamingException
* @exception RemoteException
* @exception Exception
*/
  @RemoteMethod()
  public List findNewUsers()
      throws NamingException, RemoteException, Exception {
    List<Patient> patCol = new ArrayList<Patient>();
    UserLocal userLocal = null;
    List<UserLocal> userList = (List<UserLocal>) userHome.findByStatus(
        MedRecConstants.USER_NEW);
    if (userList != null) {
      Iterator itr = userList.iterator();
      while (itr.hasNext()) {
        userLocal = (UserLocal) itr.next();
        patCol.add(userLocal.getPatientObj());
      }
    }
    return patCol;
  }

    //  F I N D   P A T I E N T   B Y   I D
  /**
  * <p>Find Patient by id.</p>
  *
  * @param pId   Patient Id
  * @return Patient  Patient value object.
  * @exception NamingException
  * @exception RemoteException
  * @exception Exception
  */
  @RemoteMethod()
  public Patient findPatientById(Integer pId)
      throws NamingException, RemoteException, Exception {
    // Declare local variables.
    PatientLocal patientLocal = null;
    Patient patient = null;
    try {
      patientLocal = patientHome.findByPrimaryKey(pId);
      patient = patientLocal.getPatient();
    } catch (FinderException fe) {
      logger.warn(fe.getClass().getName()+" - "+fe.getMessage());
    }
    return patient;
  }

      //  F I N D   P A T I E N T   B Y   E M A I L
  /**
  * <p>Find Patient by id.</p>
  *
  * @param pEmail   Patient email
  * @return Patient  Patient value object.
  * @exception NamingException
  * @exception RemoteException
  * @exception Exception
  */
  @RemoteMethod()
  public Patient findPatientByEmail(String pEmail)
      throws NamingException, RemoteException, Exception {
    // Declare local variables.
    PatientLocal patientLocal = null;
    Patient patient = null;
    try {
      patientLocal = patientHome.findByEmail(pEmail);
      patient = patientLocal.getPatient();
    } catch (FinderException fe) {
      logger.warn(fe.getClass().getName()+" - "+fe.getMessage());
    }
    return patient;
  }

      //  F I N D   U S E R
  /**
  * <p>Creates new user account assign a temporary password.
  * Used by the XML upload feature where passwords are not supplied.</p>
  *
  * @param pUsername    Patient value objects.
  * @return User
  * @exception NamingException
  * @exception RemoteException
  * @exception FinderException
  */
  @RemoteMethod()
  public User getUserByUsername(String pUsername)
      throws NamingException, RemoteException, FinderException {
    logger.debug("User: "+pUsername);

    // Declare local values
    UserLocal userLocal = null;

    // Find user entity
    userLocal = userHome.findByUsername(pUsername);
    return userLocal.getUser();
  }

    //   I N S E R T   M E D I C A L   R E C O R D
  /**
  * <p>Creates new patient account and stores associate records.
  * Used by the XML upload feature where medical institutions
  * pass all held record information for a particular patient.</p>
  *
  * @param pMedRecCol
  * @exception CreateException
  * @exception NamingException
  * @exception RemoteException
  * @exception Exception
  */
  @RemoteMethod()
  public void insertMedicalRecord(Collection pMedRecCol)
      throws CreateException, NamingException, MedRecException, Exception {
    logger.info("Insert medical record.");

    // Declare local variables.
    Patient patient = null;
    Integer physId = null;
    Integer patId = null;
    Collection<Record> recordCol = null;
    User user = null;
    Iterator itr = pMedRecCol.iterator();
    while (itr.hasNext()) {
      // Iterator thru medical collection.  Get physician id.
      MedicalRecord medicalRecord = (MedicalRecord) itr.next();

      // Get patient object.  Set status.
      // Then create patient and patient login data.
      patient = medicalRecord.getPatient();
      user = medicalRecord.getUser();
      try {
        patId = createPatientAccount(user, patient);
      } catch (DuplicateAccountException dupe) {
        logger.info("If user exists, continue processing.");
        patId = findPatientByEmail(patient.getEmail()).getId();
      }

      // Get collection of records to be inserted.
      recordCol = medicalRecord.getRecords();
      insertRecord(recordCol, patId);
    }
  }

    //   I N S E R T   R E C O R D
  /**
  * <p>Iterates through a collection storing records.</p>
  *
  * @param pMedRecCol
  * @param pPatId
  * @exception CreateException
  * @exception NamingException
  * @exception RemoteException
  * @exception Exception
  */
  @RemoteMethod()
  public void insertRecord(Collection pMedRecCol, Integer pPatId)
      throws CreateException, NamingException, MedRecException, Exception {
    logger.info("Insert record.");

    // Declare local variables.
    Record record = null;
    RecordLocal recordLocal = null;
    Iterator itr = pMedRecCol.iterator();
    while (itr.hasNext()) {
      // Iterator thru medical collection.  Get physician id.
      record = (Record) itr.next();
      record.setPatientId(pPatId);
      recordLocal = addRecord(record);
      insertPrescriptions(record.getPrescriptions(), recordLocal);
    }
  }

      //   I N S E R T   R E C O R D
  /**
  * <p>Iterates through a collection storing prescriptions.</p>
  *
  * @param pPrescriptionVOs Array of Prescription value objects
  * @param pRecordLocal Local Record interface
  * @exception CreateException
  * @exception NamingException
  * @exception RemoteException
  * @exception Exception
  */
  public void insertPrescriptions(Prescription[] pPrescriptionVOs,
                                  RecordLocal pRecordLocal)
      throws CreateException, NamingException, MedRecException, Exception {
    logger.info("Insert prescriptions.");
    if (pPrescriptionVOs != null && pPrescriptionVOs.length > 0) {
      for (int i = 0; i < pPrescriptionVOs.length; i++) {
        Prescription pPrescriptionVO = pPrescriptionVOs[i];
        pPrescriptionVO.setRecordId(pRecordLocal.getId());
        pPrescriptionVO.setPatientId(pRecordLocal.getPatientId());
        addPrescription(pPrescriptionVO, pRecordLocal);
      }
    }
  }

    //    P R O C E S S   R E G I S T R A T I O N
  /**
  * <p>Start registration process by placing registration object on queue.
  * The object is eventually picked up and acted upon.</p>
  *
  * @param pReg    Registration value objects.
  * @exception NamingException
  * @exception Exception
  */
  @RemoteMethod()
  public void processRegistration(Registration pReg) throws Exception {
    logger.debug("Sending registration to queue");
    qSender = qSession.createSender(queReg);
    sendToQueue(pReg, qSender);
  }

    //    P R O C E S S   R E G I S T R A T I O N
  /**
  * <p>Start XML file upload process by placing filename on queue.
  * The object is eventually picked up and acted upon.</p>
  *
  * @param filename    Filename of XML file to be uploaded.
  * @exception Exception
  */
  @RemoteMethod()
  public void processXMLUpload(String filename) throws Exception {
    logger.debug("Sending xml filename to queue");
    qSender = qSession.createSender(queXml);
    sendToQueue(filename, qSender);
  }

    //   S E N D   T O   Q U E U E
  /**
  * <p>Places new registration on registration
  * queue for further processing.</p>
  *
  * @param obj    Serializable object to be placed on que.
  * @param qSender
  * @exception Exception
  */
  @RemoteMethod()
  public void sendToQueue(java.io.Serializable obj, QueueSender qSender)
      throws Exception {
    logger.debug("sendToQueue: "+obj.toString());
    try {
      ObjectMessage message = qSession.createObjectMessage(obj);
      qSender.send(message);
      logger.debug("Finished sending mnessage to queue");
    } catch (Exception e) {
      logger.error(e.getLocalizedMessage(), e);
      throw e;
    }
  }

    //  U P D A T E   A C C O U N T   S T A T U S
  /**
  * <p>Update user account status to given status.</p>
  *
  * @param pUsername    Patient value objects.
  * @param pStatus    Patient value objects.
  * @exception MedRecException
  */
  @RemoteMethod()
  public void updateAccountStatus(String pUsername, String pStatus)
      throws MedRecException, Exception {
    logger.debug("Updating "+pUsername+" with account status "+pStatus);
    UserLocal userLocal = null;
    try {
      userLocal = userHome.findByUsername(pUsername);
      userLocal.setStatus(pStatus);
    } catch (FinderException fe) {
      logger.warn(ErrorConstants.USER_NOT_FOUND+" "+pUsername);
      throw new MedRecException(ErrorConstants.USER_NOT_FOUND+" "+pUsername);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw e;
    }
  }
}
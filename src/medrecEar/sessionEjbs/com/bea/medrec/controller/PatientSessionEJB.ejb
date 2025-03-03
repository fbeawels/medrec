package com.bea.medrec.controller;

import com.bea.medrec.entities.AddressLocal;
import com.bea.medrec.entities.PatientLocal;
import com.bea.medrec.entities.PatientLocalHome;
import com.bea.medrec.exceptions.DuplicateAccountException;
import com.bea.medrec.utils.JNDINames;
import com.bea.medrec.utils.MedRecConstants;
import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.utils.MedRecUtils;
import com.bea.medrec.value.Address;
import com.bea.medrec.value.Patient;
import com.bea.medrec.value.Registration;
import com.bea.medrec.value.User;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.SessionContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import weblogic.ejb.GenericSessionBean;
import weblogic.ejbgen.*;
import weblogic.jdbc.rowset.RowSetFactory;
import weblogic.jdbc.rowset.WLCachedRowSet;

/**
 * <p>Session facade for all MedRec patient functionality.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
@EjbLocalRefs({
    @EjbLocalRef(name = "ejb/local/address",
                 home = "com.bea.medrec.entities.AddressLocalHome",
                 local = "com.bea.medrec.entities.AddressLocal",
                 type = Constants.RefType.ENTITY,
                 link = "AddressEJB"),
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
    @EjbLocalRef(name = "ejb/local/user",
                 home = "com.bea.medrec.entities.UserLocalHome",
                 local = "com.bea.medrec.entities.UserLocal",
                 type = Constants.RefType.ENTITY,
                 link = "UserEJB")
})
@FileGeneration(remoteClass = Constants.Bool.TRUE,
                remoteHome = Constants.Bool.TRUE)
@JndiName(remote = "PatientSessionEJB.PatientSessionHome")
@ResourceRefs({
    @ResourceRef(name = "jms/MedRecQueueConnectionFactory",
                 type = "javax.jms.QueueConnectionFactory",
                 auth = ResourceRef.Auth.APPLICATION,
                 sharingScope = ResourceRef.SharingScope.SHAREABLE,
                 jndiName = "jms/MedRecQueueConnectionFactory"),
    @ResourceRef(name = "jdbc/MedRecTxDataSource",
                 type = "javax.sql.DataSource",
                 auth = ResourceRef.Auth.APPLICATION,
                 jndiName = "jdbc/MedRecTxDataSource")
})
@Session(maxBeansInFreePool = "1000",
         initialBeansInFreePool = "0",
         transTimeoutSeconds = "0",
         type = Session.SessionType.STATELESS,
         defaultTransaction = Constants.TransactionAttribute.REQUIRED,
         enableCallByReference = Constants.Bool.TRUE,
         ejbName = "PatientSessionEJB")

public class PatientSessionEJB extends GenericSessionBean {
  private static Logger logger =
    MedRecLog4jFactory.getLogger(PatientSessionEJB.class.getName());

  // Member variables
  private SessionContext ctx;
  private AdminSession adminSession;
  private PatientLocalHome patientHome;

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
      // Session bean homes.
      adminSession = JNDILookupUtils.getAdminSession();
      // Entity bean homes.
      patientHome = JNDILookupUtils.getPatientLocalHome();
    } catch (NamingException ne) {
      throw new EJBException(ne);
    }
  }

  //   P U B L I C   M E T H O D S

  //  F I N D   P A T I E N T   B Y   E M A I L
 /**
 * <p/>Finds MedRec patient by email.</p>
 *
 * @param pEmail Patient email
 * @return Patient  Patient value object.
 * @throws NamingException
 * @throws RemoteException
 * @throws Exception
 */
  @RemoteMethod()
  public Patient findPatientByEmail(String pEmail)
      throws NamingException, RemoteException, Exception {
    logger.debug("Email: "+pEmail);

    // Declare local variables.
    PatientLocal patientLocal = null;
    Patient patientVO = null;
    try {
      patientLocal = patientHome.findByEmail(pEmail);
      patientVO = patientLocal.getPatient();
      logger.debug(patientVO.toString());
    } catch (FinderException fe) {
      logger.warn(fe.getClass().getName()+" - "+fe.getMessage());
    }
    return patientVO;
  }

  //  F I N D   P A T I E N T   B Y   I D
  /**
 * <p>Find Patient by id.</p>
 *
 * @param pPatientId Patient Id
 * @return Patient  Patient value object.
 * @throws NamingException
 * @throws RemoteException
 * @throws Exception
 */
  @RemoteMethod()
  public Patient findPatientById(Integer pPatientId)
      throws NamingException, RemoteException, Exception {
  // Declare local variables.
    PatientLocal patientLocal = null;
    Patient patientVO = null;
    try {
      patientLocal = patientHome.findByPrimaryKey(pPatientId);
      patientVO = patientLocal.getPatient();
    } catch (FinderException fe) {
      logger.warn(fe.getClass().getName()+" - "+fe.getMessage());
    }
    return patientVO;
  }

    //  F I N D   P A T I E N T   B Y   L A S T N A M E   W I L D
  /**
 * <p/>
 * Find patient by last name.  Wild card search- %lastName%.
 * </p>
 *
 * @param pLastName Last name substring
 * @return Patient[] Array of Patient value objects.
 * @throws NamingException
 * @throws RemoteException
 * @throws Exception
 */
  @RemoteMethod()
  public Patient[] findPatientByLastNameWild(String pLastName)
      throws NamingException, RemoteException, Exception {
    logger.info("Finding patient by wildcard last name.");
    logger.debug("Patient last name: "+pLastName);

    // Declare local variables.
    List<Object> patientList = new ArrayList<Object>();
    String sql = "SELECT "+
        "p.id as patientId, "+
        "p.address_id , "+
        "p.first_name as firstName, "+
        "p.middle_name as middleName, "+
        "p.last_name as lastName, "+
        "p.dob as dateOfBirth, "+
        "p.ssn as ssn, "+
        "p.email as email, "+
        "p.gender as gender, "+
        "p.phone as phone, "+
        "a.id as addressId, "+
        "a.city as city, "+
        "a.country as country, "+
        "a.state as state, "+
        "a.street1 as streetName1, "+
        "a.street2 as streetName2, "+
        "a.zip as zipCode "+
        "FROM patient p LEFT OUTER JOIN address a ON p.address_id = a.id "+
        "WHERE ( ( UPPER( p.last_name ) LIKE UPPER( '%"+
        pLastName+
        "%' ) ) ) "+
        "ORDER BY p.last_name";
    try {
      logger.debug(sql);
      WLCachedRowSet rs = RowSetFactory.newInstance().newCachedRowSet();
      rs.setCommand(sql);
      rs.setDataSourceName(JNDINames.CATALOG_HOME+JNDINames.MEDREC_TX_DATASOURCE);
      rs.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
      rs.execute();
      rs.beforeFirst();
      while (rs.next()) {
        Address addrVO = new Address();
        addrVO.setId(rs.getInt("addressId"));
        addrVO.setStreetName1(rs.getString("streetName1"));
        addrVO.setStreetName2(rs.getString("streetName2"));
        addrVO.setCity(rs.getString("city"));
        addrVO.setState(rs.getString("state"));
        addrVO.setZipCode(rs.getString("zipCode"));
        addrVO.setCountry(rs.getString("country"));
        Patient patientVO = new Patient();
        patientVO.setId(rs.getInt("patientId"));
        patientVO.setFirstName(rs.getString("firstName"));
        patientVO.setMiddleName(rs.getString("middleName"));
        patientVO.setLastName(rs.getString("lastName"));
        patientVO.setGender(rs.getString("gender"));
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(rs.getDate("dateOfBirth"));
        patientVO.setDateOfBirth(cal);
        patientVO.setEmail(rs.getString("email"));
        patientVO.setPhone(rs.getString("phone"));
        patientVO.setSsn(rs.getString("ssn"));
        patientVO.setAddress(addrVO);
        patientList.add(patientVO);
      }
      logger.debug("Num of patients found: "+
          patientList.size());
    } catch (Exception ex) {
      logger.error(ex);
      throw new EJBException(ex);
    }
    return (Patient[]) patientList.toArray(new Patient[0]);
  }

  //  F I N D   P A T I E N T   B Y   S S N
  /**
  * <p/>
  * Finds MedRec patient by social security number.
  * </p>
  *
  * @param pSsn Patient SSN
  * @return Patient  Patient value object.
  * @throws NamingException
  * @throws RemoteException
  * @throws Exception
  */
  @RemoteMethod()
  public Patient findPatientBySsn(String pSsn)
      throws NamingException, RemoteException, Exception {
      logger.debug("Paient SSN: "+pSsn);

    // Declare local variables.
    PatientLocal patientLocal = null;
    Patient patientVO = null;
    try {
      patientLocal = patientHome.findBySsn(pSsn);
      patientVO = patientLocal.getPatient();
      logger.debug(patientVO.toString());
    } catch (FinderException fe) {
      logger.warn(fe.getClass().getName()+" - "+fe.getMessage());
    }
    return patientVO;
  }

  //  P R O C E S S   A C T I V E   R E G I S T R A T I O N
  /**
  * <p>Registers new patient account assigning status to ACTIVE.
  * This registration process is used to bypass administration
  * workflow which approves/denys new patient accounts.
  * <p/>
  * Typically this functionality is used by "affiliated" organizations
  * who register new patients thru MedRec's web services features.  These
  * organizations are trusted; therefore, no adminstration approval is needed.</p>
  *
  * @param pPatient Patient value objects.
  * @return Patient
  * @throws NamingException
  * @throws RemoteException
  * @throws Exception
  */
  @RemoteMethod()
  public Patient processActiveRegistration(Patient pPatient, String pPassword)
      throws NamingException, RemoteException, Exception {
    // Declare local variables.
    Registration regVO = null;
    User userVO = null;
    regVO = new Registration();
    regVO.setPatient(pPatient);
    userVO = new User();
    userVO.setUsername(pPatient.getEmail());
    userVO.setPassword(pPassword);
    userVO.setStatus(MedRecConstants.USER_ACTIVE);
    regVO.setUser(userVO);
    // Call process registration worker.
    return processRegistration(regVO);
  }

  //    P R O C E S S   N E W   R E G I S T R A T I O N
  /**
  * <p>Registers new patient account assign status to NEW.
  * Typically this functionality is used by online registrants that must
  * go through a MedRec adminstration approval process.</p>
  *
  * @param pReg Patient value objects.
  */
  @RemoteMethod()
      public void processNewRegistration(Registration pReg) {
    try {
      // Assign account status to NEW.
      pReg.getUser().setStatus(MedRecConstants.USER_NEW);
      processRegistration(pReg);
    } catch (DuplicateAccountException dupe) {
      logger.error("Unable to register the following user: \n"+
          pReg.toString()+" due to "+dupe.getMessage());
      // Send mail to notifying user of existing registration.
    } catch (Throwable th) {
      logger.error("Unable to register the following user: \n"+
          pReg.toString()+" due to "+th.getMessage());
      // Send registration to MedRec admintration for manual processing.
    }
  }

//   P R O C E S S   R E G I S T R A T I O N
  /**
  * <p>Main patient registration process method.  This method creates a
  * login account, assigns patient designated groups, and stores
  * pertinent patient data such as name, dob, ssn, etc.
  * This method acts as the worker to different types of registration.</p>
  *
  * @param registration Registration value objects.
  * @return Patient
  * @throws NamingException
  * @throws RemoteException
  * @throws Exception
  */
  @RemoteMethod()
  public Patient processRegistration(Registration registration) throws Exception {
    logger.debug("Registering Patient: "+
        registration.getPatient().toStringLite());

    // Declare local variables.
    User userVO = null;
    Patient patientVO = null;
    Integer patientId = null;

    // Get user and patient objects.
    userVO = registration.getUser();
    patientVO = registration.getPatient();

    // Create user, groups and patient information.
    patientId = adminSession.createPatientAccount(userVO, patientVO);
    logger.debug("Registration successful.");
    logger.debug("Retrieving new patient.");
    return findPatientById(patientId);
  }

//  U P D A T E   P A T I E N T
  /**
  * <p/>
  * Updates a patient by calling the appropriate entity beans.
  * </p>
  *
  * @param pPatient Patient value objects.
  * @return Patient
  * @throws NamingException
  * @throws RemoteException
  * @throws Exception
  */
  @RemoteMethod()
  public Patient updatePatient(Patient pPatient)
      throws NamingException, RemoteException, Exception {
      logger.debug("Updating Patient: "+pPatient.toString());

    // Declare local variables.
    PatientLocal selectedPatient = null;
    AddressLocal selectedAddress = null;
    Address addressVO = null;
    try {
      selectedPatient = patientHome.findByPrimaryKey(pPatient.getId());

      // Patient info
      selectedPatient.setFirstName(pPatient.getFirstName());
      selectedPatient.setMiddleName(pPatient.getMiddleName());
      selectedPatient.setLastName(pPatient.getLastName());
      Date date = MedRecUtils.getDate(pPatient.getDateOfBirth());
      selectedPatient.setDateOfBirth(date);
      selectedPatient.setGender(pPatient.getGender());
      selectedPatient.setSsn(pPatient.getSsn());
      selectedPatient.setPhone(pPatient.getPhone());

      // Address info
      selectedAddress = selectedPatient.getAddress();
      addressVO = pPatient.getAddress();
      selectedAddress.setStreetName1(addressVO.getStreetName1());
      selectedAddress.setStreetName2(addressVO.getStreetName2());
      selectedAddress.setCity(addressVO.getCity());
      selectedAddress.setState(addressVO.getState());
      selectedAddress.setZipCode(addressVO.getZipCode());
      selectedAddress.setCountry(addressVO.getCountry());
    } catch (Exception e) {
      logger.error(e);
      throw new EJBException(e);
    }
    return selectedPatient.getPatient();
  }
}
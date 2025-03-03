package com.bea.medrec.entities;

import com.bea.medrec.utils.JNDINames;
import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.utils.MedRecUtils;
import com.bea.medrec.utils.ServiceLocator;
import com.bea.medrec.value.Address;
import com.bea.medrec.value.Patient;
import java.sql.Date;
import java.util.Calendar;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import weblogic.ejb.GenericEntityBean;
import weblogic.ejbgen.*;

/**
 * <p>PatientEJB is an Container Managed EntityBean that
 * manipulates patient persisted data.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
@AutomaticKeyGeneration(name = "PATIENT_SEQ",
                        type = AutomaticKeyGeneration.AutomaticKeyGenerationType.SEQUENCE_TABLE,
                        cacheSize = "10")
@CreateDefaultDbmsTables(value = "Disabled")
@EjbLocalRefs({
    @EjbLocalRef(name = "ejb/local/address",
                 home = "com.bea.medrec.entities.AddressLocalHome",
                 local = "com.bea.medrec.entities.AddressLocal",
                 type = Constants.RefType.ENTITY,
                 link = "AddressEJB")
})
@Entity(maxBeansInCache = "1000",
        dataSourceName = "jdbc/MedRecTxDataSource",
        transTimeoutSeconds = "0",
        ejbName = "PatientEJB",
        reentrant = Constants.Bool.FALSE,
        concurrencyStrategy = Constants.ConcurrencyStrategy.DATABASE,
        delayDatabaseInsertUntil = Entity.DelayDatabaseInsertUntil.EJB_POST_CREATE,
        tableName = "patient",
        readTimeoutSeconds = "600",
        primKeyClass = "java.lang.Integer",
        defaultTransaction = Constants.TransactionAttribute.MANDATORY,
        abstractSchemaName = "PatientEJB")
@FileGeneration(localClass = Constants.Bool.TRUE,
                localHome = Constants.Bool.TRUE,
                valueClass = Constants.Bool.FALSE)
@Finders({
    @Finder(signature = "java.util.Collection findPatientByStatus(java.lang.String n0)",
            ejbQl = "SELECT OBJECT(o) FROM PatientEJB AS o, UserEJB AS u WHERE u.status = ?1"),
    @Finder(signature = "java.util.Collection findByLastNameFirstName(java.lang.String n0, java.lang.String n1)",
            ejbQl = "SELECT OBJECT(o) FROM PatientEJB AS o WHERE UPPER(o.lastName) = UPPER(?1) and UPPER(o.firstName) = UPPER(?2)"),
    @Finder(signature = "com.bea.medrec.entities.PatientLocal findBySsn(java.lang.String n0)",
            ejbQl = "SELECT OBJECT(o) FROM PatientEJB AS o  WHERE o.ssn = ?1",
            groupName = "liteWeight"),
    @Finder(signature = "com.bea.medrec.entities.PatientLocal findByEmail(java.lang.String n0)",
            ejbQl = "not used in favor of weblogic-ejb-ql for relationship caching",
            weblogicEjbQl = "SELECT OBJECT(o) FROM PatientEJB AS o WHERE UPPER(o.email) = UPPER(?1)",
            cachingName = "addressCache"),
    @Finder(signature = "com.bea.medrec.entities.PatientLocal findByPatientIdLite(java.lang.Integer n0)",
            ejbQl = "SELECT OBJECT(o) FROM PatientEJB AS o WHERE o.id = ?1",
            groupName = "liteWeight"),
    @Finder(signature = "java.util.Collection findByLastNameFirstName(java.lang.String n0, java.lang.String n1)",
            ejbQl = "SELECT OBJECT(o) FROM PatientEJB AS o WHERE UPPER(o.lastName) = UPPER(?1) and UPPER(o.firstName) = UPPER(?2)"),
    @Finder(signature = "java.util.Collection findByLastNameDOB(java.lang.String n0, java.sql.Date n1)",
            ejbQl = "SELECT OBJECT(o) FROM PatientEJB AS o WHERE UPPER(o.lastName) = UPPER(?1) and o.dateOfBirth = ?2"),
    @Finder(signature = "java.util.Collection findByLastName(java.lang.String n0)",
            ejbQl = "SELECT OBJECT(o) FROM PatientEJB AS o WHERE o.lastName = ?1"),
    @Finder(signature = "java.util.Collection findByLastNameWild(java.lang.String n0)",
            ejbQl = "not used in favor of weblogic-ejb-ql for relationship caching",
            weblogicEjbQl = "SELECT OBJECT(o) FROM PatientEJB AS o WHERE UPPER(o.lastName) like UPPER(?1) ORDERBY o.lastName",
            cachingName = "addressCache")
})
@Relations({
    @Relation(cascadeDelete = Constants.Bool.FALSE,
              cmrField = "address",
              name = "Patient-Address",
              roleName = "Patient-Has-Addresses",
              multiplicity = Relation.Multiplicity.MANY,
              targetEjb = "AddressEJB",
              fkColumn = "address_id"),
    @Relation(cascadeDelete = Constants.Bool.TRUE,
              cmrField = "user",
              name = "User-Patient",
              roleName = "Patient-Has-User",
              multiplicity = Relation.Multiplicity.ONE,
              targetEjb = "UserEJB",
              fkColumn = "email")
})
@RelationshipCachingElements({
    @RelationshipCachingElement(cachingName = "addressCache",
                                cmrField = "address",
                                groupName = "address-group")
})
public abstract class PatientEJB extends GenericEntityBean {
  // Logger
  private static Logger logger =
      MedRecLog4jFactory.getLogger(PatientEJB.class.getName());

  // Attributes
  private EntityContext ctx;
  private AddressLocalHome addrHome;

  // Local methods
  // Container managed fields
  @CmpField(column = "id",
            orderingNumber = "1",
            groupNames = "liteWeight")
  @LocalMethod()
  @PrimkeyField()
  public abstract Integer getId();

  @LocalMethod()
  public abstract void setId(Integer id);

  @CmpField(column = "first_name",
            orderingNumber = "2",
            groupNames = "liteWeight")
  @LocalMethod()
  public abstract String getFirstName();

  @LocalMethod()
  public abstract void setFirstName(String firstName);

  @CmpField(column = "middle_name",
            orderingNumber = "3",
            groupNames = "liteWeight")
  @LocalMethod()
  public abstract String getMiddleName();

  @LocalMethod()
  public abstract void setMiddleName(String middleName);

  @CmpField(column = "last_name",
            orderingNumber = "4",
            groupNames = "liteWeight")
  @LocalMethod()
  public abstract String getLastName();

  @LocalMethod()
  public abstract void setLastName(String lastName);

  @CmpField(column = "dob",
            orderingNumber = "5",
            groupNames = "liteWeight")
  @LocalMethod()
  public abstract java.sql.Date getDateOfBirth();

  @LocalMethod()
  public abstract void setDateOfBirth(java.sql.Date dob);

  @CmpField(column = "gender",
            orderingNumber = "6")
  @LocalMethod()
  public abstract String getGender();

  @LocalMethod()
  public abstract void setGender(String gender);

  @CmpField(column = "ssn",
            orderingNumber = "7")
  @LocalMethod()
  public abstract String getSsn();

  @LocalMethod()
  public abstract void setSsn(String ssn);

  @CmpField(column = "phone",
            orderingNumber = "8")
  @LocalMethod()
  public abstract String getPhone();


  @LocalMethod()
  public abstract void setPhone(String phone);

  @CmpField(column = "email",
            orderingNumber = "9")
  @LocalMethod()
  public abstract String getEmail();

  @LocalMethod()
  public abstract void setEmail(String email);

  @CmpField(column = "address_id",
            orderingNumber = "10")
  @LocalMethod()
  public abstract Integer getAddressId();

  @LocalMethod()
  public abstract void setAddressId(Integer pAddressId);

  // Container managed relation fields
  @CmrField(orderingNumber = "11")
  @LocalMethod()
  public abstract AddressLocal getAddress();

  @LocalMethod()
  public abstract void setAddress(AddressLocal val);

  @CmrField(orderingNumber = "12")
  @LocalMethod()
  public abstract UserLocal getUser();

  @LocalMethod()
  public abstract void setUser(UserLocal val);

  /**
   * <p>Returns a value object representation of the bean.</p>
   *
   * @return Patient
   */
  @LocalMethod()
  public Patient getPatient() {
    Patient patient = new Patient();
    patient.setId(getId());
    patient.setFirstName(getFirstName());
    patient.setMiddleName(getMiddleName());
    patient.setLastName(getLastName());
    Calendar cal = MedRecUtils.convertSqlDate2Calendar(getDateOfBirth());
    patient.setDateOfBirth(cal);
    patient.setGender(getGender());
    patient.setSsn(getSsn());
    patient.setPhone(getPhone());
    patient.setEmail(getEmail());
    patient.setAddress(getAddress().getAddress());
    return patient;
  }

  /**
   * <p>Returns an abbreviated value object representation of the bean.
   * Fields returned are:<br>
   * id<br>
   * first name<br>
   * middle name<br>
   * last name</p>
   *
   * @return Patient
   */
  @LocalMethod()
  public Patient getPatientLite() {
    Patient patient = new Patient();
    patient.setId(getId());
    patient.setFirstName(getFirstName());
    patient.setMiddleName(getMiddleName());
    patient.setLastName(getLastName());
    Calendar cal = MedRecUtils.convertSqlDate2Calendar(getDateOfBirth());
    patient.setDateOfBirth(cal);
    patient.setGender(getGender());
    return patient;
  }

  // Lifecycle methods
  /**
   * <p>Sets the entity context.  Get handles for all
   * required entity beans.</p>
   *
   * @param ctx EntityContext for entity
   */
  public void setEntityContext(EntityContext ctx) {
    this.ctx = ctx;
    try {
      // Entity bean homes.
      addrHome = getAddressLocalHome();
    } catch (NamingException ne) {
      throw new EJBException(ne);
    }
  }

  // Home methods
  /**
   * <p>Patient create.</p>
   */
  public Object ejbCreate(Patient patient, UserLocal userLocal)
      throws NamingException, CreateException {
    logger.debug("Patient: "+patient);
    setFirstName(patient.getFirstName());
    setMiddleName(patient.getMiddleName());
    setLastName(patient.getLastName());
    Date dob = MedRecUtils.getDate(patient.getDateOfBirth());
    setDateOfBirth(dob);
    setGender(patient.getGender());
    setSsn(patient.getSsn());
    setPhone(patient.getPhone());
    return null;
  }

  public void ejbPostCreate(Patient patient, UserLocal userLocal)
      throws NamingException, CreateException {
    logger.debug("patient: "+patient);
    try {
      setAddress(addrHome.create(patient.getAddress()));
      setUser(userLocal);
    } catch (CreateException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * <p>Patient create.</p>
   */
  public Object ejbCreate(String firstName,
                          String middleName,
                          String lastName,
                          Calendar dob,
                          String gender,
                          String ssn,
                          String phone,
                          String email,
                          Address address, UserLocal userLocal)
      throws NamingException, CreateException {
    setFirstName(firstName);
    setMiddleName(middleName);
    setLastName(lastName);
    java.sql.Date date = new java.sql.Date(dob.getTime().getTime());
    setDateOfBirth(date);
    setGender(gender);
    setSsn(ssn);
    setPhone(phone);
    return null;
  }

  public void ejbPostCreate(String firstName,
                            String middleName,
                            String lastName,
                            Calendar dob,
                            String gender,
                            String ssn,
                            String phone,
                            String email,
                            Address address,
                            UserLocal userLocal)
      throws NamingException, CreateException {
    try {
      AddressLocal addressLocal = addrHome.create(address.getStreetName1(),
          address.getStreetName2(), address.getCity(), address.getState(),
          address.getZipCode(), address.getCountry());
      setAddress(addressLocal);
      setUser(userLocal);
    } catch (CreateException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  // Utility methods
  /**
   * Get Address Entity EJB local home
   */
  public AddressLocalHome getAddressLocalHome() throws NamingException {
    return (AddressLocalHome) lookUpLocalHome(JNDINames.ADDRESS_LOCAL_HOME,
        AddressLocalHome.class);
  }

  /**
   * Get local home
   */
  public Object lookUpLocalHome(String pHome, Class pClazz) throws NamingException {
    ServiceLocator locator = ServiceLocator.getInstance();
    Object obj = locator.lookupLocalHome(pHome, pClazz);
    return obj;
  }
}
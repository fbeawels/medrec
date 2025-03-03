package com.bea.medrec.entities;

import com.bea.medrec.value.Patient;
import com.bea.medrec.value.User;
import javax.ejb.CreateException;
import weblogic.ejb.GenericEntityBean;
import weblogic.ejbgen.*;

/**
 * <p>UserEJB is a Container Managed EntityBean that
 * manipulates user persisted data.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
@CreateDefaultDbmsTables(value = "Disabled")
@EjbLocalRefs({
    @EjbLocalRef(name = "ejb/local/patient",
                 home = "com.bea.medrec.entities.PatientLocalHome",
                 local = "com.bea.medrec.entities.PatientLocal",
                 type = Constants.RefType.ENTITY,
                 link = "PatientEJB")
})
@Entity(maxBeansInCache = "1000",
        dataSourceName = "jdbc/MedRecTxDataSource",
        transTimeoutSeconds = "0",
        ejbName = "UserEJB",
        reentrant = Constants.Bool.FALSE,
        concurrencyStrategy = Constants.ConcurrencyStrategy.DATABASE,
        delayDatabaseInsertUntil = Entity.DelayDatabaseInsertUntil.EJB_POST_CREATE,
        tableName = "medrec_user",
        readTimeoutSeconds = "600",
        primKeyClass = "java.lang.String",
        defaultTransaction = Constants.TransactionAttribute.MANDATORY,
        abstractSchemaName = "UserEJB")
@FileGeneration(localClass = Constants.Bool.TRUE,
                localHome = Constants.Bool.TRUE,
                valueClass = Constants.Bool.FALSE)
@Finders({
    @Finder(signature = "java.util.Collection<UserLocal> findByStatus(java.lang.String n0)",
            ejbQl = "SELECT OBJECT(o) FROM UserEJB AS o WHERE o.status = ?1"),
    @Finder(signature = "com.bea.medrec.entities.UserLocal findByUsername(java.lang.String n0)",
            ejbQl = "SELECT OBJECT(o) FROM UserEJB AS o WHERE o.username = ?1")
})
@Relations({
    @Relation(cascadeDelete = Constants.Bool.FALSE,
              cmrField = "patient",
              name = "User-Patient",
              roleName = "User-Has-Patient",
              multiplicity = Relation.Multiplicity.ONE,
              targetEjb = "PatientEJB")
})
public abstract class UserEJB extends GenericEntityBean {

  // Local methods
  // Container managed fields
  @CmpField(column = "username",
            orderingNumber = "1")
  @LocalMethod()
      @PrimkeyField()
  public abstract String getUsername();

  @LocalMethod()
  public abstract void setUsername(String email);

  @CmpField(column = "password",
            orderingNumber = "2")
  @LocalMethod()
  public abstract String getPassword();

  @LocalMethod()
  public abstract void setPassword(String password);

  @CmpField(column = "status",
            orderingNumber = "3")
  @LocalMethod()
  public abstract String getStatus();

  @LocalMethod()
  public abstract void setStatus(String status);

  // Container manager methods
  @CmrField(orderingNumber = "4")
  @LocalMethod()
  public abstract PatientLocal getPatient();

  @LocalMethod()
  public abstract void setPatient(PatientLocal patient);

  /**
   * <p>Returns associated patient object.</p>
   */
  @LocalMethod()
  public Patient getPatientObj() {
    return getPatient().getPatientLite();
  }

  /**
   * <p>Returns a value object representation of the bean.</p>
   *
   * @return User
   */
  @LocalMethod()
  public User getUser() {
    User user = new User();
    user.setUsername(getUsername());
    user.setPassword(getPassword());
    user.setStatus(getStatus());
    return user;
  }

  // Home methods
  /**
   * <p>User create.</p>
   */
  public Object ejbCreate(String pUsername,
                          String pPassword,
                          String pStatus) throws CreateException {
    setUsername(pUsername);
    setPassword(pPassword);
    setStatus(pStatus);
    return null;
  }

  public void ejbPostCreate(String pUsername,
                            String pPassword,
                            String pStatus) throws CreateException {
    /* not implemented */
  }

  /**
   * <p>User methods.</p>
   */
  public Object ejbCreate(User user) throws CreateException {
    setUsername(user.getUsername());
    setPassword(user.getEncodedPassword());
    setStatus(user.getStatus());
    return null;
  }

  public void ejbPostCreate(User user) throws CreateException {
    /* not implemented */
  }
}
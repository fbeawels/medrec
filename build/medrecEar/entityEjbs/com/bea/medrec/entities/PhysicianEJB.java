package com.bea.medrec.entities;

import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.value.Physician;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.EntityContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import weblogic.ejb.GenericEntityBean;
import weblogic.ejbgen.*;

/**
 * <p>PhysicianEJB is an Container Managed EntityBean that
 * manipulates patient persisted data.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
@AutomaticKeyGeneration(name = "PHYSICIAN_SEQ",
                        type = AutomaticKeyGeneration.AutomaticKeyGenerationType.SEQUENCE_TABLE,
                        cacheSize = "10")
@CreateDefaultDbmsTables(value = "Disabled")
@Entity(maxBeansInCache = "1000",
        dataSourceName = "jdbc/MedRecTxDataSource",
        transTimeoutSeconds = "0",
        ejbName = "PhysicianEJB",
        reentrant = Constants.Bool.FALSE,
        concurrencyStrategy = Constants.ConcurrencyStrategy.DATABASE,
        delayDatabaseInsertUntil = Entity.DelayDatabaseInsertUntil.EJB_POST_CREATE,
        tableName = "physician",
        readTimeoutSeconds = "600",
        primKeyClass = "java.lang.Integer",
        defaultTransaction = Constants.TransactionAttribute.MANDATORY,
        abstractSchemaName = "PhysicianEJB")
@FileGeneration(localClass = Constants.Bool.TRUE,
                localHome = Constants.Bool.TRUE,
                valueClass = Constants.Bool.FALSE)
@Finders({
    @Finder(signature = "com.bea.medrec.entities.PhysicianLocal findByLastName(java.lang.String n0)",
            ejbQl = "SELECT OBJECT(o) FROM PhysicianEJB AS o WHERE o.lastName = ?1"),
    @Finder(signature = "com.bea.medrec.entities.PhysicianLocal findByFirstLastName(java.lang.String n0, java.lang.String n1)",
            ejbQl = "SELECT OBJECT(o) FROM PhysicianEJB AS o WHERE o.firstName = ?1 and o.lastName = ?2",
            includeUpdates = Constants.Bool.FALSE)
})
@Relations({
    @Relation(cascadeDelete = Constants.Bool.FALSE,
              cmrField = "records",
              name = "Record-Physician",
              roleName = "Physician-Has-Records",
              multiplicity = Relation.Multiplicity.ONE,
              targetEjb = "RecordEJB")
})
public abstract class PhysicianEJB extends GenericEntityBean {
  // Logger
  private static Logger logger =
      MedRecLog4jFactory.getLogger(PhysicianEJB.class.getName());

  // Attributes
  private EntityContext ctx;

  // Local methods
  // Container managed fields
  @CmpField(column = "id",
            orderingNumber = "1")
  @LocalMethod()
  @PrimkeyField()
  public abstract Integer getId();

  @LocalMethod()
  public abstract void setId(Integer id);

  @CmpField(column = "first_name",
            orderingNumber = "2",
      groupNames = "physician-group")
  @LocalMethod()
  public abstract String getFirstName();

  @LocalMethod()
  public abstract void setFirstName(String firstName);

  @CmpField(column = "middle_name",
            orderingNumber = "3",
            groupNames = "physician-group")
  @LocalMethod()
  public abstract String getMiddleName();

  @LocalMethod()
  public abstract void setMiddleName(String middleName);

  @CmpField(column = "last_name",
            orderingNumber = "4",
            groupNames = "physician-group")
  @LocalMethod()
  public abstract String getLastName();

  @LocalMethod()
  public abstract void setLastName(String lastName);

  @CmpField(column = "phone",
            orderingNumber = "5")
  @LocalMethod()
  public abstract String getPhone();

  @LocalMethod()
  public abstract void setPhone(String phone);

  @CmpField(column = "email",
            orderingNumber = "6")
  @LocalMethod()
  public abstract String getEmail();

  @LocalMethod()
  public abstract void setEmail(String email);

  @CmrField(orderingNumber = "7")
  @LocalMethod()
  public abstract Collection getRecords();

  @LocalMethod()
  public abstract void setRecords(Collection records);

  /**
   * <p>Returns a value object representation of the bean.</p>
   *
   * @return Physician
   */
  @LocalMethod()
  public Physician getPhysician() {
    Physician physician = new Physician();
    physician.setId(getId());
    physician.setFirstName(getFirstName());
    physician.setMiddleName(getMiddleName());
    physician.setLastName(getLastName());
    physician.setPhone(getPhone());
    physician.setEmail(getEmail());
    return physician;
  }

  /**
   * <p>Returns an abbreviated value object representation of the bean.
   * Fields returned are:<br>
   * id<br>
   * first name<br>
   * middle name<br>
   * last name</p>
   *
   * @return Physician
   */
  @LocalMethod()
  public Physician getPhysicianLite() {
    Physician physician = new Physician();
    physician.setId(getId());
    physician.setFirstName(getFirstName());
    physician.setMiddleName(getMiddleName());
    physician.setLastName(getLastName());
    return physician;
  }

  // Home methods
  /**
   * <p>Physician create.</p>
   */
  public Object ejbCreate(Physician physician)
      throws NamingException, CreateException {
    logger.debug("Creating physician: "+physician.toString());
    setFirstName(physician.getFirstName());
    setMiddleName(physician.getMiddleName());
    setLastName(physician.getLastName());
    setPhone(physician.getPhone());
    setEmail(physician.getEmail());
    return null;
  }

  public void ejbPostCreate(Physician physician) throws CreateException {
    /* not implemented */
  }

  public Object ejbCreate(String firstName, String middleName, String lastName)
      throws NamingException, CreateException {
    setFirstName(firstName);
    setMiddleName(middleName);
    setLastName(lastName);
    return null;
  }

  public void ejbPostCreate(String firstName,
                            String middleName,
                            String lastName) throws CreateException {
    /* not implemented */
  }

  @LocalMethod()
  public String toString() {
    StringBuffer str = new StringBuffer();
    str.append("PHYSICIAN [Id: "+getId());
    str.append("| Name: "+getFirstName());
    str.append(" "+getMiddleName());
    str.append(" "+getLastName());
    str.append("| Phone: "+getPhone());
    str.append("| Email: "+getEmail());
    str.append("]");
    return str.toString();
  }
}
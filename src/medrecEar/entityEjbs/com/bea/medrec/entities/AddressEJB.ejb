package com.bea.medrec.entities;

import com.bea.medrec.value.Address;
import javax.ejb.CreateException;
import weblogic.ejb.GenericEntityBean;
import weblogic.ejbgen.*;

/**
 * <p>AddressEJB is an Container Managed EntityBean that
 * manipulates address persisted data.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
@AutomaticKeyGeneration(name = "ADDRESS_SEQ",
                        type = AutomaticKeyGeneration.AutomaticKeyGenerationType.SEQUENCE_TABLE,
                        cacheSize = "10")
@CreateDefaultDbmsTables(value = "Disabled")
@Entity(maxBeansInCache = "1000",
        dataSourceName = "jdbc/MedRecTxDataSource",
        transTimeoutSeconds = "0",
        ejbName = "AddressEJB",
        reentrant = Constants.Bool.FALSE,
        concurrencyStrategy = Constants.ConcurrencyStrategy.DATABASE,
        delayDatabaseInsertUntil = Entity.DelayDatabaseInsertUntil.EJB_POST_CREATE,
        tableName = "address",
        readTimeoutSeconds = "600",
        primKeyClass = "java.lang.Integer",
        defaultTransaction = Constants.TransactionAttribute.MANDATORY)
@FileGeneration(localClass = Constants.Bool.TRUE,
                localHome = Constants.Bool.TRUE,
                valueClass = Constants.Bool.FALSE)
@Relations({
    @Relation(cascadeDelete = Constants.Bool.FALSE,
              name = "Patient-Address",
              roleName = "Address-Has-Patient",
              multiplicity = Relation.Multiplicity.ONE,
              targetEjb = "PatientEJB")
})
public abstract class AddressEJB extends GenericEntityBean {
  // Container managed fields

  @CmpField(column = "id",
            orderingNumber = "1")
  @LocalMethod()
  @PrimkeyField()
  public abstract Integer getId();

  @LocalMethod()
  public abstract void setId(Integer id);

  @CmpField(column = "street1",
            orderingNumber = "2",
            groupNames = "address-group")
  @LocalMethod()
  public abstract String getStreetName1();

  @LocalMethod()
  public abstract void setStreetName1(String streetName1);

  @CmpField(column = "street2",
            orderingNumber = "3",
            groupNames = "address-group")
  @LocalMethod()
  public abstract String getStreetName2();

  @LocalMethod()
  public abstract void setStreetName2(String streetName2);

  @CmpField(column = "city",
            orderingNumber = "4",
            groupNames = "address-group")
  @LocalMethod()
  public abstract String getCity();

  @LocalMethod()
  public abstract void setCity(String city);

  @CmpField(column = "state",
            orderingNumber = "5",
            groupNames = "address-group")
  @LocalMethod()
  public abstract String getState();

  @LocalMethod()
  public abstract void setState(String state);

  @CmpField(column = "zip",
            orderingNumber = "6",
            groupNames = "address-group")
  @LocalMethod()
  public abstract String getZipCode();

  @LocalMethod()
  public abstract void setZipCode(String zipCode);

  @CmpField(column = "country",
            orderingNumber = "7",
            groupNames = "address-group")
  @LocalMethod()
  public abstract String getCountry();

  @LocalMethod()
  public abstract void setCountry(String country);
  
  @LocalMethod()
  public Address getAddress() {
    Address address = new Address();
    address.setId(getId());
    address.setStreetName1(getStreetName1());
    address.setStreetName2(getStreetName2());
    address.setCity(getCity());
    address.setState(getState());
    address.setZipCode(getZipCode());
    address.setCountry(getCountry());
    return address;
  }

  // Home methods
  /**
   * <p>Address create.</p>
   */
  public Object ejbCreate(Address address) throws CreateException {
    setStreetName1(address.getStreetName1());
    setStreetName2(address.getStreetName2());
    setCity(address.getCity());
    setState(address.getState());
    setZipCode(address.getZipCode());
    setCountry(address.getCountry());
    return null;
  }

  public void ejbPostCreate(Address address) throws CreateException {
    /* not implemented */
  }

  /**
   * <p>Address create.</p>
   */
  public Object ejbCreate(String streetName1,
                          String streetName2,
                          String city,
                          String state,
                          String zipCode,
                          String country) throws CreateException {
    setStreetName1(streetName1);
    setStreetName2(streetName2);
    setCity(city);
    setState(state);
    setZipCode(zipCode);
    setCountry(country);
    return null;
  }

  public void ejbPostCreate(String streetName1,
                            String streetName2,
                            String city,
                            String state,
                            String zipCode,
                            String country) throws CreateException {
    /* not implemented */
  }
}
package com.bea.medrec.entities;

import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.value.VitalSigns;
import javax.ejb.CreateException;
import org.apache.log4j.Logger;
import weblogic.ejb.GenericEntityBean;
import weblogic.ejbgen.*;

/**
 * <p>VitalSignsEJB is an Container Managed EntityBean that
 * manipulates record persisted data.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
@AutomaticKeyGeneration(name = "VITAL_SIGNS_SEQ",
                        type = AutomaticKeyGeneration.AutomaticKeyGenerationType.SEQUENCE_TABLE,
                        cacheSize = "10")
@CreateDefaultDbmsTables(value = "Disabled")
@Entity(maxBeansInCache = "1000",
        dataSourceName = "jdbc/MedRecTxDataSource",
        transTimeoutSeconds = "0",
        ejbName = "VitalSignsEJB",
        reentrant = Constants.Bool.FALSE,
        concurrencyStrategy = Constants.ConcurrencyStrategy.DATABASE,
        delayDatabaseInsertUntil = Entity.DelayDatabaseInsertUntil.EJB_POST_CREATE,
        tableName = "vital_signs",
        readTimeoutSeconds = "600",
        primKeyClass = "java.lang.Integer",
        defaultTransaction = Constants.TransactionAttribute.MANDATORY,
        abstractSchemaName = "VitalSignsEJB")
@FileGeneration(localClass = Constants.Bool.TRUE,
                localHome = Constants.Bool.TRUE,
                valueClass = Constants.Bool.FALSE)
@Relations({
    @Relation(cascadeDelete = Constants.Bool.FALSE,
              name = "Record-VitalSigns",
              roleName = "VitalSigns-Has-Record",
              multiplicity = Relation.Multiplicity.ONE,
              targetEjb = "RecordEJB")
})
public abstract class VitalSignsEJB extends GenericEntityBean{
// Logger
  private static Logger logger =
      MedRecLog4jFactory.getLogger(VitalSignsEJB.class.getName());

  // Container managed fields

  @CmpField(column = "id",
            orderingNumber = "1")
  @LocalMethod()
  @PrimkeyField()
  public abstract Integer getId();


  @LocalMethod()
  public abstract void setId(Integer id);


  @CmpField(column = "temperature",
            orderingNumber = "2",
            groupNames = "vitalSigns-group")
  @LocalMethod()
  public abstract String getTemperature();


  @LocalMethod()
  public abstract void setTemperature(String temperature);


  @CmpField(column = "blood_pressure",
            orderingNumber = "3",
            groupNames = "vitalSigns-group")
  @LocalMethod()
  public abstract String getBloodPressure();

  @LocalMethod()
  public abstract void setBloodPressure(String bloodPressure);

  @CmpField(column = "pulse",
            orderingNumber = "4",
            groupNames = "vitalSigns-group")
  @LocalMethod()
  public abstract String getPulse();

  @LocalMethod()
  public abstract void setPulse(String pulse);

  @CmpField(column = "weight",
            orderingNumber = "5",
            groupNames = "vitalSigns-group")
  @LocalMethod()
  public abstract Integer getWeight();

  @LocalMethod()
  public abstract void setWeight(Integer weight);

  @CmpField(column = "height",
            orderingNumber = "6",
            groupNames = "vitalSigns-group")
  @LocalMethod()
  public abstract Integer getHeight();

  @LocalMethod()
  public abstract void setHeight(Integer height);

  @LocalMethod()
  public VitalSigns getVitalSigns() {
    VitalSigns vitals = new VitalSigns();
    vitals.setId(getId());
    vitals.setTemperature(getTemperature());
    vitals.setBloodPressure(getBloodPressure());
    vitals.setPulse(getPulse());
    vitals.setWeight(getWeight());
    vitals.setHeight(getHeight());
    return vitals;
  }

  // Home methods
  /**
   * <p>Vitals create.</p>
   */
  public Object ejbCreate(String temperature,
                          String bloodPressure,
                          String pulse,
                          Integer weight,
                          Integer height) throws CreateException {
    setTemperature(temperature);
    setBloodPressure(bloodPressure);
    setPulse(pulse);
    setWeight(weight);
    setHeight(height);
    return null;
  }

  public void ejbPostCreate(String temperature,
                            String bloodPressure,
                            String pulse,
                            Integer weight,
                            Integer height) throws CreateException {
    /* not implemented */
  }

  /**
   * <p>Vitals create.</p>
   */
  public Object ejbCreate(VitalSigns vitalSigns) throws CreateException {
    logger.debug("Creating vitalSigns: "+vitalSigns.toString());
    setTemperature(vitalSigns.getTemperature());
    setBloodPressure(vitalSigns.getBloodPressure());
    setPulse(vitalSigns.getPulse());
    setWeight(vitalSigns.getWeight());
    setHeight(vitalSigns.getHeight());
    return null;
  }

  public void ejbPostCreate(VitalSigns vitalSigns) throws CreateException {
    /* not implemented */
  }

  @LocalMethod()
  public String toString() {
    StringBuffer str = new StringBuffer();
    str.append("VITALSIGNS [Id: "+getId());
    str.append(" | Temp: "+getTemperature());
    str.append(" | BP: "+getBloodPressure());
    str.append(" | Pulse: "+getPulse());
    str.append(" | Weight: "+getWeight());
    str.append(" | Height: "+getHeight());
    str.append("]");
    return str.toString();
  }
}
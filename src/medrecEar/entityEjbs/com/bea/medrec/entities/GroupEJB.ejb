package com.bea.medrec.entities;

import javax.ejb.CreateException;
import weblogic.ejb.GenericEntityBean;
import weblogic.ejbgen.*;

/**
 * <p>GroupEJB is a Container Managed EntityBean that
 * manipulates group persisted data.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
@CreateDefaultDbmsTables(value = "Disabled")
@Entity(maxBeansInCache = "1000",
        dataSourceName = "jdbc/MedRecTxDataSource",
        transTimeoutSeconds = "0",
        ejbName = "GroupEJB",
        reentrant = Constants.Bool.FALSE,
        concurrencyStrategy = Constants.ConcurrencyStrategy.DATABASE,
        delayDatabaseInsertUntil = Entity.DelayDatabaseInsertUntil.EJB_POST_CREATE,
        tableName = "groups",
        readTimeoutSeconds = "600",
        primKeyClass = "com.bea.medrec.entities.GroupCPK",
        defaultTransaction = Constants.TransactionAttribute.REQUIRED,
        abstractSchemaName = "Mandatory")
@FileGeneration(localClass = Constants.Bool.TRUE,
                localHome = Constants.Bool.TRUE,
                pkClass = Constants.Bool.TRUE,
                valueClass = Constants.Bool.FALSE)
public abstract class GroupEJB extends GenericEntityBean {
// Container managed fields
  @CmpField(column = "group_name",
            orderingNumber = "1")
  @LocalMethod()
  @PrimkeyField()
  public abstract String getGroupname();


  @LocalMethod()
  public abstract void setGroupname(String groupname);

  @CmpField(column = "username",
            orderingNumber = "2")
  @LocalMethod()
  @PrimkeyField()
  public abstract String getUsername();


  @LocalMethod()
  public abstract void setUsername(String username);

  // Home methods
  /**
* <p>Group create.</p>
*/
  public Object ejbCreate(String groupname, String username)
      throws CreateException {
    setGroupname(groupname);
    setUsername(username);
    return null;
  }

  public void ejbPostCreate(String groupname, String username)
      throws CreateException  {
    /* not implemented */
  }
}
package com.bea.medrec.entities;


/*
  ** This file was automatically generated by 
  ** EJBGen WebLogic Server 10.0 SP0  Mon Mar 26 02:02:31 BST 2007 914577 
*/


import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import java.util.Collection;

// BEGIN imports from bean class
import javax.ejb.*;
import weblogic.ejb.*;
import weblogic.ejbgen.*;
// END imports from bean class


/**
 * <p>GroupEJB is a Container Managed EntityBean that
 manipulates group persisted data.</p>
 */

public interface GroupLocalHome extends EJBLocalHome {

  public GroupLocal findByPrimaryKey(com.bea.medrec.entities.GroupCPK primaryKey)  throws FinderException;





  /**
   * <p>Group create.</p>
   */
  public GroupLocal create(String groupname, String username)     throws CreateException, javax.ejb.CreateException;



}

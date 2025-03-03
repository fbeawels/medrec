package com.bea.medrec.controller;

import javax.management.StandardMBean;

/**
 * <p>The implementation for a standard MBean that keeps track of how many
 * times RecordSessionEJB writes a prescription to the database.</p>
 * <p>The MBean's interface is implemented in this class (instead of in
 * the EJB itself) so that management logic is separate from business logic.</p> 
 * <p>All implementations of standard MBeans must be public classes.</p>
 * <p>This class extends javax.management.StandardMBean so that the
 * MBean can follow BEA's naming conventions:</p>
 * <ul>
 * <li>The interface file is named <code><i>Business-object</i>MBean.java</code>
 * <br/>where <i>Business-object</i> is the object that is being managed.</li>
 * <li>The implementation file is named <code><i>MBean-interface</i>Impl.java</code>
 * </li></ul>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */

public class RecordSessionEJBMBeanImpl
    extends StandardMBean implements RecordSessionEJBMBean {
	
  /**
   * <p>Instance variable for the number of times RecordSessionEJB writes
   * a prescription to the database.</p>
   * <p>The variable is static because RecordSessionEJB is
   * static.</p>
   */	
  public static int totalRx = 0;
 
  /**
   * <p>Implements the getTotalRx() method in the management interface.</p>
   */
  public int getTotalRx() {
    return totalRx;
  }
 
  /**
   * <p>Used by RecordSessionEJB to push management data to this MBean.</p>
   * <p>The RecordSessionEJB.addPrescriptions() method is implemented to invoke
   * this method. </p>
   */
  public static void incrementTotalRx() {
    totalRx++;
  }

  public void resetTotalRx() {
    totalRx = 0;
  }

  /**
   * <p>Because the class extends javax.management.StandardMBean, it must
   * provide a public constructor that implements the 
   * StandardMBean(Object implementation, Class mbeanInterface) constructor.</p>
   */
  public RecordSessionEJBMBeanImpl() throws
      javax.management.NotCompliantMBeanException {
    super(RecordSessionEJBMBean.class);
  }
}

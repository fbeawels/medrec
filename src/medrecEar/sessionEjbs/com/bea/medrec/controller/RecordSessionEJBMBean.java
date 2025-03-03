package com.bea.medrec.controller;

/**
 * <p>The interface for a standard MBean that keeps track of how many
 * times RecordSessionEJB writes a prescription to the database.</p>
 * <p>Because this interface is part of a standard MBean, when the 
 * MBean is registered in an MBean server, the MBean server introspects
 * the MBean and presents it to JMX clients as having one read-only 
 * attribute named "TotalRx" and one operation named "resetTotalRx".</p>
 * <p>The implementation for this interface is RecordSessionEJBMBeanImpl.java</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public interface RecordSessionEJBMBean {
  public int getTotalRx();
  public void resetTotalRx();
}

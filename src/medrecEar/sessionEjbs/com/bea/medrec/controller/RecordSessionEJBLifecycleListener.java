package com.bea.medrec.controller;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.naming.InitialContext;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationLifecycleEvent;
import weblogic.application.ApplicationLifecycleListener;

/**
 * <p>Manages the life cycle of an MBean that keeps track of how many
 * times RecordSessionEJB writes a prescription to the database.</p>
 * <p>The WebLogic Server deployment service emits notifications
 * (ApplicationLifecycleEvent objects) at specific parts of an 
 * application's life cycle. When an ApplicationLifecyceListener
 * receives such a notification, it invokes one of its methods.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class RecordSessionEJBLifecycleListener
    extends ApplicationLifecycleListener {

  private static javax.management.MBeanServer server;

  /**
   * <p>To register the RecordSessionEJBMBean after RecordSessionEJB starts,
   * this class implements the ApplicationLifecyceListener.postStart()
   * method, which is invoked when the class receives notification that
   * the EJB has started.</p>
   * <p>In this postStart() implementation, the listener looks up the MBeanServer 
   * interface for the WebLogic Server Runtime MBean Sever. The interface is 
   * registered in the JNDI tree. Because this listener class is not part of a 
   * J2EE module, the JNDI name for the MBeanServer interface is 
   * "java:comp/jmx/runtime". For code that runs inside a J2EE module, the 
   * JNDI name is "java:comp/env/jmx/runtime".
   */
  public void postStart(ApplicationLifecycleEvent evt) {
    try {
      ApplicationContext appctx = evt.getApplicationContext();
      String appID = appctx.getApplicationId();
      ObjectName MBeanON = getMBeanON(appID);
      RecordSessionEJBMBeanImpl mgmtClass = new RecordSessionEJBMBeanImpl();

      // get the mbean server..
      InitialContext ctx = new InitialContext();
      server = (MBeanServer) ctx.lookup("java:comp/jmx/runtime");

      // register the model MBean in the MBean server
      server.registerMBean(mgmtClass, MBeanON);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * <p>To unregister the RecordSessionEJBMBean when RecordSessionEJB stops,
   * this class implements the ApplicationLifecyceListener.preStop()
   * method, which is invoked when the class receives notification that
   * the EJB is being stopped.</p>
   */
  public void preStop(ApplicationLifecycleEvent evt) {
    try {
      ApplicationContext appctx = evt.getApplicationContext();
      String appID = appctx.getApplicationId();
      ObjectName MBeanON = getMBeanON(appID);

      // Unregister the model MBean in the MBean server
      server.unregisterMBean(MBeanON);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private ObjectName getMBeanON(String AppID)
      throws MalformedObjectNameException {
    // Create a JMX object name for the MBean
    return new ObjectName("com.bea.medrec:Name=" + AppID +
        ",Type=com.bea.medrec.controller.RecordSessionEJBMBean");
  }
}

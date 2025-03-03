package com.bea.medrec.init;

import com.bea.medrec.admin.AdminReport;
import weblogic.management.mbeanservers.runtime.RuntimeServiceMBean;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Register Custom MBeans on server startup.
 *
 * @author Copyright (c) 2006 by BEA Systems, Inc. All Rights Reserved.
 */
public class RegisterMBeansServlet extends HttpServlet implements Runnable {
  public void run() {
    return;
  }

  /**
   * init method.
   *
   * @param config
   * @throws ServletException
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    this.registerMBeans();
  }

  /**
   * Register Customer MBeans
   */
  public void registerMBeans() {
    try {
      ObjectName objName = new ObjectName("medrec:Name=AdminReportMBean,Type=CustomMBeanType");

      // get the mbean server..
      InitialContext ctx = new InitialContext();
      MBeanServer mbeanServer = (MBeanServer)ctx.lookup("java:comp/env/jmx/runtime");
      mbeanServer.createMBean(AdminReport.class.getName(), objName);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}

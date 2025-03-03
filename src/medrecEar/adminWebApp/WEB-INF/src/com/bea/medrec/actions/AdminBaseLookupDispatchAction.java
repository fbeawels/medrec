package com.bea.medrec.actions;

import com.bea.medrec.controller.AdminSession;
import com.bea.medrec.controller.AdminSessionHome;
import com.bea.medrec.utils.MedRecLog4jFactory;
import javax.naming.InitialContext;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.mbeanservers.runtime.RuntimeServiceMBean;
import weblogic.management.runtime.WLDFAccessRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import com.bea.medrec.utils.ClientException;
import com.bea.medrec.utils.WLSMBeanServerConnectionFactory;
import java.io.IOException;
import java.util.Locale;

/**
 * <p>Base lookup dispatch action encapsulating
 * common admin webapp functionality.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public abstract class AdminBaseLookupDispatchAction
    extends BaseLookupDispatchAction implements AdminConstants {
  private static Logger logger =
      MedRecLog4jFactory.getLogger(AdminBaseLookupDispatchAction.class.getName());

  protected InitialContext ctx = null;
  private AdminSession adminSession;
  private MBeanServerConnection mbeanServerConnection = null;
  private WLDFAccessRuntimeMBean dar = null;

  /**
   * <p>Retrives AdminSession home.
   * If instance does not exist, retrieve a new instance.<p>
   *
   * @return AdminSession
   */
  protected AdminSession getAdminSession() throws Exception {
    if (ctx == null) ctx = new InitialContext();
    if (adminSession == null) {
      logger.debug("Getting new admin session.");
      this.adminSession = getAdminSessionHome();
    }
    return this.adminSession;
  }

  protected WLDFAccessRuntimeMBean getDiagntosicMBean(
      HttpServletRequest request)
      throws Exception {
    logger.info("Getting WLDF runtime MBean.");
    RuntimeServiceMBean rtService = null;
    ObjectName rtSvcObjName = null;

    if (dar == null) {
      rtSvcObjName = new ObjectName(RuntimeServiceMBean.OBJECT_NAME);

      try {
        rtService = (RuntimeServiceMBean)
          MBeanServerInvocationHandler.newProxyInstance(
            getMBeanServerConnection(request), rtSvcObjName);
        logger.debug("Got RuntimeServiceMBean.");
      } catch (Throwable t) {
        logger.error(t.getMessage());
        throw new ClientException(t);
      }

      ServerRuntimeMBean srt = rtService.getServerRuntime();
      dar = srt.getWLDFRuntime().getWLDFAccessRuntime();
      logger.debug("Obtained WLDFAccessRuntimeMBean.");
    }
    return dar;
  }

  protected MBeanServerConnection getDomainMBeanServerConnection(
      HttpServletRequest request)
      throws ClientException {
    WLSMBeanServerConnectionFactory mbsFactory = null;
        // get server and port of admin server
    // values only located in default message properties files
    String server = getMessage(request, Locale.ENGLISH, "wls.admin.server.host");
    String port = getMessage(request, Locale.ENGLISH, "wls.admin.server.post");
    String wlsUser = getMessage(request, Locale.ENGLISH, "wls.admin.server.user");
    String wlsPassword = getMessage(request, Locale.ENGLISH,
        "wls.admin.server.password");

    if (mbeanServerConnection == null) {
      logger.debug("Getting MBeanServerConnection: ["+wlsUser+"], ["+wlsPassword+
          "], ["+server+"], ["+port+"]");
      mbsFactory = WLSMBeanServerConnectionFactory.getInstance(wlsUser,
          wlsPassword, server, Integer.parseInt(port));
      try {
        mbeanServerConnection = mbsFactory.getDomainRuntimeMBeanServer();
      } catch (IOException e) {
        logger.error(e.getMessage());
        throw new ClientException(e);
      }
    }
    return mbeanServerConnection;
  }

  protected MBeanServerConnection getEditMBeanServerConnection(
      HttpServletRequest request)
      throws ClientException {
    WLSMBeanServerConnectionFactory mbsFactory = null;
        // get server and port of admin server
    // values only located in default message properties files
    String server = getMessage(request, Locale.ENGLISH, "wls.admin.server.host");
    String port = getMessage(request, Locale.ENGLISH, "wls.admin.server.post");
    String wlsUser = getMessage(request, Locale.ENGLISH, "wls.admin.server.user");
    String wlsPassword = getMessage(request, Locale.ENGLISH,
        "wls.admin.server.password");

    if (mbeanServerConnection == null) {
      logger.debug("Getting MBeanServerConnection: ["+wlsUser+"], ["+wlsPassword+
          "], ["+server+"], ["+port+"]");
      mbsFactory = WLSMBeanServerConnectionFactory.getInstance(wlsUser,
          wlsPassword, server, Integer.parseInt(port));
      try {
        mbeanServerConnection = mbsFactory.getEditMBeanServer();
      } catch (IOException e) {
        logger.error(e.getMessage());
        throw new ClientException(e);
      }
    }
    return mbeanServerConnection;
  }


  protected MBeanServerConnection getMBeanServerConnection(
      HttpServletRequest request)
      throws ClientException {
    WLSMBeanServerConnectionFactory mbsFactory = null;
        // get server and port of admin server
    // values only located in default message properties files
    String server = getMessage(request, Locale.ENGLISH, "wls.admin.server.host");
    String port = getMessage(request, Locale.ENGLISH, "wls.admin.server.post");
    String wlsUser = getMessage(request, Locale.ENGLISH, "wls.admin.server.user");
    String wlsPassword = getMessage(request, Locale.ENGLISH,
        "wls.admin.server.password");

    if (mbeanServerConnection == null) {
      logger.debug("Getting MBeanServerConnection: ["+wlsUser+"], ["+wlsPassword+
          "], ["+server+"], ["+port+"]");
      mbsFactory = WLSMBeanServerConnectionFactory.getInstance(wlsUser,
          wlsPassword, server, Integer.parseInt(port));
      try {
        mbeanServerConnection = mbsFactory.getRuntimeMBeanServer();
      } catch (IOException e) {
        logger.error(e.getMessage());
        throw new ClientException(e);
      }
    }
    return mbeanServerConnection;
  }
  //   P R I V A T E   M E T H O D S
  /**
   * <p>Get AdminSession</p>
   *
   * @return AdminSession
   */
  private AdminSession getAdminSessionHome() throws Exception {
    AdminSessionHome home = (AdminSessionHome) ctx.lookup(
        "java:/comp/env/AdminSessionEJB");
    return (AdminSession) home.create();
  }
}

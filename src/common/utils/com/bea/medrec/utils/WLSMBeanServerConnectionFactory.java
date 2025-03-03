package com.bea.medrec.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.net.MalformedURLException;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;
import org.apache.log4j.Logger;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.mbeanservers.edit.EditServiceMBean;
import weblogic.management.mbeanservers.runtime.RuntimeServiceMBean;

/**
 * Utility to obtain WLS MBean Objects.
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class WLSMBeanServerConnectionFactory {

  private static Logger logger =
      MedRecLog4jFactory.getLogger(WLSMBeanServerConnectionFactory.class.getName());

  private static final String JNDI = "/jndi/";

  // Holds value of property serverUrl.
  private String serverUrl;

  // Holds value of property username.
  private String username;

  // Holds value of property password.
  private String password;

  // Holds value of property editServer.
  private MBeanServerConnection editServer;

  // Holds value of property runtimeServer.
  private MBeanServerConnection runtimeServer;

  // Holds value of property domainServer.
  private MBeanServerConnection domainServer;

  // Holds value of property host.
  private String host;

  // Holds value of property port.
  private int port;

  // Holds value of property protocol.
  private String protocol = "t3";

  ArrayList<Object> connectorList =  new ArrayList<Object>();

  private WLSMBeanServerConnectionFactory(String user,
                                         String password,
                                         String host,
                                         int port)
  {
    // connect to an MBeanServer using the BH process name
    setHost(host);
    setPort(port);
    setServerUrl(getProtocol()+"://"+getHost()+":"+ getPort());
    setUsername(user);
    setPassword(password);
  }

  public static WLSMBeanServerConnectionFactory getInstance(String user,
                                                            String password,
                                                            String host,
                                                            int port) {
    return new WLSMBeanServerConnectionFactory(user, password, host, port);
  }

  /**
   * Getter for property serverUrl.
   * @return Value of property serverUrl.
   */
  public String getServerUrl() {
    return this.serverUrl;
  }

  /**
   * Setter for property serverUrl.
   * @param serverUrl New value of property serverUrl.
   */
  public void setServerUrl(String serverUrl) {
    this.serverUrl = serverUrl;
  }

  /**
   * Getter for property username.
   * @return Value of property username.
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Setter for property username.
   * @param username New value of property username.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Getter for property password.
   * @return Value of property password.
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Setter for property password.
   * @param password New value of property password.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Getter for property host.
   * @return Value of property host.
   */
  public String getHost() {
    return this.host;
  }

  /**
   * Setter for property host.
   * @param host New value of property host.
   */
  public void setHost(String host) {
    this.host = host;
  }

  /**
   * Getter for property port.
   * @return Value of property port.
   */
  public int getPort() {
    return this.port;
  }

  /**
   * Setter for property port.
   * @param port New value of property port.
   */
  public void setPort(int port) {
    this.port = port;
  }

  /**
   * Getter for property protocol.
   * @return Value of property protocol.
   */
  public String getProtocol() {
    return this.protocol;
  }

  /**
   * Setter for property protocol.
   * @param protocol New value of property protocol.
   */
  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public MBeanServerConnection getEditMBeanServer() throws IOException {
    if (editServer == null) {
      editServer =
        getMBeanServerConnection(JNDI+
          EditServiceMBean.MBEANSERVER_JNDI_NAME);
    }
    return editServer;
  }

  public MBeanServerConnection getRuntimeMBeanServer() throws IOException {
    if (runtimeServer == null) {
      runtimeServer =
        getMBeanServerConnection(JNDI+
          RuntimeServiceMBean.MBEANSERVER_JNDI_NAME);
    }
    return runtimeServer;
  }

  public MBeanServerConnection getDomainRuntimeMBeanServer() throws IOException
  {
    if (domainServer == null) {
      domainServer =
        getMBeanServerConnection(
          JNDI+DomainRuntimeServiceMBean.MBEANSERVER_JNDI_NAME
          );
    }
    return domainServer;
  }

  public void closeConnections() throws IOException {
    Iterator it = connectorList.iterator();
    try {
      while (it.hasNext()) {
        JMXConnector c = (JMXConnector) it.next();
        try {
          c.close();
        } catch (IOException ioex) {
          logger.debug("Caught IOException closing JMXConnector with id " +
                 c.getConnectionId());
        }
      }
    } finally {
      connectorList.clear();
    }
  }

  private MBeanServerConnection getMBeanServerConnection(String jndiName)
    throws IOException
  {
    JMXServiceURL serviceURL = null;
    JMXConnector connector = null;
    Hashtable<String,String> h = new Hashtable<String,String>();
    MBeanServerConnection connection = null;

    try {
      serviceURL =
        new JMXServiceURL("t3", getHost(), getPort(), jndiName);
    }
    catch (MalformedURLException mue) {
      logger.error(
        "While trying to get JMXServiceURL got a malformed URL: "+
        host+":"+port+" due to: "+mue.getMessage());
      throw mue;
    }

    logger.debug("user name ["+getUsername()+"]");
    logger.debug("password ["+getPassword()+"]");
    h = new Hashtable<String, String>();
    h.put(Context.SECURITY_PRINCIPAL, getUsername());
    h.put(Context.SECURITY_CREDENTIALS, getPassword());
    h.put(JMXConnectorFactory.PROTOCOL_PROVIDER_PACKAGES,
          "weblogic.management.remote");

    try {
      connector = JMXConnectorFactory.connect(serviceURL, h);
      logger.debug("Using JMX Connector to connect to serviceURL="+serviceURL);
      connectorList.add(connector);
    }
    catch (IOException ioe) {
      logger.error(
        "Could not get JMXConnector due to "+ioe.getMessage());
      throw ioe;
    }

    try {
      connection = connector.getMBeanServerConnection();
      logger.debug("Got connection");
    }
    catch (IOException ioe) {
      logger.error(
        "ERROR: Could not get MBeanServerConnection due to "+ioe.getMessage());
      throw ioe;
    }
    return connection;
  }
}

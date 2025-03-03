package com.bea.medrec.startup;

import java.io.IOException;
import java.net.Socket;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.naming.InitialContext;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import weblogic.servlet.internal.WebAppServletContext;

/**
 * <p>Example demonstrates the use of ServletContext listeners.</p>
 *
 * @author Copyright (c) 2003-2006 by BEA Systems, Inc. All Rights Reserved.
 */
public class StartBrowser
  implements ServletContextListener, Runnable {

  private Socket socket;
  private String host = "localhost";
  private String port = "7101";
  private String page = "index.jsp";
  private static int SLEEPTIME = 500;

  // Used to identify the windows platform.
  private static final String WIN_ID = "Windows";
  // The default system browser under windows.
  private static final String WIN_PATH = "rundll32";
  // The flag to display a url.
  private static final String WIN_FLAG = "url.dll,FileProtocolHandler";
  // The default browser under unix.
  private static final String UNIX_PATH = "netscape";
  // The flag to display a url.
  private static final String UNIX_FLAG = "-remote openURL";

  // constructors
  public StartBrowser() {
  }

  public StartBrowser(String host, String port, String page) {
    this.host = host;
    this.port = port;
    this.page = page;
  }

  public void contextInitialized(ServletContextEvent sce) {
    ServletContext context = sce.getServletContext();
    // get page to popup
    String tempPage = context.getInitParameter("StartBrowser.page");
    page = (tempPage != null && tempPage.equals("") ? tempPage : page);

    // determine host and port
    getListenAddressAndPort();

    // log info msg
    log(getInfoString(page));

    // kick it off
    Thread t = new Thread(new StartBrowser(host, port, page));
    t.start();
    return;
  }

  public void contextDestroyed(ServletContextEvent sce) { }

  /**
  * Loops indefinitely trying to create a socket to host/port
  * waits sleepTime in between each try.
  * On a successful socket create, start browser.
  */
  public void run() {
    boolean loop = true;
    while (loop) {
      try {
        // loop thru until webapp is listening
        socket = new Socket(this.host, new Integer(this.port).intValue());
        socket.close();

        //launch browser
        openBrowser("http://"+this.host+":"+this.port+"/"+this.page);

        // connection made, stop looping
        loop = false;
      } catch (Exception e) {
        try {
          Thread.sleep(SLEEPTIME); // try every 500 ms
        } catch (InterruptedException ie) {}
        finally {
          try {
            socket.close();
          } catch (Exception se) {}
        }
      }
    }
  }

  // The method executing the task
  public void openBrowser(String url) {
    boolean windows = isWindowsPlatform();
    String cmd = null;
    try {
      if (windows) {
        // cmd = 'rundll32 url.dll,FileProtocolHandler http://...'
        cmd = WIN_PATH+" "+WIN_FLAG+" "+url;
        Process p = Runtime.getRuntime().exec(cmd);
      }
      else {
        // Under Unix, Netscape has to be running for the "-remote"
        // command to work.  So, we try sending the command and
        // check for an exit value.  If the exit command is 0,
        // it worked, otherwise we need to start the browser.
        // cmd = 'netscape -remote openURL(url)'
        cmd = UNIX_PATH+" "+UNIX_FLAG+"("+url+")";
        Process p = Runtime.getRuntime().exec(cmd);

        try {
          // wait for exit code -- if it's 0, command worked,
          // otherwise we need to start the browser up.
          int exitCode = p.waitFor();
          if (exitCode != 0) {
            // Command failed, start up the browser
            // cmd = 'netscape url'
            cmd = UNIX_PATH+" "+url;
            p = Runtime.getRuntime().exec(cmd);
          }
        }
        catch(InterruptedException x) {
          System.err.println("Error bringing up browser, cmd='"+cmd+"'.  "+
              "Please make sure that 'netscape' can open from the cmd-line.\n"+x);
        }
      }
    }
    catch (IOException x) {
      // couldn't exec browser
      System.err.println("Could not invoke browser, command="+cmd+"'.  "+
          "Windows: Please make sure that default browser can open.  "+
          "Unix: Please make sure that 'netscape' can open from the cmd-line.\n"+x);
    }
  }

  /**
   * Try to determine whether this application is running under Windows
   * or some other platform by examing the "os.name" property.
   *
   * @return true if this application is running under a Windows OS
   */
  public static boolean isWindowsPlatform() {
    String os = System.getProperty("os.name");
    if (os != null && os.startsWith(WIN_ID)) return true;
    else return false;
  }

  public String getInfoString(String url) {
    StringBuffer str = new StringBuffer();
    str.append("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    str.append("\nAfter the server has booted, your browser should");
    str.append("\nautomatically launch and point to the ");
    str.append("\nAvitek Medical Records Sample Application Introduction Page ");
    str.append("\nrunning on this server. If your browser fails to launch, ");
    str.append("\npoint your browser to the following URL:");
    str.append("\n\"http://"+this.host+":"+this.port+"/"+url+"\"");
    str.append("\nNote: On Unix-based systems, browser defaults to Netscape.");
    str.append("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    return str.toString();
  }

  public void getListenAddressAndPort() {
    InitialContext ctx = null;
    String listenAddress = "";
    Integer listenPort = null;
    try {
      ctx = new InitialContext();
      MBeanServer mbeanServer = (MBeanServer)ctx.lookup(
          "java:comp/env/jmx/runtime");
      String runtimeServiceName =  "com.bea:Name=RuntimeService,Type="+
        "weblogic.management.mbeanservers.runtime.RuntimeServiceMBean";

      // Create Objectname for the runtime service
      ObjectName runtimeService = new ObjectName(runtimeServiceName);

      // Get the ObjectName for the ServerRuntimeMBean
      ObjectName serverRuntime = (ObjectName) mbeanServer.getAttribute(
          runtimeService,"ServerRuntime");

      // Get the listen address of the server
      listenAddress = (String) mbeanServer.getAttribute(serverRuntime,
          "ListenAddress");
      listenPort = (Integer) mbeanServer.getAttribute(serverRuntime,
          "ListenPort");
      if ((listenAddress == null || listenAddress.equals("")
          || listenPort == null)) {
        throw new Exception("listenAddress and/or listenPort are null or == \"\"");
      }
    } catch (Exception e) {
      System.out.println("Unable to obtain listen address; using default "+
          "localhost:7101. : "+e.getMessage());
    }
    this.host = (listenAddress != null &&
        !listenAddress.equals("") ?
        listenAddress.substring(listenAddress.indexOf('/')+1) : this.host);
    this.port = (listenPort != null ? listenPort.toString() : this.port);
  }

  private void log(String str) { System.out.println(str); }
}

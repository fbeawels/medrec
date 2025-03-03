package com.bea.medrec.listeners;

import weblogic.application.ApplicationLifecycleEvent;
import weblogic.application.ApplicationLifecycleListener;
import weblogic.logging.log4j.Log4jLoggingHelper;
import weblogic.logging.LoggerNotAvailableException;
import org.apache.log4j.Logger;
import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import java.util.Calendar;
import java.util.Enumeration;
import java.text.SimpleDateFormat;

public class LoggingInitListener extends ApplicationLifecycleListener {

  public void postStart(ApplicationLifecycleEvent evt) {
    logMsg("Notice", "Initializing MedRec logger - appending to WLS Server "+
        "logger");
    Logger tmpLogger;
    try {
     tmpLogger = Log4jLoggingHelper.getLog4jServerLogger();
     Appender app = Logger.getRootLogger().getAppender(
         "MedRecLog4jRollingFileAppender");
     if (app != null) {
       tmpLogger.addAppender(app);
     } else {
       throw new Exception("MedRecLog4jRollingFileAppender is null");
     }
   } catch(Exception ex) {
     logMsg("Warning", "Unable to get a reference to the log4j Logger. Will "+
         "use non-server logger: "+ex.getMessage());
     String logFile = System.getProperty("log4j.configuration");
     if (logFile == null) {
       logMsg("Notice", "Log file not found, calling BasicConfigurator");
       BasicConfigurator.configure();
     } else {
       logFile = logFile.substring(logFile.indexOf(':')+1);
       logMsg("Notice", "Calling DOMConfigurator on: "+logFile);
       DOMConfigurator.configure(logFile);
     }
   }
  }

  public void postStop(ApplicationLifecycleEvent evt) {
    logMsg("Notice", "Detaching MedRec logger from WLS Server logger");
    Logger tmpLogger;
   try {
     tmpLogger = Log4jLoggingHelper.getLog4jServerLogger();
     Appender app = Logger.getRootLogger().getAppender(
         "MedRecLog4jRollingFileAppender");
     if (app != null) {
       tmpLogger.removeAppender(app);
     } else {
       throw new Exception("MedRecLog4jRollingFileAppender is null");
     }
   } catch(Exception ex) {
     logMsg("Warning", "Unable to detach MedRec logger: "+ex.getMessage());
   }
  }

  private void logMsg(String type, String message) {
    System.out.println("<"+getDisplayDate()+"> <"+type+"> <MedRec Logging>  "+
        message);
  }

  private String getDisplayDate() {
    SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a z");
    return format.format(Calendar.getInstance().getTime());
  }

   public static void main(String[] args) {
     // not implemented
   }
}

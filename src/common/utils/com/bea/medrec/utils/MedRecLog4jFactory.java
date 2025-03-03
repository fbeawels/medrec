package com.bea.medrec.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.log4j.Logger;
import weblogic.logging.LoggerNotAvailableException;
import weblogic.logging.log4j.Log4jLoggingHelper;

/**
 * Utility to obtain Log4j handles.
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class MedRecLog4jFactory {

  /**
   * <p>Get logger.</p>
   *
   * @param className  The HTTP request we are processing
   * @return Logger  Log4j logger.
   */
  public static Logger getLogger(String className) {
    Logger tmpLogger = null;
    try {
      tmpLogger = Logger.getLogger(className);
      if (tmpLogger == null) {
        throw new Exception("Unable to obtain logger for class: "+className);
      }
    } catch(Exception ex) {
      SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a z");
      String dateStr = format.format(Calendar.getInstance().getTime());
      System.out.println("<"+dateStr+"> <Warning> <MedRec Logging>  "+
         "Unable to get a reference to class name logger.  Will use "+
         "server logger: "+ex.getMessage());
      try {
        tmpLogger = Log4jLoggingHelper.getLog4jServerLogger();
        if (tmpLogger == null) {
          dateStr = format.format(Calendar.getInstance().getTime());
          System.out.println("<"+dateStr+"> <Notice> <MedRec Logging>  "+
               "Unable to get a reference to the non-server logger");
        }
      } catch (LoggerNotAvailableException e) {
        e.printStackTrace();
      }
    }
    return tmpLogger;
  }
}

package com.bea.medrec.actions;

import com.bea.medrec.beans.DiagnosticsBean;
import com.bea.medrec.utils.ClientException;
import com.bea.medrec.utils.MedRecLog4jFactory;
import java.lang.reflect.Field;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.management.MBeanServerConnection;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import weblogic.diagnostics.accessor.ColumnInfo;
import weblogic.diagnostics.accessor.LogTypes;
import weblogic.logging.Severities;
import javax.management.ObjectName;
import weblogic.management.runtime.WLDFDataAccessRuntimeMBean;
import weblogic.management.runtime.WLDFAccessRuntimeMBean;

/**
 * Handles retrieving and organizing diagnotics data.
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class DiagnosticsAction extends AdminBaseLookupDispatchAction {

  private static Logger logger =
      MedRecLog4jFactory.getLogger(DiagnosticsAction.class.getName());

  public ActionForward execute(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
    throws ClientException, Exception
  {
    logger.debug("method mapping="+
        request.getParameter(mapping.getParameter()));
    if (request.getParameter(mapping.getParameter()) == null) {
      return defaultMethod(mapping, form, request, response);
    } else
      return super.execute(mapping, form, request, response);
  }

 /**
  * <p>Determines method calling based on select submit button.</p>
  */
  protected Map getKeyMethodMap() {
    Map<String,String> map = new HashMap<String,String>();
    map.put("button.Logs", "getLogs");
    map.put("button.Cancel", "cancel");
    return map;
  }

 /**
  * <p>Default action method.</p>
  *
  * @return ActionForward
  */
  public ActionForward defaultMethod(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
    throws Exception
  {
    return loadDiagnosticsPage(mapping, form, request, response);
  }

  public ActionForward loadDiagnosticsPage(ActionMapping mapping,
                                           ActionForm form,
                                           HttpServletRequest request,
                                           HttpServletResponse response)
    throws ClientException, Exception
  {
    // local variables
    DiagnosticsBean diagnosticsBean = (DiagnosticsBean)form;
    if (diagnosticsBean == null) diagnosticsBean= new DiagnosticsBean();

    // create collection of log type options
    setLogNames(request, diagnosticsBean);

    // create list of time intervals
    setTimeSpan(diagnosticsBean);

    // create list of message severity
    setSeverity(diagnosticsBean);

    // Redirect to home view.
    return mapping.findForward("diagnostics");
  }

  private void setLogNames(HttpServletRequest request,
                           DiagnosticsBean diagnosticsBean)
      throws Exception {
    String[] names =
        getDiagntosicMBean(request).getAvailableDiagnosticDataAccessorNames();
    Arrays.sort(names);
    for (int i=0; i<names.length; i++) {
      String name = names[i];
      if (name.contains("pop_up_only"))
        continue;
      diagnosticsBean.addLogType(name, name);
    }
  }

  private void setTimeSpan(DiagnosticsBean diagnosticsBean)
      throws NamingException {
        diagnosticsBean.addTimeSpan("All", "0");
    Context initCtx = new InitialContext();
    int intervalSpan = ((Integer)initCtx.lookup(
        "java:comp/env/diagnostics/timeSpan/interval")).intValue();
    int numOfIntervals = ((Integer)initCtx.lookup(
        "java:comp/env/diagnostics/timeSpan/numOfintervals")).intValue();
    int tmpInterval = 0;
    for (int j=0; j<numOfIntervals; j++) {
      tmpInterval += intervalSpan;
      diagnosticsBean.addTimeSpan(String.valueOf(tmpInterval),
          String.valueOf(tmpInterval));
    }
  }

  private void setSeverity(DiagnosticsBean diagnosticsBean)
      throws IllegalAccessException {
    Field[] msgFields = null;
    diagnosticsBean.addMsgSeverity("All","All");
    msgFields = Severities.class.getFields();
    sortFields(msgFields);
    for (int k=0; k<msgFields.length; k++) {
      String name = msgFields[k].getName();
      if (!name.endsWith("_TEXT")) continue;
      diagnosticsBean.addMsgSeverity((String)msgFields[k].get(name),
          (String)msgFields[k].get(name));
    }
  }

  public ActionForward getLogs(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
    throws ClientException, Exception
  {
    // local variables
    DiagnosticsBean diagnosticsBean = (DiagnosticsBean)form;
    logger.debug(diagnosticsBean.toString());
    WLDFAccessRuntimeMBean daigRuntime = null;
    WLDFDataAccessRuntimeMBean accessor = null;
    ColumnInfo[] colInfo = null;
    String queryStr = "";
    long beginTime = 0;
    long endTime = System.currentTimeMillis();
    Iterator records = null;

    try {
      // get handle on data accessor
      daigRuntime = getDiagntosicMBean(request);
       accessor = daigRuntime.lookupWLDFDataAccessRuntime(
           diagnosticsBean.getLogType());
      logger.debug("Got WLDFDataAccessRuntimeMBean");
    } catch (Exception ex) {
      throwClientException(new Exception("Unable to obtain log for "+
          diagnosticsBean.getLogType()+".  Be sure that logging is properly "+
          "configured for this component."), mapping, "diagnostics.home");
    }

    // construct being and end time filtering
    if (diagnosticsBean.getTimeSpan() != null
        && !diagnosticsBean.getTimeSpan().equals("0")) {
      try {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR,
            Integer.parseInt(diagnosticsBean.getTimeSpan()));
        endTime = cal.getTimeInMillis();
        logger.debug("Calculated end time");
      } catch (NumberFormatException nfex) {
        throw new RuntimeException("Error formatting time bounds", nfex);
      }
    }

    // construct query string for msg severity
    if (isNotEmpty(diagnosticsBean.getMsgSeverity())
        && !diagnosticsBean.getMsgSeverity().equals("All")
        && (diagnosticsBean.getLogType().equals(LogTypes.SERVER_LOG)
            || diagnosticsBean.getLogType().equals(LogTypes.DOMAIN_LOG))) {
      queryStr += "SEVERITY = '"+diagnosticsBean.getMsgSeverity()+"'";
    }

    // construct query string for msg content search
    if (isNotEmpty(diagnosticsBean.getSearchCriteria())
        && (diagnosticsBean.getLogType().equals(LogTypes.SERVER_LOG)
            || diagnosticsBean.getLogType().equals(LogTypes.DOMAIN_LOG))) {
      if (queryStr.length() > 0)
        queryStr += " AND";
      queryStr += " MESSAGE LIKE '%"+diagnosticsBean.getSearchCriteria()+"%'";
    }
    logger.debug("Final query string: "+queryStr);

    // get column names and retrieve records
    colInfo = accessor.getColumns();
    records = accessor.retrieveDataRecords(beginTime, endTime, queryStr);

    ObjectName service = null;
    ObjectName serverRuntime = null;
    ObjectName wldfRuntime = null;
    ObjectName wldfAccessRuntime = null;
    ObjectName wldfDataAccessRuntime = null;
    String cursor = null;
    Object[] rows = null;

    MBeanServerConnection mbeanServerConnection = getMBeanServerConnection(
        request);
    service = new ObjectName("com.bea:Name=RuntimeService,"+
        "Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
    serverRuntime =  (ObjectName) mbeanServerConnection.getAttribute(service,
        "ServerRuntime");
    wldfRuntime = (ObjectName) mbeanServerConnection.getAttribute(serverRuntime,
        "WLDFRuntime");
    wldfAccessRuntime = (ObjectName) mbeanServerConnection.getAttribute(
        wldfRuntime, "WLDFAccessRuntime");

    wldfDataAccessRuntime = (ObjectName) mbeanServerConnection.invoke(
        wldfAccessRuntime, "lookupWLDFDataAccessRuntime",
        new Object[] { diagnosticsBean.getLogType() },
        new String[] {"java.lang.String"});

    logger.debug("Query str: "+queryStr);
    cursor = (String) mbeanServerConnection.invoke(wldfDataAccessRuntime,
        "openCursor",
        new Object[] { queryStr },
        new String[] {"java.lang.String"});

    rows = (Object[]) mbeanServerConnection.invoke(
        wldfDataAccessRuntime,
        "fetch",
        new Object[] {cursor},
        new String[] {"java.lang.String"});

    logger.debug("Found "+rows.length+" log entries:");
    Collection<Object> logResults = new ArrayList<Object>();

    for (int i=0; i<rows.length; i++) {
      StringBuffer strBuff = new StringBuffer();
      strBuff.append("Row "+i+1+": ");
      Object[] cols = (Object[]) rows[i];
      for (int j=0; j<cols.length; j++) {
        strBuff.append(" Index "+j+"="+cols[j]);
      }
      logger.debug(strBuff.toString());
    }
    logResults.add(rows);

    mbeanServerConnection.invoke(
        wldfDataAccessRuntime,
        "closeCursor",
        new Object[] {cursor},
        new String[] {"java.lang.String"});


    // place objects on request to be render by the view
    logger.debug("Preparing output");
    request.setAttribute(AdminConstants.LOG_TYPE,
        diagnosticsBean.getLogType());
    request.setAttribute(AdminConstants.LOG_COLUMNS, colInfo);
    request.setAttribute(AdminConstants.LOG_RECORDS, rows);

    // Redirect to home view.
    return mapping.findForward("logs");
  }

 /**
  * <p>User selected cancel button.</p>
  *
  * @return ActionForward
  */
  public ActionForward cancel(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    throws Exception
  {
    logger.info("Cancel log retrieval.");
    ActionForward forward = null;
    try {
      forward = mapping.findForward("home");
    }
    catch(Exception e) {
      return handleException(e, request, mapping);
    }
    return forward;
  }

  // sort view fields
  private void sortFields(Field[] fields) {
    Arrays.sort(fields,
        new Comparator() {
            public int compare(Object o1, Object o2) {
              String fname1 = ((Field)o1).getName();
              String fname2 = ((Field)o2).getName();
              int n1=fname1.length(), n2=fname2.length();
              for (int i1=0, i2=0; i1<n1 && i2<n2; i1++, i2++) {
                  char c1 = fname1.charAt(i1);
                  char c2 = fname2.charAt(i2);
                  if (c1 != c2) {
                      c1 = Character.toUpperCase(c1);
                      c2 = Character.toUpperCase(c2);
                      if (c1 != c2) {
                          c1 = Character.toLowerCase(c1);
                          c2 = Character.toLowerCase(c2);
                          if (c1 != c2) {
                              return c1 - c2;
                          }
                      }
                  }
                }
                return n1 - n2;
              }
          });
  }
}

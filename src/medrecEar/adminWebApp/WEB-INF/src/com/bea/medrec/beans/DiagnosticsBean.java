package com.bea.medrec.beans;

import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * <p>Form bean for the
 * </p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class DiagnosticsBean extends BaseBean {

  ArrayList<Object> logTypes = new ArrayList<Object>();
  ArrayList<Object> timeSpans = new ArrayList<Object>();
  ArrayList<Object> msgSeverities = new ArrayList<Object>();
  String logType = "";
  String searchCriteria = "";
  String timeSpan = "";
  String msgSeverity = "";

  public DiagnosticsBean() { }

  public class OptionType { // inner bean for represention option items
    private String label = null;
    private String value = null;

    public OptionType(String label, String value) {
      this.label = label;
      this.value = value;
    }

    public String getOptionLabel() {
      return label;
    }

    public String getOptionValue() {
      return value;
    }
  } // end inner class

  // getters
  public Collection getLogTypes() {
    return logTypes;
  }

  public String getLogType() {
    return logType;
  }

  public String getSearchCriteria() {
    return searchCriteria;
  }

  public String getTimeSpan() {
    return timeSpan;
  }

  public Collection getTimeSpans() {
    return timeSpans;
  }

  public String getMsgSeverity() {
    return msgSeverity;
  }

  public Collection getMsgSeverities() {
    return msgSeverities;
  }

  // setters
  public void setLogType(String logType) {
    this.logType = logType;
  }

  public void setSearchCriteria(String searchCriteria) {
    this.searchCriteria = searchCriteria;
  }

  public void setTimeSpan(String timeSpan) {
    this.timeSpan = timeSpan;
  }

  public void setMsgSeverity(String msgSeverity) {
    this.msgSeverity = msgSeverity;
  }

  public void addLogType(String label, String value) {
    logTypes.add(new OptionType(label, value));
  }

/*  public void addTimeSpan(LabelValueBean label) {
    timeSpans.add(label);
  }
*/

  public void addTimeSpan(String label, String value) {
    timeSpans.add(new OptionType(label, value));
  }

  public void addMsgSeverity(String label, String value) {
    msgSeverities.add(new OptionType(label, value));
  }

  /**
   * <p>Validate registration.</p>
   *
   * @param mapping
   * @param request
   * @return ActionErrors
   */
  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {
    return super.validate(mapping, request);
  }

  public String toString() {
    StringBuffer str = new StringBuffer();
    str.append("Diagnostic[LogType: "+logType);
    str.append(" | MsgSeverity: "+msgSeverity);
    str.append(" | TimeSpan (hrs): "+timeSpan);
    str.append(" | Search Criteria: "+searchCriteria);
    str.append("]");

    return str.toString();
  }

}

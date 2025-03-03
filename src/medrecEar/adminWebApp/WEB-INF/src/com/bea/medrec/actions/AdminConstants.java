package com.bea.medrec.actions;

/**
 * <p>Admin web application constants.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public interface AdminConstants {
  // Security
  public static final String MEDREC_ADMIN_GROUP = "MedRecAdmins";
  public static final String MEDREC_ADMIN_ROLE = "MedRecAdmin";

  // Beans
  public static final String ADMIN_BEAN = "adminBean";
  public static final String ERROR_BEAN = "errorBean";
  public static final String IMPORT_BEANS = "importBeans";
  public static final String PATIENT_BEAN = "patientBean";

  // Webapp
  public static final String ACTION = "action";

  // Buttons
  public static final String BTN_DENY = "Deny";
  public static final String BTN_APPROVE = "Approve";
  public static final String BTN_CANCEL = "Cancel";
  public static final String BTN_LOGIN = "Login";

  // XML Upload
  public static final String XML_UPLOAD = "upload";
  public static final String XML_FILE = "file";

  // Diagnostics
  public static final String LOG_TYPE = "logType";
  public static final String LOG_COLUMNS = "colInfo";
  public static final String LOG_RECORDS = "records";
  public static final String EVENTS_DATA_ARCHIVE = "EventsDataArchive";
}

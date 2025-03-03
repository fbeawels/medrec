package com.bea.medrec.actions;

/**
 * <p>Patient web application constants.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public interface PatientConstants {
  // Security
  public static final String PATIENT_GROUP = "MedRecPatients";
  public static final String PATIENT_ROLE = "MedRecPatient";

  // Session and request keys
  public static final String ERROR_BEAN = "errorBean";
  public static final String PATIENT_BEAN = "patientBean";
  public static final String PATIENT_COLLECTION = "patientCollection";
  public static final String PREVIOUS_SEARCH = "previousSearch";
  public static final String RECORD_BEAN = "recordBean";
  public static final String RECORD_BEANS = "recordBeans";
  public static final String RECORD_COLLECTION = "recordCollection";
  public static final String PRESCRIPTION_COLLECTION = "prescriptionCollection";
  public static final String PRESCRIPTION_BEANS = "prescriptionBeans";
  public static final String USER_BEAN = "userBean";

  // Webapp
  public static final String ACTION = "action";

  // Buttons
  public static final String BTN_CANCEL = "Cancel";
  public static final String BTN_LOGIN = "Login";
  public static final String BTN_REGISTER = "Register";
  public static final String BTN_SAVE = "Save";
}

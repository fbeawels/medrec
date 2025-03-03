package com.bea.medrec.actions;

/**
 * Physician web application constants.
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public interface PhysicianConstants {
  // Security
  public static final String MEDREC_ADMIN_GROUP = "MedRecPhysicians";
  public static final String MEDREC_ADMIN_ROLE = "MedRecPhysician";

  // Session and request keys
  public static final String ERROR_BEAN = "errorBean";
  public static final String PATIENT_BEAN = "patientBean";
  public static final String PATIENT_BEANS = "patientCollection";
  public static final String PHYSICIAN_BEAN = "physicianBean";
  public static final String PREVIOUS_SEARCH = "previousSearch";
  public static final String RECORD_BEAN = "recordBean";
  public static final String RECORD_BEANS = "recordCollection";
  public static final String PRESCRIPTION_BEANS = "prescriptionCollection";
  public static final String USER_BEAN = "userBean";

  // Webapp
  public static final String ACTION = "action";

  // Action and query param names
  public static final String NEW_VISIT = "new";
  public static final String DELETE_PRESCRIPTION = "deleteprescription";
  public static final String PRESCRIPTION_ID = "prescriptionid";
}

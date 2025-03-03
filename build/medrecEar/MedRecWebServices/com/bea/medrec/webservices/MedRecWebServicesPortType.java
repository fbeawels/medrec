package com.bea.medrec.webservices;

/**
 * Generated interface, please do not edit.
 * Date: [Wed Apr 04 12:49:02 PDT 2007]
 */

public interface MedRecWebServicesPortType extends java.rmi.Remote {

  /**
   * Web Method: findPatientByLastNameWild ...
   */
  com.bea.medrec.value.Patient[] findPatientByLastNameWild(java.lang.String pLastname)
      throws java.rmi.RemoteException, java.lang.Exception;
  /**
   * Web Method: getRecord ...
   */
  com.bea.medrec.value.Record getRecord(java.lang.Integer pRecordId)
      throws java.rmi.RemoteException, java.lang.Exception;
  /**
   * Web Method: findPatientBySsn ...
   */
  com.bea.medrec.value.Patient findPatientBySsn(java.lang.String pPatientSSN)
      throws java.rmi.RemoteException, java.lang.Exception;
  /**
   * Web Method: getRecordsSummary ...
   */
  com.bea.medrec.value.RecordsSummary getRecordsSummary(java.lang.Integer pPatientId)
      throws java.rmi.RemoteException, java.lang.Exception;
  /**
   * Web Method: updatePatient ...
   */
  com.bea.medrec.value.Patient updatePatient(com.bea.medrec.value.Patient pPatientVO)
      throws java.rmi.RemoteException, java.lang.Exception;
}

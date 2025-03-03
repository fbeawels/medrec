package com.bea.medrec.webservices;

/**
 * Generated interface, please do not edit.
 * Date: [Wed Apr 04 12:49:09 PDT 2007]
 */

public interface MedRecRMWebServicesPortType extends java.rmi.Remote {

  /**
   * Web Method: registerPatient ...
   */
  com.bea.medrec.value.Patient registerPatient(com.bea.medrec.value.Patient pPatientVO,java.lang.String pPassword)
      throws java.rmi.RemoteException, java.lang.Exception;
  /**
   * Web Method: addRecord ...
   */
  com.bea.medrec.value.Record addRecord(com.bea.medrec.value.Record pRecordVO)
      throws java.rmi.RemoteException, java.lang.Exception;
  /**
   * Web Method: updatePatient ...
   */
  com.bea.medrec.value.Patient updatePatient(com.bea.medrec.value.Patient pPatientVO)
      throws java.rmi.RemoteException, java.lang.Exception;
}

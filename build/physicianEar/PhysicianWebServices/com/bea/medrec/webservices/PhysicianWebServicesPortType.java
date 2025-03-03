package com.bea.medrec.webservices;

/**
 * Generated interface, please do not edit.
 * Date: [Wed Apr 04 12:49:50 PDT 2007]
 */

public interface PhysicianWebServicesPortType extends java.rmi.Remote {

  /**
   * Web Method: addRecord ...
   */
  void addRecord(java.lang.String wsServiceUrl,com.bea.medrec.value.Record pRecordVO)
      throws java.rmi.RemoteException, java.lang.Exception;
}

package com.bea.medrec.webservices;

/**
 * Generated class, do not edit.
 *
 * This service interface was generated by weblogic
 * webservice stub gen on Wed Apr 04 12:49:54 PDT 2007  
 */

public interface MedRecWebServices extends javax.xml.rpc.Service {

  weblogic.wsee.context.WebServiceContext context();

  weblogic.wsee.context.WebServiceContext joinContext()
       throws weblogic.wsee.context.ContextNotFoundException;
 
     

     //***********************************
     // Port Name: MedRecWebServicesPort  
     // Port Type: MedRecWebServicesPortType   
     //***********************************
     
  /**
   * returns MedRecWebServicesPort port in this service 
   */
  com.bea.medrec.webservices.MedRecWebServicesPortType getMedRecWebServicesPort() throws javax.xml.rpc.ServiceException;

  /**
   * @deprecated Use getMedRecWebServicesPort(byte[] username, byte[] password);
   */
  com.bea.medrec.webservices.MedRecWebServicesPortType getMedRecWebServicesPort(String username, String password) throws javax.xml.rpc.ServiceException;

  com.bea.medrec.webservices.MedRecWebServicesPortType getMedRecWebServicesPort(byte[] username, byte[] password) throws javax.xml.rpc.ServiceException;
  
    
  }
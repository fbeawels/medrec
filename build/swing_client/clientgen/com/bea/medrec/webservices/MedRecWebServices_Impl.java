package com.bea.medrec.webservices;

/**
 * Generated class, do not edit.
 *
 * This service class was generated by weblogic
 * webservice stub gen on Wed Apr 04 12:49:54 PDT 2007 */

public class MedRecWebServices_Impl     extends weblogic.wsee.jaxrpc.ServiceImpl
     implements com.bea.medrec.webservices.MedRecWebServices {

  public MedRecWebServices_Impl() throws javax.xml.rpc.ServiceException {
    this("com/bea/medrec/webservices/MedRecWebServices_saved_wsdl.wsdl", null);
  }
  
  public MedRecWebServices_Impl(String wsdlurl) throws javax.xml.rpc.ServiceException {
    this(wsdlurl, null);
  }
  
  public MedRecWebServices_Impl(String wsdlurl, weblogic.wsee.connection.transport.TransportInfo transportInfo) throws javax.xml.rpc.ServiceException {
    super(wsdlurl,
          new javax.xml.namespace.QName("http://www.bea.com/medrec", "MedRecWebServices"),
          "com/bea/medrec/webservices/MedRecWebServices_internaldd.xml", transportInfo);
  }
  
  //***********************************
  // Port Name: MedRecWebServicesPort 
  // Port Type: MedRecWebServicesPortType 
  //***********************************

  com.bea.medrec.webservices.MedRecWebServicesPortType mvar_MedRecWebServicesPort;

  /**
   * returns MedRecWebServicesPort port in this service 
   */
  public com.bea.medrec.webservices.MedRecWebServicesPortType getMedRecWebServicesPort() 
    throws javax.xml.rpc.ServiceException
  {

    if (mvar_MedRecWebServicesPort == null) {
      mvar_MedRecWebServicesPort =
        new com.bea.medrec.webservices.MedRecWebServicesPortType_Stub(_getPort("MedRecWebServicesPort"), this);
    }

    if (transportInfo != null && 
        transportInfo instanceof weblogic.wsee.connection.transport.http.HttpTransportInfo) {
      ((javax.xml.rpc.Stub)mvar_MedRecWebServicesPort)._setProperty(
        "weblogic.wsee.connection.transportinfo", 
        (weblogic.wsee.connection.transport.http.HttpTransportInfo)transportInfo);
    }
    
    return mvar_MedRecWebServicesPort;
  }

  /**
   * @deprecated  Use getMedRecWebServicesPort(byte[] username, byte[] password)
   */
  public com.bea.medrec.webservices.MedRecWebServicesPortType getMedRecWebServicesPort(String username, String password) 
    throws javax.xml.rpc.ServiceException
  {
    if (username != null & password != null) {
      weblogic.wsee.connection.transport.http.HttpTransportInfo httpInfo = 
        new weblogic.wsee.connection.transport.http.HttpTransportInfo();
      httpInfo.setUsername(username.getBytes());
      httpInfo.setPassword(password.getBytes());
      transportInfo = httpInfo;
    }
    return getMedRecWebServicesPort();
  }

  public com.bea.medrec.webservices.MedRecWebServicesPortType getMedRecWebServicesPort(byte[] username, byte[] password) 
    throws javax.xml.rpc.ServiceException
  {
    if (username != null & password != null) {
      weblogic.wsee.connection.transport.http.HttpTransportInfo httpInfo = 
        new weblogic.wsee.connection.transport.http.HttpTransportInfo();
      httpInfo.setUsername(username);
      httpInfo.setPassword(password);
      transportInfo = httpInfo;
    }
    return getMedRecWebServicesPort();
  }
  
  
}
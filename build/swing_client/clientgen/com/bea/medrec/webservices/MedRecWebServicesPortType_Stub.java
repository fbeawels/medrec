package com.bea.medrec.webservices;

/**
 * Generated class, do not edit.
 *
 * This stub class was generated by weblogic
 * webservice stub gen on Wed Apr 04 12:49:54 PDT 2007 
 */

public class MedRecWebServicesPortType_Stub 
     extends weblogic.wsee.jaxrpc.StubImpl
     implements com.bea.medrec.webservices.MedRecWebServicesPortType, /**/java.io.Serializable
{

  public MedRecWebServicesPortType_Stub(weblogic.wsee.wsdl.WsdlPort port, 
                        weblogic.wsee.jaxrpc.ServiceImpl service) 
    throws javax.xml.rpc.ServiceException
  {
    super(port, com.bea.medrec.webservices.MedRecWebServicesPortType.class, service);
  }
  
    
  /**
   * Operation Name : {http://www.bea.com/medrec}findPatientByLastNameWild   */

  public com.bea.medrec.ArrayOfPatient_Literal findPatientByLastNameWild(java.lang.String pLastname) throws java.rmi.RemoteException {

    java.util.ArrayList _args = new java.util.ArrayList();
    _args.add(pLastname);
    
    try {
            java.lang.Object _result = _invoke("findPatientByLastNameWild", _args.toArray());
      return (com.bea.medrec.ArrayOfPatient_Literal)_result;
    } catch (javax.xml.rpc.JAXRPCException _wls_exception) {
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception.getLinkedCause());
    } catch (javax.xml.rpc.soap.SOAPFaultException _wls_exception) {
      throw new java.rmi.RemoteException(_soapFault2String(_wls_exception), _wls_exception);
    } catch (java.lang.Throwable _wls_exception) {

      
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception);
    }
  }  public void findPatientByLastNameWildAsync(weblogic.wsee.async.AsyncPreCallContext apc, java.lang.String pLastname) throws java.rmi.RemoteException  {
    java.util.ArrayList _args = new java.util.ArrayList();

    _setProperty(weblogic.wsee.async.AsyncConstants.ASYNC_INVOKE_PROPERTY, "true");
    _setProperty(weblogic.wsee.async.AsyncConstants.ASYNC_PRE_CALL_CONTEXT_PROPERTY, apc);
    _setProperty(weblogic.wsee.async.AsyncConstants.METHOD_NAME_PROPERTY, "findPatientByLastNameWild");
    _setProperty(weblogic.wsee.async.AsyncConstants.OPERATION_NAME_PROPERTY,
                 "findPatientByLastNameWild");
    _setProperty(weblogic.wsee.async.AsyncConstants.RETURN_TYPE_PROPERTY, "com.bea.medrec.ArrayOfPatient_Literal");
    _args.add(pLastname);
    
    try {
      java.lang.Object _result = _invoke("findPatientByLastNameWild", _args.toArray());
    } catch (javax.xml.rpc.JAXRPCException _wls_exception) {
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception.getLinkedCause());
    } catch (javax.xml.rpc.soap.SOAPFaultException _wls_exception) {
      throw new java.rmi.RemoteException(_soapFault2String(_wls_exception), _wls_exception);
    } catch (java.lang.Throwable _wls_exception) {
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception);
    }
    finally {
      _removeProperty(weblogic.wsee.async.AsyncConstants.ASYNC_INVOKE_PROPERTY);
      _removeProperty(weblogic.wsee.async.AsyncConstants.ASYNC_PRE_CALL_CONTEXT_PROPERTY);
      _removeProperty(weblogic.wsee.async.AsyncConstants.METHOD_NAME_PROPERTY);
      _removeProperty(weblogic.wsee.async.AsyncConstants.RETURN_TYPE_PROPERTY);

    }
  }
   
        
  /**
   * Operation Name : {http://www.bea.com/medrec}getRecord   */

  public com.bea.medrec.value.Record getRecord(java.lang.Integer pRecordId) throws java.rmi.RemoteException {

    java.util.ArrayList _args = new java.util.ArrayList();
    _args.add(pRecordId);
    
    try {
            java.lang.Object _result = _invoke("getRecord", _args.toArray());
      return (com.bea.medrec.value.Record)_result;
    } catch (javax.xml.rpc.JAXRPCException _wls_exception) {
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception.getLinkedCause());
    } catch (javax.xml.rpc.soap.SOAPFaultException _wls_exception) {
      throw new java.rmi.RemoteException(_soapFault2String(_wls_exception), _wls_exception);
    } catch (java.lang.Throwable _wls_exception) {

      
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception);
    }
  }  public void getRecordAsync(weblogic.wsee.async.AsyncPreCallContext apc, java.lang.Integer pRecordId) throws java.rmi.RemoteException  {
    java.util.ArrayList _args = new java.util.ArrayList();

    _setProperty(weblogic.wsee.async.AsyncConstants.ASYNC_INVOKE_PROPERTY, "true");
    _setProperty(weblogic.wsee.async.AsyncConstants.ASYNC_PRE_CALL_CONTEXT_PROPERTY, apc);
    _setProperty(weblogic.wsee.async.AsyncConstants.METHOD_NAME_PROPERTY, "getRecord");
    _setProperty(weblogic.wsee.async.AsyncConstants.OPERATION_NAME_PROPERTY,
                 "getRecord");
    _setProperty(weblogic.wsee.async.AsyncConstants.RETURN_TYPE_PROPERTY, "com.bea.medrec.value.Record");
    _args.add(pRecordId);
    
    try {
      java.lang.Object _result = _invoke("getRecord", _args.toArray());
    } catch (javax.xml.rpc.JAXRPCException _wls_exception) {
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception.getLinkedCause());
    } catch (javax.xml.rpc.soap.SOAPFaultException _wls_exception) {
      throw new java.rmi.RemoteException(_soapFault2String(_wls_exception), _wls_exception);
    } catch (java.lang.Throwable _wls_exception) {
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception);
    }
    finally {
      _removeProperty(weblogic.wsee.async.AsyncConstants.ASYNC_INVOKE_PROPERTY);
      _removeProperty(weblogic.wsee.async.AsyncConstants.ASYNC_PRE_CALL_CONTEXT_PROPERTY);
      _removeProperty(weblogic.wsee.async.AsyncConstants.METHOD_NAME_PROPERTY);
      _removeProperty(weblogic.wsee.async.AsyncConstants.RETURN_TYPE_PROPERTY);

    }
  }
   
        
  /**
   * Operation Name : {http://www.bea.com/medrec}findPatientBySsn   */

  public com.bea.medrec.value.Patient findPatientBySsn(java.lang.String pPatientSSN) throws java.rmi.RemoteException {

    java.util.ArrayList _args = new java.util.ArrayList();
    _args.add(pPatientSSN);
    
    try {
            java.lang.Object _result = _invoke("findPatientBySsn", _args.toArray());
      return (com.bea.medrec.value.Patient)_result;
    } catch (javax.xml.rpc.JAXRPCException _wls_exception) {
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception.getLinkedCause());
    } catch (javax.xml.rpc.soap.SOAPFaultException _wls_exception) {
      throw new java.rmi.RemoteException(_soapFault2String(_wls_exception), _wls_exception);
    } catch (java.lang.Throwable _wls_exception) {

      
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception);
    }
  }  public void findPatientBySsnAsync(weblogic.wsee.async.AsyncPreCallContext apc, java.lang.String pPatientSSN) throws java.rmi.RemoteException  {
    java.util.ArrayList _args = new java.util.ArrayList();

    _setProperty(weblogic.wsee.async.AsyncConstants.ASYNC_INVOKE_PROPERTY, "true");
    _setProperty(weblogic.wsee.async.AsyncConstants.ASYNC_PRE_CALL_CONTEXT_PROPERTY, apc);
    _setProperty(weblogic.wsee.async.AsyncConstants.METHOD_NAME_PROPERTY, "findPatientBySsn");
    _setProperty(weblogic.wsee.async.AsyncConstants.OPERATION_NAME_PROPERTY,
                 "findPatientBySsn");
    _setProperty(weblogic.wsee.async.AsyncConstants.RETURN_TYPE_PROPERTY, "com.bea.medrec.value.Patient");
    _args.add(pPatientSSN);
    
    try {
      java.lang.Object _result = _invoke("findPatientBySsn", _args.toArray());
    } catch (javax.xml.rpc.JAXRPCException _wls_exception) {
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception.getLinkedCause());
    } catch (javax.xml.rpc.soap.SOAPFaultException _wls_exception) {
      throw new java.rmi.RemoteException(_soapFault2String(_wls_exception), _wls_exception);
    } catch (java.lang.Throwable _wls_exception) {
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception);
    }
    finally {
      _removeProperty(weblogic.wsee.async.AsyncConstants.ASYNC_INVOKE_PROPERTY);
      _removeProperty(weblogic.wsee.async.AsyncConstants.ASYNC_PRE_CALL_CONTEXT_PROPERTY);
      _removeProperty(weblogic.wsee.async.AsyncConstants.METHOD_NAME_PROPERTY);
      _removeProperty(weblogic.wsee.async.AsyncConstants.RETURN_TYPE_PROPERTY);

    }
  }
   
        
  /**
   * Operation Name : {http://www.bea.com/medrec}getRecordsSummary   */

  public com.bea.medrec.value.RecordsSummary getRecordsSummary(java.lang.Integer pPatientId) throws java.rmi.RemoteException {

    java.util.ArrayList _args = new java.util.ArrayList();
    _args.add(pPatientId);
    
    try {
            java.lang.Object _result = _invoke("getRecordsSummary", _args.toArray());
      return (com.bea.medrec.value.RecordsSummary)_result;
    } catch (javax.xml.rpc.JAXRPCException _wls_exception) {
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception.getLinkedCause());
    } catch (javax.xml.rpc.soap.SOAPFaultException _wls_exception) {
      throw new java.rmi.RemoteException(_soapFault2String(_wls_exception), _wls_exception);
    } catch (java.lang.Throwable _wls_exception) {

      
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception);
    }
  }  public void getRecordsSummaryAsync(weblogic.wsee.async.AsyncPreCallContext apc, java.lang.Integer pPatientId) throws java.rmi.RemoteException  {
    java.util.ArrayList _args = new java.util.ArrayList();

    _setProperty(weblogic.wsee.async.AsyncConstants.ASYNC_INVOKE_PROPERTY, "true");
    _setProperty(weblogic.wsee.async.AsyncConstants.ASYNC_PRE_CALL_CONTEXT_PROPERTY, apc);
    _setProperty(weblogic.wsee.async.AsyncConstants.METHOD_NAME_PROPERTY, "getRecordsSummary");
    _setProperty(weblogic.wsee.async.AsyncConstants.OPERATION_NAME_PROPERTY,
                 "getRecordsSummary");
    _setProperty(weblogic.wsee.async.AsyncConstants.RETURN_TYPE_PROPERTY, "com.bea.medrec.value.RecordsSummary");
    _args.add(pPatientId);
    
    try {
      java.lang.Object _result = _invoke("getRecordsSummary", _args.toArray());
    } catch (javax.xml.rpc.JAXRPCException _wls_exception) {
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception.getLinkedCause());
    } catch (javax.xml.rpc.soap.SOAPFaultException _wls_exception) {
      throw new java.rmi.RemoteException(_soapFault2String(_wls_exception), _wls_exception);
    } catch (java.lang.Throwable _wls_exception) {
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception);
    }
    finally {
      _removeProperty(weblogic.wsee.async.AsyncConstants.ASYNC_INVOKE_PROPERTY);
      _removeProperty(weblogic.wsee.async.AsyncConstants.ASYNC_PRE_CALL_CONTEXT_PROPERTY);
      _removeProperty(weblogic.wsee.async.AsyncConstants.METHOD_NAME_PROPERTY);
      _removeProperty(weblogic.wsee.async.AsyncConstants.RETURN_TYPE_PROPERTY);

    }
  }
   
        
  /**
   * Operation Name : {http://www.bea.com/medrec}updatePatient   */

  public com.bea.medrec.value.Patient updatePatient(com.bea.medrec.value.Patient pPatientVO) throws java.rmi.RemoteException {

    java.util.ArrayList _args = new java.util.ArrayList();
    _args.add(pPatientVO);
    
    try {
            java.lang.Object _result = _invoke("updatePatient", _args.toArray());
      return (com.bea.medrec.value.Patient)_result;
    } catch (javax.xml.rpc.JAXRPCException _wls_exception) {
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception.getLinkedCause());
    } catch (javax.xml.rpc.soap.SOAPFaultException _wls_exception) {
      throw new java.rmi.RemoteException(_soapFault2String(_wls_exception), _wls_exception);
    } catch (java.lang.Throwable _wls_exception) {

      
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception);
    }
  }  public void updatePatientAsync(weblogic.wsee.async.AsyncPreCallContext apc, com.bea.medrec.value.Patient pPatientVO) throws java.rmi.RemoteException  {
    java.util.ArrayList _args = new java.util.ArrayList();

    _setProperty(weblogic.wsee.async.AsyncConstants.ASYNC_INVOKE_PROPERTY, "true");
    _setProperty(weblogic.wsee.async.AsyncConstants.ASYNC_PRE_CALL_CONTEXT_PROPERTY, apc);
    _setProperty(weblogic.wsee.async.AsyncConstants.METHOD_NAME_PROPERTY, "updatePatient");
    _setProperty(weblogic.wsee.async.AsyncConstants.OPERATION_NAME_PROPERTY,
                 "updatePatient");
    _setProperty(weblogic.wsee.async.AsyncConstants.RETURN_TYPE_PROPERTY, "com.bea.medrec.value.Patient");
    _args.add(pPatientVO);
    
    try {
      java.lang.Object _result = _invoke("updatePatient", _args.toArray());
    } catch (javax.xml.rpc.JAXRPCException _wls_exception) {
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception.getLinkedCause());
    } catch (javax.xml.rpc.soap.SOAPFaultException _wls_exception) {
      throw new java.rmi.RemoteException(_soapFault2String(_wls_exception), _wls_exception);
    } catch (java.lang.Throwable _wls_exception) {
      throw new java.rmi.RemoteException(_wls_exception.getMessage(), _wls_exception);
    }
    finally {
      _removeProperty(weblogic.wsee.async.AsyncConstants.ASYNC_INVOKE_PROPERTY);
      _removeProperty(weblogic.wsee.async.AsyncConstants.ASYNC_PRE_CALL_CONTEXT_PROPERTY);
      _removeProperty(weblogic.wsee.async.AsyncConstants.METHOD_NAME_PROPERTY);
      _removeProperty(weblogic.wsee.async.AsyncConstants.RETURN_TYPE_PROPERTY);

    }
  }
   
    
  private Object writeReplace() throws java.io.ObjectStreamException {
    java.util.HashMap map = new java.util.HashMap();
            map.put("MedRecWebServicesPort", "MedRecWebServicesPort");
            
    return new weblogic.wsee.tools.clientgen.stubgen.StubSerialWrapper(
      "com.bea.medrec.webservices.MedRecWebServices",
      (String)map.get(_getPortName()),
      _getUserProperties());
  }
}
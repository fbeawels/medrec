package com.bea.medrec.webservices;

import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.value.Record;
import com.bea.medrec.webservices.client.MedRecRMWebServicesPortType;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.rpc.Stub;
import org.apache.log4j.Logger;
import weblogic.jws.ServiceClient;
import weblogic.jws.WLHttpTransport;
import weblogic.wsee.async.AsyncCallContextFactory;
import weblogic.wsee.async.AsyncPostCallContext;
import weblogic.wsee.async.AsyncPreCallContext;


// Standard JWS annotation that specifies that the name of the Web Service is
// "MedRecRMWebServices", its public service name is "MedRecRMWebServices", and the
// targetNamespace used in the generated WSDL is "http://www.bea.com/medrec"
@WebService(name="PhysicianWebServicesPortType",
            serviceName="PhysicianWebServices",
            targetNamespace="http://www.bea.com/medrec")

// Standard JWS annotation that specifies this is a document-literal-wrapped
// Web Service  REVIEWME - review comment
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,
             use=SOAPBinding.Use.LITERAL,
             parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

// WebLogic-specific JWS annotation that specifies the port name is
// "PhysicianWebServices", and the context path and service URI used to build
// the URI of the Web Service is "ws_phys/PhysicianWebServices"
@WLHttpTransport(portName="PhysicianWebServicesPort",
                 contextPath="ws_phys",
                 serviceUri="PhysicianWebServices")

/**
 * <p>Physician Web Service that asynchronously invokes the MedRec's reliable
 *    asynchronous webservice.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class PhysicianWebServices {

  private static Logger logger =
      MedRecLog4jFactory.getLogger(PhysicianWebServices.class.getName());

  // FIXME - need comment here
  @ServiceClient(serviceName = "MedRecRMWebServices")
  private MedRecRMWebServicesPortType port;

 /**
  * <p>Reliable and asynchronously transmits new record to MedRec.</p>
  *
  * @param wsServiceUrl
  * @param pRecordVO
  */
  @WebMethod
  public void addRecord(String wsServiceUrl, Record pRecordVO) throws Exception {
    logger.info("Sending record to MedRec...");
    logger.debug("Service end point: "+wsServiceUrl);
    logger.debug("Record: "+pRecordVO.toString());
    if (wsServiceUrl == null || wsServiceUrl.equals(""))
      throw new Exception("Service end point is null.");
    AsyncPreCallContext apc = AsyncCallContextFactory.getAsyncPreCallContext();
    apc.setProperty("record", pRecordVO);
    Stub stub = (Stub)port;
    stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, wsServiceUrl);
    port.addRecordAsync(apc, pRecordVO);
 }

 /**
  * <p>Method called once MedRec processes the new record.</p>
  *
  * @param apc
  * @param pRecordVO
  */
  public void onAddRecordAsyncResponse(AsyncPostCallContext apc,
                                       Record pRecordVO) {
    logger.info("WS-RM async response: "+pRecordVO.toString());
  }

 /**
  * <p>Method called once MedRec processes the new record.</p>
  *
  * @param apc
  * @param t
  */
  public void onAddRecordAsyncFailure(AsyncPostCallContext apc, Throwable t) {
    logger.error("Failed to transmit patient record: ");
    t.printStackTrace();
  }
}

package com.bea.medrec.webservices;

import com.bea.medrec.controller.PatientSession;
import com.bea.medrec.controller.RecordSession;
import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.value.Patient;
import com.bea.medrec.value.Record;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import org.apache.log4j.Logger;
import weblogic.jws.AsyncResponseBean;
import weblogic.jws.Policy;
import weblogic.jws.WLHttpTransport;

// Standard JWS annotation that specifies that the name of the Web Service is
// "MedRecRMWebServices", its public service name is "MedRecRMWebServices", and the
// targetNamespace used in the generated WSDL is "http://www.bea.com/medrec"
@WebService(name = "MedRecRMWebServicesPortType",
    serviceName = "MedRecRMWebServices",
    targetNamespace = "http://www.bea.com/medrec")

// Standard JWS annotation that specifies this is a document-literal-wrapped
// Web Service
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,
             use=SOAPBinding.Use.LITERAL,
             parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

// WebLogic Web Services use WS-Policy files to enable a destination endpoint
// to describe and advertise its reliable messaging capabilities and requirements.
// The WS-Policy specification provides a general purpose model and syntax to
// describe and communicate the policies of a Web service.
// These WS-Policy files are XML files that describe features such as the
// version of the WS-ReliableMessaging specification that is supported, the
// source endpoint's retransmission interval, the destination endpoint's
// acknowledgment interval, and so on.  REVIEWME - review comment
@Policy(uri="MedRecRMServicePolicy.xml", attachToWsdl=true)

// FIXME - need comment here
@AsyncResponseBean()

// WebLogic-specific JWS annotation that specifies the port name is
// "MedRecRMWebServices", and the context path and service URI used to build
// the URI of the Web Service is "ws_rm_medrec/MedRecWebServices"
@WLHttpTransport(portName = "MedRecRMWebServicesPort",
    contextPath = "ws_rm_medrec",
    serviceUri = "MedRecRMWebServices")

/**
 * <p>MedRecWebServices provides an interface for all MedRec Web services.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class MedRecRMWebServices extends MedRecBaseWebServices {
  private static Logger logger =
      MedRecLog4jFactory.getLogger(MedRecRMWebServices.class.getName());

  //  A D D   R E C O R D
  /**
   * <p>Accesses MedRec Web service adding a record, including
   * vital signs and prescriptions.</p>
   *
   * @param pRecordVO
   * @return Record
   */
  @WebMethod()
  public Record addRecord(Record pRecordVO) throws Exception {
    logger.info("Adding record.");
    logger.debug(pRecordVO.toString());

    // Declare local variables.
    RecordSession recordSession = null;
    Record newRecord = null;

    try {
      recordSession = getRecordSession();
      newRecord = recordSession.addRecord(pRecordVO);
    } catch (Exception e) {
      logger.error(e);
      throw e;
    }

    return newRecord;
  }

  //  R E G I S T E R   P A T I E N T
  /**
   * <p>Accesses MedRec Web service adding a new active patient.</p>
   *
   * @param pPatientVO
   * @param pPassword
   * @return Patient
   */
  @WebMethod()
  public Patient registerPatient(Patient pPatientVO,
                                 String pPassword)
    throws Exception {
    logger.info("Adding patient.");
    logger.debug(pPatientVO.toString());

    // Declare local variables.
    PatientSession patientSession = null;
    Patient newPatient = null;

    try {
      patientSession = getPatientSession();
      if (patientSession.findPatientByEmail(pPatientVO.getEmail()) != null) {
        throw new Exception("User "+pPatientVO.getEmail()+" already exists.");
      }
      logger.debug("Creating new account for patient.");
      newPatient = patientSession.processActiveRegistration(pPatientVO,
          pPassword);
    } catch (Exception e) {
      logger.error(e);
      throw e;
    }

    return newPatient;
  }

  //  U P D A T E   P A T I E N T
  /**
   * <p>Accesses MedRec Web service to update patient info.</p>
   *
   * @param pPatientVO
   * @return Patient
   */
  @WebMethod()
  public Patient updatePatient(Patient pPatientVO) throws Exception {
    logger.info("Updating patient.");
    logger.debug(pPatientVO.toString());

    // Declare local variables.
    PatientSession patientSession = null;
    Patient updatedPatient = null;

    try {
      patientSession = getPatientSession();
      updatedPatient = patientSession.updatePatient(pPatientVO);
    } catch (Exception e) {
      logger.error(e);
      throw e;
    }

    return updatedPatient;
  }
}

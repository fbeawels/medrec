package com.bea.medrec.webservices;

import com.bea.medrec.controller.PatientSession;
import com.bea.medrec.controller.RecordSession;
import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.value.Patient;
import com.bea.medrec.value.Record;
import com.bea.medrec.value.RecordsSummary;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import org.apache.log4j.Logger;
import weblogic.jws.WLHttpTransport;

// Standard JWS annotation that specifies that the name of the Web Service is
// "MedRecWebServices", its public service name is "MedRecWebServices", and the
// targetNamespace used in the generated WSDL is "http://www.bea.com/medrec"
@WebService(name = "MedRecWebServicesPortType",
    serviceName = "MedRecWebServices",
    targetNamespace = "http://www.bea.com/medrec")

// Standard JWS annotation that specifies this is a document-literal-wrapped
// Web Service  REVIEWME - review comment
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,
             use=SOAPBinding.Use.LITERAL,
             parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

// WebLogic-specific JWS annotation that specifies the port name is "MedRecWebServicesPort",
// and the context path and service URI used to build the URI of the Web
// Service is "ws_medrec/MedRecWebServices"
@WLHttpTransport(portName = "MedRecWebServicesPort",
    contextPath = "ws_medrec",
    serviceUri = "MedRecWebServices")

/**
 * <p>MedRecWebServices provides an interface for all MedRec Web services.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class MedRecWebServices extends MedRecBaseWebServices {
  private static Logger logger =
      MedRecLog4jFactory.getLogger(MedRecWebServices.class.getName());

  //  G E T   A   R E C O R D
  /**
   * <p>Retrieves all information for a visit given a record id.
   * This includes vital signs, the record, and any prescriptions.</p>
   *
   * @param pRecordId Id of the record.
   * @return Record Record value object
   * @throws Exception
   */
  @WebMethod()
  public Record getRecord(Integer pRecordId) throws Exception {
    logger.info("Getting record.");
    logger.debug("Record id: "+pRecordId);

    // Declare local variables.
    RecordSession recordSession = null;
    Record record = null;

    try {
      recordSession = getRecordSession();
      record = recordSession.getRecord(pRecordId);
    } catch (Exception e) {
      logger.error(e);
      throw e;
    }
    return record;
  }

  //  G E T   R E C O R D S   S U M M A R Y
  /**
   * <p>Retrieves all medical record summary.
   * This includes a List of abbreviated records, and
   * a List of current and recent prescriptions.</p>
   *
   * @param pPatientId Id of the record.
   * @return RecordsSummary Record Summary value object
   * @throws Exception
   */
  @WebMethod()
  public RecordsSummary getRecordsSummary(Integer pPatientId)
      throws Exception {
    logger.info("Getting records summary.");
    logger.debug("Patient ID: "+pPatientId);

    // Declare local variables.
    RecordSession recordSession = null;
    RecordsSummary recSumaryVO = null;

    try {
      recordSession = getRecordSession();
      recSumaryVO = recordSession.getRecordsSummary(pPatientId);
    } catch (Exception e) {
      logger.error(e);
      throw e;
    }
    return recSumaryVO;
  }

  //  G E T   P A T I E N T
  /**
   * <p>Find patient by last name.  Wild card search- %lastName%.</p>
   *
   * @param pLastname Last name substring
   * @return Patient[]  Array of Patient value objects.
   * @throws Exception
   */
  @WebMethod()
  public Patient[] findPatientByLastNameWild(String pLastname)
      throws Exception {
    logger.info("Finding patient by wildcard last name.");
    logger.debug("Last name: "+pLastname);

    // Declare local variables.
    PatientSession patientSession = null;
    Patient[] patientVOs = null;
    try {
      patientSession = getPatientSession();
      patientVOs = patientSession.findPatientByLastNameWild(pLastname);
    } catch (Exception e) {
      logger.error(e);
      throw e;
    }
    return patientVOs;
  }

  //  G E T   P A T I E N T   B Y   S S N
  /**
   * <p>Get patient by patient id.</p>
   *
   * @param pPatientSSN SSN of patient.
   * @return Patient Patient value object
   * @throws Exception
   */
  @WebMethod()
  public Patient findPatientBySsn(String pPatientSSN)
      throws Exception {
    logger.info("Finding patient by ssn.");
    logger.debug("SSN: "+pPatientSSN);

    // Declare local variables.
    PatientSession recordSession = null;
    Patient patientVO = null;

    try {
      // Get handle on patient session bean.
      recordSession = getPatientSession();
      // Find patient.
      patientVO = recordSession.findPatientBySsn(pPatientSSN);
    } catch (Exception e) {
      logger.error(e);
      throw e;
    }
    return patientVO;
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

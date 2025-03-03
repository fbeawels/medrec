package com.bea.medrec.webservices;

import com.bea.medrec.controller.PatientSession;
import com.bea.medrec.controller.RecordSession;
import com.bea.medrec.utils.JNDINames;
import com.bea.medrec.utils.ServiceLocator;
import javax.naming.NamingException;
import java.io.Serializable;

/**
 * <p>MedRecBaseWebServices provides base class services for all MedRec Web services.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public abstract class MedRecBaseWebServices implements Serializable {

    //   U T I L I T Y   M E T H O D S
  /**
  * Get PatientSession
  */
  protected PatientSession getPatientSession() throws NamingException {
    ServiceLocator locator = ServiceLocator.getInstance();
    Object obj = locator.getObj(JNDINames.PATIENT_SESSION_REMOTE_HOME,
        com.bea.medrec.controller.PatientSessionHome.class);
    return (PatientSession) obj;
  }

  /**
  * Get RecordSession
  */
  protected RecordSession getRecordSession() throws NamingException {
    ServiceLocator locator = ServiceLocator.getInstance();
    Object obj = locator.getObj(JNDINames.RECORD_SESSION_REMOTE_HOME,
        com.bea.medrec.controller.RecordSessionHome.class);
    return (RecordSession) obj;
  }

  /**
   * Construct and throw javax.xml.rpc.soap.SOAPFaultException;
   */
  protected void captureException(MedRecWSResponse response,
                                  String exceptionClause){
    response.setExceptionClause(exceptionClause);
  }
}

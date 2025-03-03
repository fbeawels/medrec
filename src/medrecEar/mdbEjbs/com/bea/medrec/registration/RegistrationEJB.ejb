package com.bea.medrec.registration;

import com.bea.medrec.controller.PatientSession;
import com.bea.medrec.utils.JNDINames;
import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.utils.ServiceLocator;
import com.bea.medrec.value.Registration;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import weblogic.ejb.GenericMessageDrivenBean;
import weblogic.ejbgen.*;

/**
 * <p>Handles the flow of incoming registration.</p>
 *
 * @author Copyright (c) 1999-2006 by BEA Systems, Inc. All Rights Reserved.
 *
 * Note: Recommend initial-beans-in-free-pool == max-beans-in-free-pool
 * for production system.
 */
@EjbRefs({
    @EjbRef(name = "ejb/patientsession",
            home = "com.bea.medrec.controller.PatientSessionHome",
            remote = "com.bea.medrec.controller.PatientSession",
            type = Constants.RefType.SESSION,
            link = "PatientSessionEJB",
            jndiName = "PatientSessionEJB.PatientSessionHome")
})
@MessageDriven(ejbName = "RegistrationBean",
               destinationJndiName = "jms/REGISTRATION_MDB_QUEUE",
               destinationType = "javax.jms.Queue",
               initialBeansInFreePool = "0",
               maxBeansInFreePool = "10",
               defaultTransaction = MessageDriven.DefaultTransaction.REQUIRED)
@ResourceEnvRefs({
    @ResourceEnvRef(name = "jms/MAIL_MDB_QUEUE",
                    type = "javax.jms.Queue",
                    jndiName = "jms/MAIL_MDB_QUEUE")
})
public class RegistrationEJB
    extends GenericMessageDrivenBean implements MessageListener {

  private static Logger logger =
      MedRecLog4jFactory.getLogger(RegistrationEJB.class.getName());

  // Member variables
  private MessageDrivenContext ctx;
  private PatientSession patientSession;

  public void setMessageDrivenContext(MessageDrivenContext mdc) {
    ctx = mdc;
    try {
      ServiceLocator locator = ServiceLocator.getInstance();
      Object obj = locator.getObj(JNDINames.PATIENT_SESSION_REMOTE_HOME,
          com.bea.medrec.controller.PatientSessionHome.class);
      patientSession = (PatientSession) obj;
    } catch (NamingException ne) {
      throw new EJBException(ne);
    }
  }

  /**
   * <p>Retrieve the Patient Value Object and print it out.</p>
   */
  public void onMessage(Message msg) {
    logger.debug("Message from registration queue: "+msg);

    // Declare and initialize local variables
    ObjectMessage message = (javax.jms.ObjectMessage) msg;
    Registration registration = null;
    try {
      registration = (Registration) message.getObject();
      patientSession.processNewRegistration(registration);
    } catch (JMSException jmsex) {
      logger.error("Unable to register the following user: \n" +
          registration.toString(), jmsex);
      getMessageDrivenContext().setRollbackOnly();
    } catch (Exception ex) {
      logger.error("Unable to register the following user: \n" +
          registration.toString(), ex);
    }
  }
}



package com.bea.medrec.xml;

import com.bea.medrec.utils.MedRecLog4jFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.apache.log4j.Logger;
import weblogic.ejb.GenericMessageDrivenBean;
import weblogic.ejbgen.*;

/**
 * <p>Handles the flow of incoming xml processing.</p>
 *
 * @author Copyright (c) 1999-2006 by BEA Systems, Inc. All Rights Reserved.
 */
@EjbRefs({
    @EjbRef(name = "ejb/adminsession",
            home = "com.bea.medrec.controller.AdminSessionHome",
            remote = "com.bea.medrec.controller.AdminSession",
            type = Constants.RefType.SESSION,
            link = "AdminSessionEJB",
            jndiName = "AdminSessionEJB.AdminSessionHome")
})
@MessageDriven(ejbName = "XMLUploadEJB",
               destinationJndiName = "jms/XML_UPLOAD_MDB_QUEUE",
               destinationType = "javax.jms.Queue",
               initialBeansInFreePool = "0",
               maxBeansInFreePool = "10",
               defaultTransaction = MessageDriven.DefaultTransaction.REQUIRED)
@ResourceEnvRefs({
    @ResourceEnvRef(name = "jms/MAIL_MDB_QUEUE",
                    type = "javax.jms.Queue",
                    jndiName = "jms/MAIL_MDB_QUEUE")
})
public class XMLUploadEJB
    extends GenericMessageDrivenBean implements MessageListener {
  private static Logger logger =
      MedRecLog4jFactory.getLogger(XMLUploadEJB.class.getName());

  /**
   * <p>Receives filename of XML to be processes.</p>
   */
  public void onMessage(Message msg) {
    logger.debug("Message from xml upload queue: " +
        msg);
    ObjectMessage message = (javax.jms.ObjectMessage) msg;
    String filename = null;
    MedRecXMLProcessor xmlProcessor = null;
    try {
      filename = (String) message.getObject();
      // Get XML processor, then all pending xml files.
      xmlProcessor = MedRecXMLProcessor.getInstance();
      // Pass xml file to be presisted.
      xmlProcessor.saveXMLRecord(filename);
    } catch (JMSException jmsex) {
      logger.error("Unable to upload the following file: " +
          filename, jmsex);
      getMessageDrivenContext().setRollbackOnly();
    } catch (Exception ex) {
      logger.error("Unable to upload the following file: " +
          filename, ex);
    }
  }
}
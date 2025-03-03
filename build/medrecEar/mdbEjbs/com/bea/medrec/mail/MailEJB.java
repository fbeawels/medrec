package com.bea.medrec.mail;

import com.bea.medrec.utils.JNDINames;
import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.value.Mail;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import weblogic.ejb.GenericMessageDrivenBean;
import weblogic.ejbgen.*;

/**
 * <p>Handles the flow of outgoing mail messages.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
@EnvEntries({
    @EnvEntry(name = "sendmailflag",
              type = "java.lang.Boolean",
              value = "false")
})
@MessageDriven(ejbName = "MailBean",
               destinationJndiName = "jms/MAIL_MDB_QUEUE",
               destinationType = "javax.jms.Queue",
               initialBeansInFreePool = "0",
               maxBeansInFreePool = "10",
               defaultTransaction = MessageDriven.DefaultTransaction.REQUIRED)
@ResourceEnvRefs({
    @ResourceEnvRef(name = "jms/MAIL_MDB_QUEUE",
                    type = "javax.jms.Queue",
                    jndiName = "jms/MAIL_MDB_QUEUE")
})
public class MailEJB
    extends GenericMessageDrivenBean implements MessageListener {

  // Attributes
  private static Logger logger =
    MedRecLog4jFactory.getLogger(MailEJB.class.getName());
  private boolean mailEnabled = false;
  private javax.mail.Session session = null;

  public void setMessageDrivenContext(MessageDrivenContext mdc) {
    try {
      InitialContext ic = new InitialContext();
      session = (javax.mail.Session) ic.lookup(JNDINames.MAIL_SESSION);
      Boolean sendMailBoolean = (Boolean) ic.lookup("java:comp/env/sendmailflag");
      if (sendMailBoolean != null)
        mailEnabled = sendMailBoolean.booleanValue();
    } catch (NamingException ne) {
      logger.debug(ne);
    }
  }

  public void onMessage(javax.jms.Message msg) {
    logger.debug("Message from mail queue: " +
        msg);
    ObjectMessage message = (ObjectMessage) msg;
    Mail mail = null;
    try {
      mail = (Mail) message.getObject();
    } catch (JMSException je) {
      logger.error("Unable to obtain mail message from queue");
      getMessageDrivenContext().setRollbackOnly();
    }
    if (mail != null && mailEnabled) {
      try {
        javax.mail.Message mailMessage = new MimeMessage(session);
        mailMessage.setFrom(new InternetAddress(mail.getFrom()));
        mailMessage.setRecipient(javax.mail.Message.RecipientType.TO,
            new InternetAddress(mail.getTo()));
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setContent(mail.getMessage(), "text/html");
        mailMessage.setSentDate(new java.util.Date());
        Transport.send(mailMessage);
      } catch (MessagingException me) {
        logger.info(me);
        getMessageDrivenContext().setRollbackOnly();
      } catch (Exception ex) {
        logger.error("Unable to mail: \n" +
            mail.toString(), ex);
      }
    } else {
      logger.info("Mail turned OFF. Please edit 'sendmailflag' " +
          "in MailEJB.ejb to enable.");
    }
  }
}

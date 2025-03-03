package com.bea.medrec.controller;

import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.value.Mail;
import java.text.MessageFormat;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.jms.*;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import weblogic.ejbgen.*;
import weblogic.ejbgen.Session;

/**
 * <p>Session Bean implementation for Mail EJB.
 * The Mail EJB provides support for composing and sending mail messages.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
@FileGeneration(remoteClass = Constants.Bool.TRUE,
                remoteHome = Constants.Bool.TRUE)
@JndiName(remote = "MailSessionEJB.MailSessionHome")
@ResourceRefs({
    @ResourceRef(name = "jms/MedRecQueueConnectionFactory",
                 type = "javax.jms.QueueConnectionFactory",
                 auth = ResourceRef.Auth.APPLICATION,
                 sharingScope = ResourceRef.SharingScope.SHAREABLE,
                 jndiName = "jms/MedRecQueueConnectionFactory")
})
@ResourceEnvRefs({
    @ResourceEnvRef(name = "jms/MAIL_MDB_QUEUE",
                    type = "javax.jms.Queue",
                    jndiName = "jms/MAIL_MDB_QUEUE")
})
@Session(maxBeansInFreePool = "1000",
         initialBeansInFreePool = "0",
         transTimeoutSeconds = "0",
         type = Session.SessionType.STATELESS,
         defaultTransaction = Constants.TransactionAttribute.REQUIRED,
         enableCallByReference = Constants.Bool.TRUE,
         ejbName = "MailSessionEJB")

public class MailSessionEJB extends weblogic.ejb.GenericSessionBean {
  private static Logger logger =
    MedRecLog4jFactory.getLogger(MailSessionEJB.class.getName());
  private SessionContext ctx;
  private QueueConnectionFactory qcFactory;
  private Queue que;

  /**
  * <p>Sets the session context.  Get handles for all
  * session and entity and JMS connections used throughout
  * this session bean.</p>
  *
  * @param ctx  SessionContext Context for session
  */
  public void setSessionContext(SessionContext ctx) {
    this.ctx = ctx;
    try {
      // JMS connections and queues.
      que = JNDILookupUtils.getJMailQueue();
      qcFactory = JNDILookupUtils.getQCFactory();
    } catch (NamingException ne) {
      throw new EJBException(ne);
    }
  }

    //   P U B L I C   M E T H O D S

    //   C O M P O S E   M A I L
  /**
  * <p>Composes standard MedRec mail message.</p>
  *
  * @param pMail
  * @exception NamingException
  * @exception Exception
  */
  @RemoteMethod()
  public Mail composeMail(Mail pMail, Object[] pObjs)
      throws NamingException, Exception {
    logger.debug("Composing mail message.");
    if (pObjs != null) {
      String formattedText = MessageFormat.format(pMail.getMessage(), pObjs);
      pMail.setMessage(formattedText);
    }
    return pMail;
  }

    //   C O M P O S E   A N D   M A I L
  /**
  * <p>Composes standard MedRec mail message.</p>
  *
  * @param pMail
  * @exception NamingException
  * @exception Exception
  */
  @RemoteMethod()
  public void composeAndSendMail(Mail pMail) throws NamingException, Exception {
    sendMailToQueue(composeMail(pMail, null));
  }

    //   C O M P O S E   A N D   M A I L
  /**
  * <p>Composes standard MedRec mail message.</p>
  *
  * @param pMail
  * @exception NamingException
  * @exception Exception
  */
  @RemoteMethod()
  public void composeAndSendMail(Mail pMail, Object[] pObjs)
      throws NamingException, Exception {
    sendMailToQueue(composeMail(pMail, pObjs));
  }

      //   S E N D   M A I L   T O   Q U E U E
  /**
* <p>Adds a patient by calling the appropriate entity beans.</p>
*
* @param pMail    Patient value object.
* @exception NamingException
* @exception Exception
*/
  @RemoteMethod()
  public void sendMailToQueue(Mail pMail) throws NamingException, Exception {
    logger.debug("Sending mail message: " +
        pMail.toString());

    // Declare local variables
    ObjectMessage mailMessage = null;
    QueueSession qSession = null;
    QueueConnection qConnection = null;
    QueueSender qSender = null;
    try {
      qConnection = qcFactory.createQueueConnection();
      qSession = qConnection.createQueueSession(false, 0);
      qConnection.start();
      qSender = qSession.createSender(que);
      mailMessage = qSession.createObjectMessage(pMail);
      qSender.send(mailMessage);
      logger.debug("Finished sending e-mail to queue");
    } catch (JMSException jmse) {
      throw new EJBException(jmse);
    } catch (Exception e) {
      logger.error(e);
      throw e;
    }
  }
}

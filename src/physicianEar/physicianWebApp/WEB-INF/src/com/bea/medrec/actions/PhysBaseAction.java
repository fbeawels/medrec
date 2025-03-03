package com.bea.medrec.actions;

import com.bea.medrec.controller.PhysicianSession;
import com.bea.medrec.controller.PhysicianSessionHome;
import com.bea.medrec.utils.MedRecLog4jFactory;
import javax.naming.InitialContext;
import org.apache.log4j.Logger;

/**
 * <p>Base servlet encapsulating common servlet functionality.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public abstract class PhysBaseAction
    extends BaseAction implements PhysicianConstants {

  private static Logger logger =
      MedRecLog4jFactory.getLogger(PhysBaseAction.class.getName());

  protected InitialContext ctx = null;
  private PhysicianSession physicianSession;

  /**
   * <p>Retrives PhysicianSession home.
   * If instance does not exist, retrieve a new instance.<p>
   *
   * @return PhysicianSession
   */
  protected PhysicianSession getPhysicianSession() throws Exception {
    if (ctx == null) ctx = new InitialContext();
    if (physicianSession == null) {
      logger.debug("Getting new physician session.");
      this.physicianSession = getPhysicianSessionHome();
    }
    return this.physicianSession;
  }

  //   P R I V A T E   M E T H O D S
  /**
   * <p>Get PhysicianSession EJB</p>
   *
   * @return PhysicianSession
   */
  private PhysicianSession getPhysicianSessionHome() throws Exception {
    PhysicianSessionHome home = (PhysicianSessionHome) ctx.lookup(
        "java:/comp/env/PhysicianSessionEJB");
    return (PhysicianSession) home.create();
  }
}

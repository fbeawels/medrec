package com.bea.medrec.controller;


/*
  ** This file was automatically generated by 
  ** EJBGen WebLogic Server 10.0 SP0  Mon Mar 26 02:02:31 BST 2007 914577 
*/


import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.Collection;

// BEGIN imports from bean class
import com.bea.medrec.utils.*;
import com.bea.medrec.value.*;
import java.text.*;
import javax.ejb.*;
import javax.jms.*;
import javax.naming.*;
import org.apache.log4j.*;
import weblogic.ejbgen.*;
// END imports from bean class


/**
 * <p>Session Bean implementation for Mail EJB.
 The Mail EJB provides support for composing and sending mail messages.</p>
 */

public interface MailSessionHome extends EJBHome {



  public MailSession create()     throws CreateException, RemoteException, javax.ejb.CreateException;



}

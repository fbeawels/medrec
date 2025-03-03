package com.bea.medrec.beans;

import com.bea.medrec.utils.MedRecLog4jFactory;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.Logger;
import org.apache.struts.validator.ValidatorForm;

/**
 * <p>Base bean encapsulating common bean attributes and functionality.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
abstract public class BaseBean extends ValidatorForm {
  private static Logger logger =
      MedRecLog4jFactory.getLogger(BaseBean.class.getName());

  // Instance Variables
  private String action = "";
  private String id = "";

  public BaseBean() {
  }

  // Getters
  public String getAction() {
    return this.action;
  }

  public String getId() {
    return this.id;
  }

  // Setters
  public void setAction(String action) {
    this.action = action;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setId(Integer id) {
    this.id = toStr(id);
  }

  // Utility
  protected String setDateStr(String str) {
    SimpleDateFormat sdf = null;
    String dateStr = "";
    try {
      sdf = new SimpleDateFormat("MM/dd/yyyy");
      Date d = sdf.parse(str);
      dateStr = sdf.format(d);
    } catch (ParseException e) {
    }
    return dateStr;
  }

  protected static String toStr(Object obj) {
    if (obj != null)
      return obj.toString();
    else
      return null;
  }

  // Validation
  /**
   * <p>String null check.</p>
   *
   * @param str
   */
  protected boolean isNotEmpty(String str) {
    return str != null && str.length() > 0;
  }

  /**
   * <p>String null check.</p>
   *
   * @param str
   */
  protected boolean isEmpty(String str) {
    return !(isNotEmpty(str));
  }

  protected Integer str2Integer(String str) {
    logger.debug("Converting "+str+" to Integer");
    Integer tempInt = null;
    try {
      if (!isEmpty(str))
        tempInt = Integer.valueOf(str);
    } catch (NumberFormatException e) {
    }
    return tempInt;
  }

 /**
  * <p>Convert string representation of a date to calendar.</p>
  *
  * @param str
  * @return Calendar
  */
  protected Calendar str2Calendar(String str) {
   logger.debug("Converting "+str+" to Calendar");
    Calendar cal = null;
    if (!isEmpty(str)) {
      try {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        java.util.Date d = sdf.parse(str);
        cal = Calendar.getInstance();
        cal.setTime(d);
      }
      catch(ParseException e) {   }
    }
    return cal;
  }

 /**
  * <p>Check for valid email.  Format only.  Format: a@a.aaa</p>
  *
  * @param email
  * @return boolean
  */
  protected boolean isValidEmail(String email) {
    boolean valid = true;
    if (email == null) {
      valid = false;
      logger.warn("Email: invalid");
    }
    if (valid) {
      String invalid[] = {" ","#","&",":",";","^","%","!","*","(",")"};
      for (int i = 0; i < invalid.length; i++) {
        if (email.indexOf(invalid[i]) >= 0) {
          valid = false;
          logger.warn("Email: invalid");
        }
      }
    }
    if (valid) {
      int atFound  = email.indexOf("@");
      int dotFound = email.indexOf(".", atFound);

      if (atFound < 0 || dotFound < 0 || atFound+1 == dotFound) {
        valid = false;
        logger.warn("Email: invalid");
      }
    }
    return valid;
  }

  /**
   * <p>Check for valid social security number. Format: 999999999</p>
   *
   * @param ssn
   * @return boolean
   */
  protected boolean isValidSsn(String ssn) {
    boolean valid = true;
    if (isEmpty(ssn)) {
      logger.warn("SSN: invalid");
      valid = false;
    } else if (ssn.length() != 9) {
      logger.warn("SSN: invalid");
      valid = false;
    }
    return valid;
  }

  /**
   * <p>Converts a array to array of given class.</p>
   *
   * @param pObjArray Array of objects
   * @param pClazz Class of newly transformed array
   * @return Object Array of given Class objects
   */
  public Object toBeanArray(Object[] pObjArray, Class pClazz) {
    if (pObjArray != null && pObjArray.length > 0) {
      Class cl = pObjArray.getClass().getComponentType();
      logger.debug("Converting incoming "+cl.getName()+" array (len="+
          pObjArray.length+") to array of "+pClazz.getName());
      Object newObjArray = Array.newInstance(pClazz, pObjArray.length);
      for (int i=0; i<pObjArray.length; i++) {
        try {
          Constructor constr = pClazz.getConstructor(new Class[]{cl});
          logger.debug("Calling: "+constr.getName()+"("+cl+")");
          Array.set(newObjArray, i, constr.newInstance(new Object[]{pObjArray[i]}));
        } catch (Exception e) {
          logger.error("Unable to transform value object array.", e);
        }
      }
      return newObjArray;
    } else {
      logger.debug("Incoming array null or len=0");
      return Array.newInstance(pClazz, 0);
    }
  }

  /**
   * <p>Converts a array to array of given class.</p>
   *
   * @param pObjArray Array of objects
   * @param pClazz Class of newly transformed array
   * @return Collection Collection of given Class objects
   */
  public Collection<Object> toCollectionBean(Object[] pObjArray, 
                                             Class pClazz) {
    if (pObjArray != null && pObjArray.length > 0) {
      Class cl = pObjArray.getClass().getComponentType();
      logger.debug("Converting incoming "+cl.getName()+" array (len="+
          pObjArray.length+") to array of "+pClazz.getName());
      Collection<Object> newCollection = new ArrayList<Object>();
      for (int i=0; i<pObjArray.length; i++) {
        try {
          Constructor constr = pClazz.getConstructor(new Class[]{cl});
          logger.debug("Calling: "+constr.getName()+"("+cl+")");
          newCollection.add(constr.newInstance(new Object[]{pObjArray[i]}));
        } catch (Exception e) {
          logger.error("Unable to transform value object array.", e);
        }
      }
      return newCollection;
    } else {
      logger.debug("Incoming array null or len=0");
      return new ArrayList<Object>();
    }
  }

  /**
   * <p>Converts a Collection to array of given class.</p>
   *
   * @param objCol Collection of objects
   * @param pClazz Class of newly transformed array
   * @return Object Array of given Class objects
   */
  protected Object toArray(Collection objCol, Class pClazz) {
    if (objCol != null && objCol.size() > 0) {
      Object newObjArray = null;
      Constructor constr = null;
      try {
        newObjArray = Array.newInstance(pClazz, objCol.size());
        Iterator itr = objCol.iterator();
        Object obj = (Object) itr.next();
        Class cl = obj.getClass();
        logger.debug("Converting incoming "+cl.getName()+" array (len="+
          objCol.size()+") to array of "+pClazz.getName());
        logger.debug("Calling: "+constr.getName()+"("+cl+")");
        constr = pClazz.getConstructor(new Class[]{cl});
        int i = 0;
        while (itr.hasNext()) {
          obj = (Object) itr.next();
          Array.set(newObjArray, i, constr.newInstance(new Object[]{obj}));
          i++;
        }
      } catch (Exception e) {
          logger.error("Unable to transform value object array", e);
      }
      return newObjArray;
    } else {
      logger.debug("Incoming array null or len=0");
      return Array.newInstance(pClazz, 0);
    }
  }
}

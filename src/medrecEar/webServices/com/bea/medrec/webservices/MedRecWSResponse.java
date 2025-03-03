package com.bea.medrec.webservices;

/**
 * <p>MedRecWebServices provides an interface for all MedRec Web services.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class MedRecWSResponse {

  private boolean success = true;
  private int returnCode = 0;
  private String exceptionClause = "";
  private Object[] returnObj = null;

  public static final boolean SUCCESS = true;  
  public static final boolean FAILURE = false;

  public void setExceptionClause(String pMsg) {
    this.exceptionClause = pMsg;
    success = false;
  }

  public String getExceptionClause() {
    return this.exceptionClause;
  }

  public void setReturnCode(int pReturnCode) {
    returnCode = pReturnCode;
  }

  public int getReturnCode() {
    return returnCode;
  }

  public void setSuccess(boolean pSuccess) {
    success = pSuccess;
  }

  public boolean getSuccess() {
    return success;
  }

  public void setReturnObject(Object[] obj) {
    this.returnObj = obj;
  }

  public Object[] getReturnObject() {
    return this.returnObj;
  }
}

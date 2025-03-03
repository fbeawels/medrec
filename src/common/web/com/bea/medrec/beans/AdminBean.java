package com.bea.medrec.beans;

/**
 * <p>Form bean containing administrator user data.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class AdminBean extends BaseBean
{
  // Instance Variables
  protected String username = "";

  // Constructors
  public AdminBean() {  }

  public AdminBean(String pUsername) { this.username = pUsername; }

  // Getters
  public String getUsername() { return this.username; }

  // Setters
  public void setUsername(String pUsername) { this.username = pUsername; }
}

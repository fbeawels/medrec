package com.bea.medrec.beans;

import com.bea.medrec.value.Physician;

/**
 * <p>Form bean for the physician web application.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public final class PhysicianBean extends BaseBean {
  // Instance Variables
  private String firstName = "";
  private String middleName = "";
  private String lastName = "";
  private String email = "";
  private String phone = "";

  // Constructors
  public PhysicianBean() {
  }

  public PhysicianBean(String firstName,
                       String lastName,
                       String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public PhysicianBean(String firstName,
                       String middleName,
                       String lastName,
                       String email,
                       String phone) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
  }

  // Getters
  public String getFirstName() {
    return this.firstName;
  }

  public String getMiddleName() {
    return this.middleName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPhone() {
    return this.phone;
  }

  // Setters
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Physician toPhysician() {
    Physician physician = new Physician();
    physician.setFirstName(this.firstName);
    physician.setMiddleName(this.middleName);
    physician.setLastName(this.lastName);
    physician.setEmail(this.email);
    physician.setPhone(this.phone);
    return physician;
  }

}

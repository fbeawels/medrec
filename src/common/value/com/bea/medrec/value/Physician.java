package com.bea.medrec.value;

/**
 * <p>This class represents information about a patient.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public final class Physician extends BaseVO {

  private String firstName;
  private String middleName;
  private String lastName;
  private String phone;
  private String email;

  public Physician() {
  }

  public Physician(Integer id,
                   String firstName,
                   String middleName,
                   String lastName,
                   String phone,
                   String email) {
    super.setId(id);
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.phone = phone;
    this.email = email;
  }

  public Physician(String firstName,
                   String middleName,
                   String lastName,
                   String phone,
                   String email) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.phone = phone;
    this.email = email;
  }

  public Physician(String firstName,
                   String middleName,
                   String lastName) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getMiddleName() {
    return this.middleName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getPhone() {
    return this.phone;
  }

  public String getEmail() {
    return this.email;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String toString() {
    StringBuffer str = new StringBuffer();
    str.append("PHYSICIAN [Id: "+super.getId());
    str.append("| Name: "+firstName);
    str.append(" "+middleName);
    str.append(" "+lastName);
    str.append("| Phone: "+phone);
    str.append("| Email: "+email);
    str.append("]");
    return str.toString();
  }
}

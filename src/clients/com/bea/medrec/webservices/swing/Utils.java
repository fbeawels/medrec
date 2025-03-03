package com.bea.medrec.webservices.swing;

import com.bea.medrec.value.Address;
import com.bea.medrec.value.Patient;
//import com.bea.medrec.webservices.Address;
//import com.bea.medrec.webservices.Patient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>Util Class for Swing Client</p>
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class Utils {

  /**
   * <p>Takes an address value object and return an Address</p>
   *
   * @param pAddress Address to be converted to an Address
   * @return Address
   */
   public static Address toAddress(Address pAddress)
   {
     Address Address = null;
     if (pAddress != null) {
       Address = new Address();
       Address.setId(pAddress.getId());
       Address.setStreetName1(pAddress.getStreetName1());
       Address.setStreetName2(pAddress.getStreetName2());
       Address.setCity(pAddress.getCity());
       Address.setState(pAddress.getState());
       Address.setZipCode(pAddress.getZipCode());
       Address.setCountry(pAddress.getCountry());
     }
     return Address;
  }

  /**
   * <p>Take a Patient and return a Patient</p>
   *
   * @param pPatient Patient Value Object
   * @return Patient
   */
   public static Patient toPatient(Patient pPatient)
   {
     Patient Patient = null;
     if (pPatient != null) {
       Patient = new Patient();
       Patient.setId(pPatient.getId());
       Patient.setFirstName(pPatient.getFirstName());
       Patient.setMiddleName(pPatient.getMiddleName());
       Patient.setLastName(pPatient.getLastName());
       Patient.setDateOfBirth(pPatient.getDateOfBirth());
       Patient.setGender(pPatient.getGender());
       Patient.setSsn(pPatient.getSsn());
       Patient.setPhone(pPatient.getPhone());
       Patient.setEmail(pPatient.getEmail());
       Patient.setAddress(toAddress(pPatient.getAddress()));
     }
     return Patient;
  }

  /**
   * <p>Take a string and return a Calendar object</p>
   *
   * @param pString A string representing a Calendar
   * @return Calendar
   */
   public static Calendar str2Calendar(String pString)
   {
     Calendar cal = null;
     if (pString != null) {
       try {
         SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
         Date d = sdf.parse(pString);
         cal = Calendar.getInstance();
         cal.setTime(d);
       }
       catch(ParseException e) {   }
     }
     return cal;
  }

 /**
  * <p>Check for valid date, ignoring delimitator. Format: MM/DD/YYYY</p>
  *
  * @param date
  * @return boolean
  */
  public static boolean isValidDate(String date)
  {
    boolean valid = true;
    try {
      if (date.length() != 10) return false;
      String m = date.substring(0,2);
      String d = date.substring(3,5);
      String y = date.substring(6,10);
      Integer.parseInt(m);
      Integer.parseInt(d);
      Integer.parseInt(y);
    } catch(Exception e) {
      return false;
    }
    return valid;
  }

         //   D A T E   M A N I P U L A T I O N
 /**
  * <p>Format: MM/DD/YYYY</p>
  *
  * @param pCalendar
  * @return String
  */
  public static String getDisplayDate(Calendar pCalendar)
  {
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    if (pCalendar != null) {
    	return format.format(pCalendar.getTime());
    }
    else return "";
  }

}

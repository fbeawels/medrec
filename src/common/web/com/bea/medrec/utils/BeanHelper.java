package com.bea.medrec.utils;

import com.bea.medrec.beans.*;
import com.bea.medrec.value.Patient;
import com.bea.medrec.value.Prescription;
import com.bea.medrec.value.Record;
import com.bea.medrec.value.XMLImportFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This helper class converts collection of presentation beans to
 * collection of value objects and visa versa.
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class BeanHelper {

  //   I M P O R T   C O L L E C T I O N
  /**
   * <p>Converts a collection of import value objects to
   * a collection of import presentation beans.</p>
   *
   * @param importFiles Collection of Import value objects
   * @return Collection   Collection of Import presentation beans
   */
  public static Collection toImportBeanCollection(Collection importFiles) {
    Collection<Object> array = new ArrayList<Object>();
    if (importFiles != null) {
      Iterator itr = importFiles.iterator();
      while (itr.hasNext()) {
        XMLImportBean xmlImportBean =
            new XMLImportBean((XMLImportFile) itr.next());
        array.add(xmlImportBean);
      }
    }
    return array;
  }

  //   P A T I E N T   A P P R O V A L
  /**
   * <p>Converts a collection of patient approval presentation beans to
   * a collection of patient approval value objects.</p>
   *
   * @param patients Collection of Patient VOs
   * @return Collection   Collection of Patient Beans
   */
  public static Collection<Object> toPatientApprovalBeanCollection(Collection patients) {
    Collection<Object> array = new ArrayList<Object>();
    if (patients != null) {
      Iterator itr = patients.iterator();
      while (itr.hasNext()) {
        Patient patient = (Patient) itr.next();
        PatientApprovalBean approval = new PatientApprovalBean(patient.getId(),
            patient.getLastName(), patient.getFirstName());
        array.add(approval);
      }
    }
    return array;
  }

}

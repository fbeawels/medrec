package com.bea.medrec.xml;

import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.value.*;
import com.bea.medrec.xml.types.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * <p>This class reads in an XML set of medical records with XMLBeans
 * and then simply converts them to the value objects necessary to
 * save the data to the database.  In the future it would be nice
 * if the XMLBean itself could be the value object.</p>
 *
 * @author Copyright (c) 2006 by BEA Systems, Inc. All Rights Reserved.
 */
public class RecordXMLToVO {

  private static Logger logger =
      MedRecLog4jFactory.getLogger(RecordXMLToVO.class.getName());

  private MedicalRecordsDocument medrecDoc;

  /**
   * <p>Constructor is private
   */
  private RecordXMLToVO() {
  }

  /**
   * <p>Parses and validates the document that will be converted.</p>
   *
   * @param stream InputStream
   * @throws Exception
   */
  public void parse(InputStream stream) throws Exception {
    if (stream == null) {
      throw new Exception("InputStream for XMLBean is null.");
    } else {
      medrecDoc = MedicalRecordsDocument.Factory.parse(stream);
    }

    if (!medrecDoc.validate()) {
      logger.info("Document not valid");
    } else {
      logger.info("Document is valid");
    }
  }

  /**
   * <p>Primary method of this class used to convert one type of bean
   * to another.</p>
   *
   * @return Collection
   * @throws Exception
   */
  public Collection<MedicalRecord> getMedicalRecords() throws Exception {
    MedicalRecordType medrec = medrecDoc.getMedicalRecords();
    Collection<MedicalRecord> medRecVOCol = new ArrayList<MedicalRecord>();

    String srcId = medrec.getSrcId();
    String srcName = medrec.getSrcName();

    MedicalVisitType[] visits = medrec.getMedicalVisitArray();

    for (int i = 0; i < visits.length; i++) {
      MedicalRecord medicalRecordVO = convertPatientVisit(visits[i],
          srcId, srcName);
      medRecVOCol.add(medicalRecordVO);
    }

    logger.debug(medRecVOCol.toString());
    return medRecVOCol;
  }

  /**
   * <p>Converts a MedicalVisit to a MedicalRecord with all the trimmings.</p>
   *
   * @param visit   MedicalVisitType
   * @param srcId   String
   * @param srcName String
   * @return MedicalRecord
   * @throws Exception
   */
  private MedicalRecord convertPatientVisit(MedicalVisitType visit,
                                            String srcId, String srcName)
      throws Exception {
    logger.debug("convertPatientVisit called...");
    MedicalRecord medicalRecordVO = new MedicalRecord(Integer.valueOf(srcId),
        srcName);
    Patient patientVO = null;
    User userVO = null;

    patientVO = convertPatient(visit.getPatient());
    userVO = new User(patientVO.getEmail());
    medicalRecordVO.setPatient(patientVO);
    medicalRecordVO.setUser(userVO);

    RecordType[] records = visit.getRecordArray();

    for (int i = 0; i < records.length; i++) {
      Record recordVO = convertRecord(records[i]);
      medicalRecordVO.addRecord(recordVO);
    }

    return medicalRecordVO;
  }

  /**
   * <p>Converts the Patient bean.</p>
   *
   * @param patient PatientInfoType
   * @return Patient
   * @throws Exception
   */
  private Patient convertPatient(PatientInfoType patient)
      throws Exception {

    logger.debug("convertPatient called...");
    Patient patientVO = new Patient();

    patientVO.setSsn(patient.getSsn());
    patientVO.setFirstName(patient.getPatientName().getFirstName());
    patientVO.setMiddleName(patient.getPatientName()
        .getMiddleName());
    patientVO.setLastName(patient.getPatientName().getLastName());
    patientVO.setDateOfBirth(patient.getDob());
    patientVO.setPhone(patient.getPhone());
    patientVO.setEmail(patient.getEmail());
    logger.debug("Gender ="+patient.getGender()+".");
    String gender = (patient.getGender().toString().equals("male") ? "Male"
        : "Female");
    patientVO.setGender(gender);

    Address addrVO = convertAddress(patient.getAddress());
    patientVO.setAddress(addrVO);

    return patientVO;
  }

  /**
   * <p>Converts the Record bean.</p>
   *
   * @param record RecordType
   * @return Record
   * @throws Exception
   */
  private Record convertRecord(RecordType record) throws Exception {
    logger.debug("convertRecord called...");
    Record recordVO = new Record();
    List<Object> prescriptionList = new ArrayList<Object>();

    recordVO.setDate(record.getDate());
    recordVO.setSymptoms(record.getSymptoms());
    recordVO.setDiagnosis(record.getDiagnosis());
    recordVO.setNotes(record.getNotes());

    Physician physician = convertPhysician(record.getPhysician());
    recordVO.setPhysician(physician);

    VitalSigns vitals = convertVitals(record.getVitalSigns());
    recordVO.setVitalSigns(vitals);

    PrescriptionType[] prescriptions = record.getPrescriptionArray();

    for (int i=0; i<prescriptions.length; i++) {
      Prescription prescription = convertPrescription(prescriptions[i]);
      prescriptionList.add(prescription);
    }

    recordVO.setPrescriptions((Prescription[])prescriptionList.toArray(
        new Prescription[0]));

    return recordVO;
  }

  /**
   * <p>Converts the Physician bean.</p>
   *
   * @param physician VitalSignsType
   * @return VitalSigns
   * @throws Exception
   */
  private Physician convertPhysician(PhysicianInfoType physician) throws Exception {
    logger.debug("convertPhysician called...");
    Physician physicianVO = new Physician();
    physicianVO.setFirstName(physician.getFirstName());
    physicianVO.setMiddleName(physician.getMiddleName());
    physicianVO.setLastName(physician.getLastName());
    physicianVO.setPhone(physician.getPhone());
    physicianVO.setEmail(physician.getEmail());
    return physicianVO;
  }

  /**
   * <p>Converts the VitalSigns bean.</p>
   *
   * @param vitals VitalSignsType
   * @return VitalSigns
   * @throws Exception
   */
  private VitalSigns convertVitals(VitalSignsType vitals)
      throws Exception {
    logger.debug("convertVitals called...");
    VitalSigns vitalsVO = new VitalSigns();

    vitalsVO.setWeight(new Integer(vitals.getWeight().getAmount()
        .toString()));
    vitalsVO.setHeight(new Integer(vitals.getHeight().getAmount()
        .toString()));
    vitalsVO.setTemperature(String.valueOf(vitals.getTemperature()
        .getAmount()));
    vitalsVO.setBloodPressure(vitals.getBloodPressure()
        .getSystolic()+"/"
       +vitals.getBloodPressure().getDiastolic());
    vitalsVO.setPulse(vitals.getPulse().toString());

    return vitalsVO;
  }

  /**
   * <p>Converts the Prescription bean.</p>
   *
   * @param prescription PrescriptionType
   * @return Prescription
   * @throws Exception
   */
  private Prescription convertPrescription(PrescriptionType prescription)
      throws Exception {
    logger.debug("convertPrescription called...");
    Prescription prescriptionVO = new Prescription();

    prescriptionVO.setDatePrescribed(prescription.getDate());
    prescriptionVO.setDrug(prescription.getDrug());
    prescriptionVO.setDosage(prescription.getDosage());
    prescriptionVO.setFrequency(prescription.getFrequency());
    prescriptionVO.setRefillsRemaining(
        new Integer(prescription.getRefills().toString()));
    prescriptionVO.setInstructions(prescription.getInstructions());

    return prescriptionVO;
  }

  /**
   * <p>Converts the Address bean.</p>
   *
   * @param address AddressType
   * @return Address
   * @throws Exception
   */
  private Address convertAddress(AddressType address)
      throws Exception {
    logger.debug("convertAddress called...");
    Address addrVO = new Address();

    addrVO.setStreetName1(address.getStreetName1());
    addrVO.setStreetName2(address.getStreetName2());
    addrVO.setCity(address.getCity());
    addrVO.setState(address.getState());
    addrVO.setZipCode(address.getZip());
    addrVO.setCountry(address.getCountry());

    return addrVO;
  }

  /**
   * <p>Creates a new instance of RecordXMLToVO.</p>
   *
   * @return RecordXMLToVO
   */
  public static RecordXMLToVO getInstance() {
    return new RecordXMLToVO();
  }

  /**
   * <p>For testing.</p>
   *
   * @param args String[]
   */
  public static void main(String[] args) {
    RecordXMLToVO b2b = new RecordXMLToVO();
    try {
      b2b.parse(null);
      b2b.getMedicalRecords();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

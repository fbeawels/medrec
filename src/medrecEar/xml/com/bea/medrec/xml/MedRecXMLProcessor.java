package com.bea.medrec.xml;

import com.bea.medrec.controller.AdminSession;
import com.bea.medrec.exceptions.MedRecException;
import com.bea.medrec.utils.JNDINames;
import com.bea.medrec.utils.MedRecLog4jFactory;
import com.bea.medrec.utils.ServiceLocator;
import com.bea.medrec.utils.XMLFilter;
import com.bea.medrec.value.MedicalRecord;
import com.bea.medrec.value.XMLImportFile;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.ejb.EJBException;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

public class MedRecXMLProcessor {

  private static Logger logger =
      MedRecLog4jFactory.getLogger(MedRecXMLProcessor.class.getName());

  private String xmlDirLoc;

  private MedRecXMLProcessor() {
    // Incoming XML medical record location
    xmlDirLoc = System.getProperty("com.bea.medrec.xml.incoming");
    logger.info("XML directory: " + xmlDirLoc);
  }

  public static MedRecXMLProcessor getInstance() {
    return new MedRecXMLProcessor();
  }

  //  G E T   P E N D I N G ,   I N C O M I N G   X M L   F I L E S
  /**
   * <p>Get pending, incoming xml files containing new patients and
   * their medical record.</p>
   *
   * @return Collection  Collection of XMLImportFile objects.
   * @throws javax.naming.NamingException
   * @throws java.rmi.RemoteException
   * @throws Exception
   */
  public Collection<Object> getIncomingXMLFiles()
      throws RemoteException, NamingException, Exception {
    // Declare local variables.
    Collection<Object> col = new ArrayList<Object>();

    // if the log4j-init-file is not set, then no point in trying
    if (xmlDirLoc != null) {
      File xmlDir = new File(xmlDirLoc);
      if (xmlDir.isDirectory()) {
        FilenameFilter filter = new XMLFilter();
        File[] files = xmlDir.listFiles(filter);
        for (int i = 0; i < files.length; i++) {
          Calendar cal = new GregorianCalendar();
          cal.setTimeInMillis(files[i].lastModified());
          XMLImportFile xmlFile = new XMLImportFile(files[i].getName(),
              xmlDirLoc, cal, files[i].length());
          logger.debug(xmlFile.toString());
          col.add(xmlFile);
        }
      }
    }
    return col;
  }

  //   S A V E   X M L    R E C O R D
  /**
   * <p>Parses XML stream into MedRec value objects.
   * Stores resultant objects.</p>
   *
   * @param pBis
   * @throws MedRecException
   */
  public void saveXMLRecord(BufferedInputStream pBis)
      throws MedRecException, Exception {
    logger.info("Parsing xml and saving resultant.");

    // Declare local variables.
    AdminSession adminSession = null;
    //XMLInputStreamFactory factory = null;
    //XMLInputStream stream = null;
    RecordXMLToVO parser = null;
    Collection<MedicalRecord> medicalRecordCol = null;

    try {
      // Session bean homes.
      adminSession = getAdminSession();
      parser = RecordXMLToVO.getInstance();
      logger.debug("Parsing xml doc.");
      parser.parse(pBis);

      logger.debug("Getting MedicalRecords collection.");
      medicalRecordCol = parser.getMedicalRecords();
      if (logger.isDebugEnabled()) printMedicalRecords(medicalRecordCol);
      adminSession.insertMedicalRecord(medicalRecordCol);
    } catch (NamingException ne) {
      logger.error(ne.getLocalizedMessage(), ne);
      throw new MedRecException(ne);
    } catch (MedRecException me) {
      logger.error(me.getLocalizedMessage(), me);
      throw me;
    } catch (EJBException ejbe) {
      logger.error(ejbe.getLocalizedMessage(), ejbe);
      throw new MedRecException(ejbe);
    } catch (Exception e) {
      logger.error(e.getLocalizedMessage(), e);
      throw e;
    }
  }

  /**
   * <p>Parses given XML file found in xml.incoming System property
   * into MedRec value objects.  Stores resultant objects.</p>
   *
   * @param pFilename
   * @throws MedRecException
   */
  public void saveXMLRecord(String pFilename)
      throws MedRecException, Exception {
    logger.info("Parsing xml file: " + pFilename);

    // Declare local variables.
    File xmlFile = null;
    File archivedFile = null;
    FileInputStream fis = null;

    xmlFile = new File(xmlDirLoc + "/" + pFilename);
    logger.debug("XML filepath: " + xmlFile.getAbsolutePath());

    if (!xmlFile.exists() || !xmlFile.isFile())
      throw new MedRecException("File not found.");

    try {
      fis = new FileInputStream(xmlFile);
      saveXMLRecord(new BufferedInputStream(fis));
      archivedFile = new File(xmlDirLoc + "/" + pFilename + "_" + getDateTimeStr());
      xmlFile.renameTo(archivedFile);
    } catch (Exception e) {
      throw e;
    } finally {
      fis.close();
    }
  }

  //   P R I V A T E   M E T H O D S
  /**
   * <p>Prints contents of medical record collection.</p>
   */
  private void printMedicalRecords(Collection<MedicalRecord> pMedRecCol) {
    // Declare local variables.
    Iterator itr = null;
    MedicalRecord medicalRecord = null;

    itr = pMedRecCol.iterator();
    while (itr.hasNext()) {
      medicalRecord = (MedicalRecord) itr.next();
      logger.debug("******* Medical Record **********");
      logger.debug(medicalRecord.toString());
    }
  }

  /**
   * <p>Returns date string - yyMMddHHmmss.</p>
   */
  private String getDateTimeStr() {
    SimpleDateFormat dateFormat = null;
    dateFormat = new SimpleDateFormat("yyMMddHHmmss");
    return dateFormat.format(new Date());

  }

  /**
   * Get AdminSession
   */
  private AdminSession getAdminSession()
      throws NamingException {
    ServiceLocator locator = ServiceLocator.getInstance();
    Object obj = locator.getObj(JNDINames.ADMIN_SESSION_REMOTE_HOME,
        com.bea.medrec.controller.AdminSessionHome.class);
    return (AdminSession) obj;
  }
}

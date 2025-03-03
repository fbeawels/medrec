package com.bea.medrec.admin;

import com.bea.medrec.BaseMBean;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * This custom mbean returns the number of patients who awaiting to have their
 * accounts approved.
 *
 * @author Copyright (c) 2006 by BEA Systems, Inc. All Rights Reserved.
 */
public class AdminReport extends BaseMBean implements AdminReportMBean {
//  private static Logger logger = MedRecLog4jFactory.getLogger(AdminReport.class.getName());

  /**
   * Returns number of patients with a status of NEW
   *
   * @return int
   */
  public int getNewUserCount() {
    //logger.debug("Get new user count");
    int result = 0;
    try {

      ResultSet rs = null;

      Context ctx = new InitialContext();
      // REVIEWME - let's make this dynamic
      DataSource ds = (DataSource) ctx.lookup("jdbc/MedRecGlobalDataSource");
      Connection conn = ds.getConnection();

      Statement stmt = conn.createStatement();

      try {
        rs = stmt.executeQuery("SELECT COUNT(*) as rowcount FROM  medrec_user WHERE status = 'NEW'");
        rs.next();
        result = rs.getInt("rowcount");
      } catch (SQLException ex) {
        ex.printStackTrace();
      } finally {
        try {
          if (rs != null) rs.close();
          if (stmt != null) stmt.close();
          if (conn != null) conn.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return result;
  }
}

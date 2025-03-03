package com.bea.medrec.security;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import weblogic.logging.LoggingHelper;
import weblogic.logging.WLLevel;
import weblogic.management.security.ProviderMBean;
import weblogic.security.providers.authentication.CustomDBMSAuthenticatorMBean;
import weblogic.security.providers.authentication.CustomDBMSAuthenticatorPlugin;

/**
 * Sample plugin for the CustomDBMSAuthenticatorPlugin ATN provider that matches
 * the MedRec ATN provider SQL modified for the test system to use the same SQL
 * that the SQL providers use.
 *
 * @author Copyright (c) 2006 by BEA Systems. All Rights Reserved.
 */
public class MedRecDBMSPlugin implements CustomDBMSAuthenticatorPlugin {

  /**
   * Default SQL that corresponds to the MedRec sample DBMS
   *
   *   private String GET_PASSWORD_STMT = "SELECT password FROM medrec_user
   *     WHERE username = ? AND status = 'ACTIVE'";
   *   private String PASSWORD_COLUMN = "password";
   *   private String GET_GROUPS_STMT = "SELECT group_name FROM groups groups
   *     WHERE groups.username = ?";
   *   private String GROUP_NAME_COLUMN = "group_name";
   *
   *
   * Default SQL that corresponds to the WLS SQL based DBMS providers
   *
   *   private String GET_PASSWORD_STMT = "SELECT U_PASSWORD FROM users
   *     WHERE U_NAME = ?";
   *   private String PASSWORD_COLUMN = "U_PASSWORD";
   *   private String GET_GROUPS_STMT = "SELECT G_NAME FROM groupmembers
   *     WHERE G_MEMBER = ?";
   *   private String GROUP_NAME_COLUMN = "G_NAME";
   */

  private String GET_PASSWORD_STMT = "SELECT password FROM medrec_user WHERE "+
    "username = ? AND status = 'ACTIVE'";
  private String PASSWORD_COLUMN = "password";
  private String GET_GROUPS_STMT = "SELECT group_name FROM groups groups WHERE "+
    "groups.username = ?";
  private String GROUP_NAME_COLUMN = "group_name";

  private int maxRecursionDepth = -1;
  private boolean caseSensitive = false;

  private CustomDBMSAuthenticatorMBean pluggableMBean = null;

  /**
   * Executed on initialization of the CustomDBMSAuthenticatorPlugin.
   * @param mBean Properties configured for the plugin
   */
  public void initialize(ProviderMBean mBean) {
    if (isDebugEnabled()) {
      logDebug("MedRecDBMSPlugin.initialize() called");
    }
    if (mBean == null) {
      if (isDebugEnabled()) {
        logDebug("No mBean supplied, using default setting values");
      }
      return;
    }
    if (!(mBean instanceof CustomDBMSAuthenticatorMBean)) {
      if (isDebugEnabled()) {
        logDebug("Not a pluggable runtime provider MBean, using default setting values");
      }
      return;
    }

    pluggableMBean = (CustomDBMSAuthenticatorMBean) mBean;
    getMBeanInfo();
  }

  /**
   * Executed on shutdown of the authentication provider, or if the
   * plugin is replaced dynamically at runtime with another implementation class.
   */
  public void shutdown() {
    if (isDebugEnabled()) {
      logDebug("MedRecDBMSPlugin.shutdown() called");
    }
  }

  /**
   * Called during authentication process to retrieve password for user.
   *
   * @param connection JDBC connection
   * @param user String representing the username
   * @return  String representing the password in one of the supported formats
   * @exception SQLException if a database access error occurs
   */
  public String lookupPassword(java.sql.Connection connection,
                                            String user)
      throws java.sql.SQLException {
    if (isDebugEnabled()) {
      logDebug("MedRecDBMSPlugin.lookupPassword() called");
      logDebug("MedRecDBMSPlugin: Looking up user: " + user);
    }
    if ((connection == null) || (user == null)) {
      if (isErrorEnabled()) {
        logError("MedRecDBMSPlugin.lookupPassword() failure, connection or " +
            "user was null");
      }
      return null;
    }

    PreparedStatement stmt = null;
    ResultSet rs = null;
    String password = null;
    try {
      if (isDebugEnabled()) {
        logDebug("MedRecDBMSPlugin.lookupPassword() SQL is: " +
            GET_PASSWORD_STMT);
      }
      stmt = connection.prepareStatement(GET_PASSWORD_STMT);
      stmt.setString(1, user);
      rs = stmt.executeQuery();
      if (rs.next())
        password = rs.getString(PASSWORD_COLUMN);
    } catch (SQLException e) {
      if (isWarnEnabled()) {
        logWarn("MedRecDBMSPlugin.lookupPassword() exception", e);
      }
      throw e;
    } finally {
      cleanup(rs, stmt);
    }
    return password;
  }

  /**
   * Called during Identity Assertion to verify existence of user.
   *
   * @param connection JDBC connection
   * @param user String representing the username
   * @return boolean indicating user exists or not. true if user exists,
   *  false if it doesn't
   * @exception SQLException if a database access error occurs
   */
  public boolean userExists(java.sql.Connection connection,
                                         String user)
      throws java.sql.SQLException {
    if (isDebugEnabled()) {
      logDebug("MedRecDBMSPlugin.userExists() called");
    }
    if ((connection == null) || (user == null)) {
      if (isErrorEnabled()) {
        logError("MedRecDBMSPlugin.userExists() failure, connection or user " +
            "was null");
      }
      return false;
    }
    String password = lookupPassword(connection, user);
    if (password != null) {
      if (isInfoEnabled()) {
        logInfo("MedRecDBMSPlugin.userExists() found user " + user);
      }
      return true;
    }

    if (isInfoEnabled()) {
      logInfo("MedRecDBMSPlugin.userExists() didn't find user " + user);
    }
    return false;
  }

  /**
   * Called during authentication and identity assertion to determine the users
   *  group membership.
   *
   * @param connection Connection to the database
   * @param user String representing the username
   * @return an array of group strings, or null for no groups.
   * @exception SQLException if a database access error occurs
   */
  public String[] lookupUserGroups(java.sql.Connection connection,
                                                String user)
      throws java.sql.SQLException {
    if (isDebugEnabled()) {
      logDebug("MedRecDBMSPlugin.lookupUserGroups() called");
    }
    if ((connection == null) || (user == null)) {
      if (isErrorEnabled()) {
        logError("MedRecDBMSPlugin.lookupUserGroups() failure, connection or " +
            "user was null");
      }
      return null;
    }

    PreparedStatement stmt = null;
    Vector<String> groups = new Vector<String>();
    try {
      if (isDebugEnabled()) {
        logDebug("MedRecDBMSPlugin.lookupUserGroups() SQL : " + GET_GROUPS_STMT);
      }
      stmt = connection.prepareStatement(GET_GROUPS_STMT);
      findMemberGroups(stmt, user, groups, 0);
    } catch (SQLException e) {
      if (isErrorEnabled()) {
        logError("MedRecDBMSPlugin.lookupUserGroups() Exception!", e);
      }
      throw e;
    } finally {
      cleanup(stmt);
    }

    if (isInfoEnabled()) {
      logInfo("MedRecDBMSPlugin.lookupUserGroups() found " + groups.size() +
          " groups:");
    }
    String[] retVal = null;
    if (groups.size() > 0) {
      retVal = new String[groups.size()];
      for (int i = 0; i < groups.size(); i++) {
        retVal[i] = (String) groups.elementAt(i);
        if (isDebugEnabled()) {
          logDebug("   " + retVal[i]);
        }
      }
    }
    return retVal;
  }

  // Implementation specific utility class for cleaning up statements and results
  private void cleanup(ResultSet pResultSet, PreparedStatement pStmt)
      throws SQLException {
    try {
      cleanup(pResultSet);
    } catch (SQLException e) {
      if (isWarnEnabled()) {
        logWarn("Cleanup failed on ResultSet", e);
      }
      throw e;
    } finally {
      cleanup(pStmt);
    }
  }

  // Implementation specific utility class for cleaning up results
  private void cleanup(ResultSet pResultSet)
      throws SQLException {
    try {
      if (pResultSet != null) {
        pResultSet.close();
        pResultSet = null;
      }
    } catch (SQLException e) {
      if (isWarnEnabled()) {
        logWarn("Cleanup failed on ResultSet", e);
      }
      throw e;
    }
  }

  // Implementation specific utility class for cleaning up statements
  private void cleanup(PreparedStatement pStmt)
      throws SQLException {
    try {
      if (pStmt != null) {
        pStmt.close();
        pStmt = null;
      }
    } catch (SQLException e) {
      if (isWarnEnabled()) {
        logWarn("Cleanup failed on Statement", e);
      }
      throw e;
    }
  }

  // Implementation specific utility class for debug logging
  private boolean isDebugEnabled() {
    return LoggingHelper.getServerLogger().isLoggable(WLLevel.DEBUG);
  }

  // Implementation specific utility class for debug logging
  private void logDebug(String msg) {
    LoggingHelper.getServerLogger().log(WLLevel.DEBUG, msg);
  }

  // Implementation specific utility class for error logging
  private boolean isErrorEnabled() {
	  return LoggingHelper.getServerLogger().isLoggable(WLLevel.ERROR);
  }

  // Implementation specific utility class for error logging
  private void logError(String msg) {
    LoggingHelper.getServerLogger().log(WLLevel.ERROR, msg);
  }

  // Implementation specific utility class for error logging
  private void logError(String msg, Throwable th) {
    LoggingHelper.getServerLogger().log(WLLevel.ERROR, msg, th);
  }

  // Implementation specific utility class for warn logging
  private boolean isWarnEnabled() {
	  return LoggingHelper.getServerLogger().isLoggable(WLLevel.WARNING);
  }

  // Implementation specific utility class for warn logging
  private void logWarn(String msg, Throwable th) {
    LoggingHelper.getServerLogger().log(WLLevel.WARNING, msg, th);
  }

  // Implementation specific utility class for warn logging
  private void logWarn(String msg) {
    LoggingHelper.getServerLogger().log(WLLevel.WARNING, msg);
  }

  // Implementation specific utility class for general logging
  private boolean isInfoEnabled() {
    return LoggingHelper.getServerLogger().isLoggable(WLLevel.INFO);
  }

  // Implementation specific utility class for general logging
  private void logInfo(String msg) {
    LoggingHelper.getServerLogger().log(WLLevel.INFO, msg);
  }

  // Implementation specific utility to get settings from MBean
  private void getMBeanInfo() {

    // Sub plugins are not required to have any properties, but if the
    // sub plugin has special settings that it would like to represent in
    // the configuration, the properties are how to do that.
    //
    // The sample subplugin allows SQL statements and column names to be
    // specified as Properties, so look for those
    Properties testProperties = pluggableMBean.getPluginProperties();
    if (testProperties != null) {
      String testPropertyValue = null;

      testPropertyValue = testProperties.getProperty("GET_PASSWORD_STMT");
      if (testPropertyValue != null) {
        if (isDebugEnabled()) {
          logDebug("GET_PASSWORD_STMT = " + testPropertyValue);
        }
        GET_PASSWORD_STMT = testPropertyValue;
      }
      testPropertyValue = testProperties.getProperty("PASSWORD_COLUMN");
      if (testPropertyValue != null) {
        if (isDebugEnabled()) {
          logDebug("PASSWORD_COLUMN = " + testPropertyValue);
        }
        PASSWORD_COLUMN = testPropertyValue;
      }
      testPropertyValue = testProperties.getProperty("GET_GROUPS_STMT");
      if (testPropertyValue != null) {
        if (isDebugEnabled()) {
          logDebug("GET_GROUPS_STMT = " + testPropertyValue);
        }
        GET_GROUPS_STMT = testPropertyValue;
      }
      testPropertyValue = testProperties.getProperty("GROUP_NAME_COLUMN");
      if (testPropertyValue != null) {
        if (isDebugEnabled()) {
          logDebug("GROUP_NAME_COLUMN = " + testPropertyValue);
        }
        GROUP_NAME_COLUMN = testPropertyValue;
      }
    } else {
      if (isDebugEnabled()) {
        logDebug("No properties specified for sub-plugin");
      }
    }

    /** Sub plugins are expected to handle any group membership recursion performed
    internally. This means that they may not handle it at all or they may
    handle it in their own way. If they do handle group membership recursion, they
    should make an attempt to handle the group membership recursion limits that
    are configured (but they can ignore those settings if they choose).

    The sample handles group membership recursion and honors the recursion limit
    settings, so get those now. */
    String testGroupMembershipSearching =
        pluggableMBean.getGroupMembershipSearching();
    if (testGroupMembershipSearching != null) {
      if ("limited".equalsIgnoreCase(testGroupMembershipSearching)) {
        int testMaxRecursionDepth =
            pluggableMBean.getMaxGroupMembershipSearchLevel().intValue();
        if (testMaxRecursionDepth < 0) {
          if (isDebugEnabled()) {
            logDebug("MaxGroupMembershipSearchLevel was invalid " +
                testMaxRecursionDepth);
          }
        } else {
          maxRecursionDepth = testMaxRecursionDepth;
          if (isDebugEnabled()) {
            logDebug("MaxGroupMembershipSearchLevel is " + maxRecursionDepth);
          }
        }
      } else if ("unlimited".equalsIgnoreCase(testGroupMembershipSearching)) {
        if (isDebugEnabled()) {
          logDebug("Unlimited GroupMembershipSearching");
        }
        maxRecursionDepth = -1;
      } else {
        if (isDebugEnabled()) {
          logDebug("Unrecognized GroupMembershipSearching setting");
        }
      }
    }
    return;
  }

  // Implementation specific utility for handling recursive group membership lookups
  // FIXME: There needs to be more thought around how to pass along the groups
  // efficiently. There is too much conversion going on and searching for
  // names in an unordered vector is to check for cycles is very expensive
  private final void findMemberGroups(PreparedStatement statement,
                                      String name,
                                      Vector<String> resultGroups,
                                      int depth)
      throws SQLException {
    // Check for bad inputs
    if (statement == null) {
      String msg = "No SQL Statement found for retrieving groups. " +
          "Check configuration";
      if (isDebugEnabled()) {
        logDebug(msg);
      }
      throw new SQLException(msg);
    }
    if ((name == null) || (resultGroups == null)) {
      if (isDebugEnabled()) {
        logDebug("Invalid parameters to findMemberGroups");
      }
      return;
    }

    if (isDebugEnabled()) {
      logDebug("findMemberGroups for " + name);
    }

    // Lookup groups in the database
    String[] results = null;
    try {
      results = queryMemberGroups(statement, name);
    } catch (SQLException se) {
      if (isWarnEnabled()) {
        logWarn("SQL Exception when retrieving groups", se);
      }
      throw se;
    }

    if ((results == null) || (results.length == 0)) {
      if (isInfoEnabled()) {
        logInfo("No groups found in database for " + name);
      }
      return;
    }

    // Add the results to the running list
    for (int i = 0; i < results.length; i++) {
      if (memberAlreadyInGroups(caseSensitive, results[i], resultGroups)) {
        if (isDebugEnabled()) {
          logDebug("Cycle detected, not recursing further for: " + name);
        }
        results[i] = null;
        continue;
      }
      resultGroups.add(results[i]);
    }

    // If the depth will be exceeded by more recursion stop now
    if ((maxRecursionDepth != -1) && (depth >= maxRecursionDepth)) {
      if(isDebugEnabled()){
        logDebug("recursive membership search depth limit reached");
      }
      return;
    }

    // If we got here, then we got some new group names and we need to
    // check their group memberships recursively
    for (int i = 0; i < results.length; i++) {
      if (results[i] != null)
        findMemberGroups(statement, results[i], resultGroups, depth+1);
    }
    return;
  }

  private final boolean namesMatch(boolean caseSensitive,
                                   String name1,
                                   String name2) {
    if (caseSensitive)
      return name1.equals(name2);
    return name1.equalsIgnoreCase(name2);
  }

  private final boolean memberAlreadyInGroups(boolean caseSensitive,
                                              String member,
                                              Vector groups) {
    // If we don't have a member we're done, and the caller likely is too
    if (member == null)
      return true;

    // If we have a user and no groups yet, it isn't in there
    if ((groups == null) || (groups.size() == 0))
      return false;

    // FIXME: Really inefficient, maybe can use a hashmap or something here...
    if (caseSensitive) {
      for (Iterator grpItr = groups.iterator(); grpItr.hasNext();) {
        if (member.equals((String) grpItr.next()))
          return true;
      }
    } else {
      for (Iterator grpItr = groups.iterator(); grpItr.hasNext();) {
        if (member.equalsIgnoreCase((String) grpItr.next()))
          return true;
      }
    }
    return false;
  }

  private final String[] queryMemberGroups(PreparedStatement statement,
                                           String userOrGroup)
      throws java.sql.SQLException {
    if (isDebugEnabled()) {
      logDebug("MedRecDBMSPlugin.queryMemberGroups() called");
    }
    if ((statement == null) || (userOrGroup == null)) {
      if (isDebugEnabled()) {
        logDebug("MedRecDBMSPlugin.queryMemberGroups() failure, prepared " +
            "statement or user/group was null");
      }
      return null;
    }

    ResultSet rs = null;
    Vector<String> groups = new Vector<String>();
    try {
      if (isDebugEnabled()) {
        logDebug("MedRecDBMSPlugin.queryMemberGroups() execute lookup statement");
      }
      statement.setString(1, userOrGroup);
      rs = statement.executeQuery();
      while (rs.next()) {
        groups.addElement(rs.getString(GROUP_NAME_COLUMN));
      }
    } catch (SQLException e) {
      if (isWarnEnabled()) {
        logWarn("MedRecDBMSPlugin.queryMemberGroups() Exception!", e);
      }
      throw e;
    } finally {
      cleanup(rs);
    }

    if (isDebugEnabled()) {
      logDebug("MedRecDBMSPlugin.lookupUserGroups() found " + groups.size() +
          " groups:");
    }
    String[] retVal = null;
    if (groups.size() > 0) {
      retVal = new String[groups.size()];
      for (int i = 0; i < groups.size(); i++) {
        retVal[i] = (String) groups.elementAt(i);
        if (isDebugEnabled()) {
          logDebug("   " + retVal[i]);
        }
      }
    }
    return retVal;
  }

}

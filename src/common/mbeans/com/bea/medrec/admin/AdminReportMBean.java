package com.bea.medrec.admin;

import java.io.Serializable;

/**
 * MBean interface
 *
 * @author Copyright (c) 2006 by BEA Systems, Inc. All Rights Reserved.
 */
public interface AdminReportMBean extends Serializable {
  public int getNewUserCount();
}

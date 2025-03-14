package com.bea.medrec.utils;

import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.validator.Resources;
import org.apache.commons.validator.ValidatorUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Used by web applications for field validation.</p>
 */
public class StrutsValidator {
  public static boolean validateTwoFields(
      Object bean,
      ValidatorAction va,
      Field field,
      ActionErrors errors,
      HttpServletRequest request) {
      String value = ValidatorUtil.getValueAsString(
          bean,
          field.getProperty());
      String sProperty2 = field.getVarValue("secondProperty");
      String value2 = ValidatorUtil.getValueAsString(
          bean,
          sProperty2);
      if (!GenericValidator.isBlankOrNull(value)) {
         try {
            if (!value.equals(value2)) {
               errors.add(field.getKey(),
                  Resources.getActionError(
                      request,
                      va,
                      field));

               return false;
            }
         } catch (Exception e) {
               errors.add(field.getKey(),
                  Resources.getActionError(
                      request,
                      va,
                      field));
               return false;
         }
      }

      return true;
  }
}

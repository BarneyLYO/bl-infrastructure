package cn.blinfra.boot.common.util;

import cn.hutool.core.collection.CollUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

import static cn.blinfra.boot.common.constants.RegexPatterns.*;

public class ValidationUtils {

  public static boolean isMobile(String mobile) {
    return StringUtils.hasText(mobile)
           && PATTERN_MOBILE.matcher(mobile).matches();
  }

  public static boolean isURL(String url) {
    return StringUtils.hasText(url)
           && PATTERN_URL.matcher(url).matches();
  }

  public static boolean isXmlNCName(String str) {
    return StringUtils.hasText(str)
           && PATTERN_XML_NCNAME.matcher(str).matches();
  }

  /**
   * @param validator
   * @param object    object to validate
   * @param groups
   * @throws ConstraintViolationException when object violate the validator rules
   */
  public static void validate(
    Validator validator,
    Object object,
    Class<?>... groups
  ) {
    Set<ConstraintViolation<Object>> constraintViolations =
      validator.validate(object, groups);
    if (CollUtil.isNotEmpty(constraintViolations)) {
      throw new ConstraintViolationException(constraintViolations);
    }
  }

}

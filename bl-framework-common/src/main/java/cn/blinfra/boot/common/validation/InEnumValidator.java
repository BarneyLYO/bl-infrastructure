package cn.blinfra.boot.common.validation;

import cn.blinfra.boot.common.model.IntArrayValuable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InEnumValidator implements ConstraintValidator<InEnum, Integer> {
  private List<Integer> values;

  @Override
  public void initialize(InEnum annotation) {
    IntArrayValuable[] values = annotation.value().getEnumConstants();
    if (values.length == 0) {
      this.values = Collections.emptyList();
    }
    else {
      this.values =
        Arrays.stream(values[0].array()).boxed().collect(Collectors.toList());
    }
  }

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    if(value == null) {
      return true;
    }

    if(values.contains(value)) {
      return true;
    }

    context.disableDefaultConstraintViolation();
    String template =context.getDefaultConstraintMessageTemplate();
    context
      .buildConstraintViolationWithTemplate(
        template.replaceAll("\\{value}", values.toString()))
      .addConstraintViolation();

    return false;
  }
}

package cn.blinfra.boot.common.validation;

import cn.blinfra.boot.common.model.IntArrayValuable;
import cn.hutool.core.collection.CollUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InEnumCollectionValidator
  implements ConstraintValidator<InEnum, Collection<Integer>> {

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
  public boolean isValid(
    Collection<Integer> list,
    ConstraintValidatorContext context
  ) {
    if (CollUtil.containsAll(values, list)) {
      return true;
    }

    context.disableDefaultConstraintViolation();
    String template = context.getDefaultConstraintMessageTemplate();
    context
      .buildConstraintViolationWithTemplate(template.replaceAll("\\{value}",
                                                                CollUtil.join(
                                                                  list,
                                                                  ","
                                                                )
      )).addConstraintViolation();

    return false;
  }
}

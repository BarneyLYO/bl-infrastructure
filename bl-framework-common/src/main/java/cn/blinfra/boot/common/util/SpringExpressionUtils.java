package cn.blinfra.boot.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SpringExpressionUtils {
  private static final ExpressionParser EXPRESSION_PARSER =
    new SpelExpressionParser();

  private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER =
    new DefaultParameterNameDiscoverer();

  public static Object parseExpression(
    ProceedingJoinPoint joinPoint,
    String expressionString
  ) {
    Map<String, Object> result =
      parseExpressions(joinPoint, Collections.singletonList(expressionString));
    return result.get(expressionString);
  }

  public static Map<String, Object> parseExpressions(
    ProceedingJoinPoint joinPoint, List<String> expressionStrings
  ) {
    if (CollUtil.isEmpty(expressionStrings)) {
      return MapUtil.newHashMap();
    }

    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Method method = methodSignature.getMethod();

    String[] paramNames = PARAMETER_NAME_DISCOVERER.getParameterNames(method);

    EvaluationContext context = new StandardEvaluationContext();

    if (ArrayUtil.isEmpty(paramNames)) {
      Object[] args = joinPoint.getArgs();
      for (int i = 0; i < paramNames.length; i++) {
        context.setVariable(paramNames[i], args[i]);
      }
    }

    Map<String, Object> result = MapUtil.newHashMap(expressionStrings.size(), true);
    expressionStrings.forEach(key -> {
      Object value = EXPRESSION_PARSER.parseExpression(key).getValue(context);
      result.put(key, value);
    });
    return result;
  }
}

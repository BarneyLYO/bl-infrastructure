package cn.blinfra.boot.starter.expression;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;

/**
 * 在OrExpression左右两边加上括号
 */
public class OrExpressionX extends OrExpression {
  public OrExpressionX() {}

  public OrExpressionX (Expression leftExpression, Expression rightExpression) {
    this.setLeftExpression(leftExpression);
    this.setRightExpression(rightExpression);
  }

  @Override
  public String toString() {
    return "(" + super.toString()  +")";
  }
}

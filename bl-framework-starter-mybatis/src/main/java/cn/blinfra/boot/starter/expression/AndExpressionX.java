package cn.blinfra.boot.starter.expression;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;

public class AndExpressionX extends AndExpression {

  public AndExpressionX() {}

  public AndExpressionX(Expression leftExpression, Expression rightExpression) {
    this.setLeftExpression(leftExpression);
    this.setRightExpression(rightExpression);
  }

  @Override
  public String toString() {
    return "(" + super.toString() + ")";
  }
}

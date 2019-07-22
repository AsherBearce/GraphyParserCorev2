package io.github.asherbearce.graphy.token;

/**
 * An enum which specifies all possible operator tokens. Some operators can also be unary.
 */
public enum OperatorTokens implements Operator, Token {
  PLUS(1, true),
  MINUS(1, true),
  MULTIPLY(2, true),
  DIVIDE(2, true),
  EXPONENT(3, true);

  private final int precedence;
  private final boolean leftAssociative;

  OperatorTokens(int precedence, boolean leftAssociative){
    this.precedence = precedence;
    this.leftAssociative = leftAssociative;
  }

  @Override
  public boolean isLeftAssociative() {
    return leftAssociative;
  }

  @Override
  public int getPrecedence() {
    return precedence;
  }

  @Override
  public TokenTypes getTokenType() {
    return TokenTypes.OPERATOR;
  }
}

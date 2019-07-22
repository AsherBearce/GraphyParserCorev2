package io.github.asherbearce.graphy.token;

/**
 * An enum that lists all possible types of Tokens allowed.
 */
public enum TokenTypes implements Token {
  NUMBER,
  IDENTIFIER,
  OPERATOR,
  OPEN_PAREN,
  CLOSE_PAREN,
  OPEN_CURLY,
  CLOSE_CURLY,
  COMMA,
  COLON,
  EQUALS,
  LESS_THAN,
  GREATER_THAN,
  END;

  @Override
  public TokenTypes getTokenType() {
    return this;
  }
}

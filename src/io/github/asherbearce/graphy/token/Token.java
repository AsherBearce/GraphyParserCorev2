package io.github.asherbearce.graphy.token;

/**
 * An interface specifying how Tokens must behave.
 */
public interface Token {

  /**
   * Returns the Enum {@link TokenTypes} of this Token.
   * @return {@link TokenTypes}
   */
  TokenTypes getTokenType();
}

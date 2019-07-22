package io.github.asherbearce.graphy.token;

import io.github.asherbearce.graphy.math.NumberValue;

/**
 * A {@link Token} type for containing {@link NumberValue} values.
 */
public class NumberToken implements Token {

  private final NumberValue value;

  /**
   * Creates a new NumberToken given a {@link NumberValue}
   * @param value The {@link NumberValue to be contained}
   */
  public NumberToken(NumberValue value){
    this.value = value;
  }

  /**
   * Returns the value contained in this {@link Token}
   * @return {@link NumberValue}
   */
  public NumberValue getValue(){
    return value;
  }

  @Override
  public TokenTypes getTokenType() {
    return TokenTypes.NUMBER;
  }
}

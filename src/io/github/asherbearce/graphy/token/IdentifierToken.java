package io.github.asherbearce.graphy.token;

/**
 * A {@link Token} class for containing a {@link String} value
 */
public class IdentifierToken implements Token {

  private final String value;

  /**
   * Creates a new Token given a {@link String}
   * @param value The {@link String} to be contained within this {@link Token}
   */
  public IdentifierToken(String value){
    this.value = value;
  }

  /**
   * Returns the {@link String} value contained in this {@link Token}
   * @return
   */
  public String getValue(){
    return value;
  }

  @Override
  public TokenTypes getTokenType() {
    return TokenTypes.IDENTIFIER;
  }
}

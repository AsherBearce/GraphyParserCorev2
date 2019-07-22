package io.github.asherbearce.graphy.parsing;

import io.github.asherbearce.graphy.exception.UnexpectedTokenException;
import io.github.asherbearce.graphy.token.Token;
import io.github.asherbearce.graphy.token.TokenTypes;
import java.util.LinkedList;

/**
 * An abstract class which outlines how a list of {@link Token} should be handled.
 */
public abstract class TokenHandler {
  private TokenList.TokenContainer currentToken;
  /**
   * The container list of {@link Token}
   */
  protected TokenList tokens;

  /**
   * Sets up a new TokenHandler given a {@link TokenList}
   * @param tokens
   */
  public TokenHandler(LinkedList<Token> tokens){
    this.tokens = new TokenList(tokens);
    currentToken = this.tokens.getFirst();
  }

  /**
   * Reads the next token.
   * @return {@link Token}
   */
  protected Token nextToken(){
    currentToken = currentToken.next;
    return currentToken.value;
  }

  /**
   * Retrieves the current token being read.
   * @return {@link Token}
   */
  protected Token getCurrent(){
    return currentToken.value;
  }

  /**
   * Resets the current token to the beginning of the list.
   */
  protected void reset(){
    currentToken = this.tokens.getFirst();
  }

  /**
   * Asserts that the current token being read is of a chosen {@link TokenTypes}
   * @param expected The expected Token type
   * @throws UnexpectedTokenException
   */
  protected void expectToken(Token expected) throws UnexpectedTokenException {
    if (expected.getTokenType() != currentToken.value.getTokenType()){
      throw new UnexpectedTokenException("Expected " + expected.getTokenType() + ", got "
          + currentToken.value.getTokenType());
    }
  }
}

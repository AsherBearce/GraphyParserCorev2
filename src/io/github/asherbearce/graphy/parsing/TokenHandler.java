package io.github.asherbearce.graphy.parsing;

import io.github.asherbearce.graphy.exception.UnexpectedTokenException;
import io.github.asherbearce.graphy.token.Token;
import io.github.asherbearce.graphy.token.TokenTypes;
import java.util.LinkedList;

public abstract class TokenHandler {
  private TokenList.TokenContainer currentToken;
  protected TokenList tokens;

  public TokenHandler(LinkedList<Token> tokens){
    this.tokens = new TokenList(tokens);
    currentToken = this.tokens.getFirst();
  }

  protected Token nextToken(){
    currentToken = currentToken.next;
    return currentToken.value;
  }

  protected Token getCurrent(){
    return currentToken.value;
  }

  protected boolean containsToken(TokenTypes type){
    boolean result = false;

    while (getCurrent().getTokenType() != TokenTypes.END){
      if (getCurrent().getTokenType() == type){
        result = true;
        break;
      }
      nextToken();
    }
    reset();

    return  result;
  }

  protected void reset(){
    currentToken = this.tokens.getFirst();
  }

  protected void expectToken(Token expected) throws UnexpectedTokenException {
    if (expected.getTokenType() != currentToken.value.getTokenType()){
      throw new UnexpectedTokenException("Expected " + expected.getTokenType() + ", got "
          + currentToken.value.getTokenType());
    }
  }
}

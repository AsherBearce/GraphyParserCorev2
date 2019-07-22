package io.github.asherbearce.graphy.parsing;

import io.github.asherbearce.graphy.token.Token;
import java.util.LinkedList;

/**
 * A special linked-list object for storing and reading {@link Token}s.
 */
public class TokenList {
  private TokenContainer first;
  private TokenContainer last;

  /**
   * Gets the first {@link TokenContainer} in this list.
   * @return {@link TokenContainer}
   */
  public TokenContainer getFirst(){
    return first;
  }

  /**
   * Constructs a new TokenList from a {@link LinkedList}
   * @param from The {@link LinkedList} to construct this TokenList
   */
  public TokenList(LinkedList<Token> from){
    for (Token t : from){
      addLast(t);
    }
  }

  /**
   * Computes a new {@link LinkedList} from this TokenList
   * @return {@link LinkedList}
   */
  public LinkedList<Token> tokenLinkedList(){
    TokenContainer currentElement = this.first;
    LinkedList<Token> result = new LinkedList<>();

    while (currentElement != this.last){
      result.addLast(currentElement.value);
      currentElement = currentElement.next;
    }

    result.addLast(currentElement.value);

    return result;
  }

  /**
   * Adds a new {@link Token} to the end of the list
   * @param value The Token to be added to the list
   */
  public void addLast(Token value){
    TokenContainer newNode = new TokenContainer(value);
    if (first == null){
      first = newNode;
      last = newNode;
    }
    else{
      last.next = newNode;
      newNode.previous = last;
      last = newNode;
    }
  }

  /**
   * A container class containing node information for the list
   */
  public static class TokenContainer{
    /**
     * The {@link Token} value that is contained
     */
    public Token value;
    /**
     * The next node in the list
     */
    public TokenContainer next;
    /**
     * The previous node in the list
     */
    public TokenContainer previous;

    /**
     * Creates a new TokenContainer to contain a given {@link Token}
     * @param value The {@link Token} to be contained in the list
     */
    public TokenContainer(Token value){
      this.value = value;
    }
  }
}

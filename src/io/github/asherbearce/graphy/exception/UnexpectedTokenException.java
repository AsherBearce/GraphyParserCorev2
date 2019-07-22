package io.github.asherbearce.graphy.exception;

/**
 * An exception that gets thrown when a certain type of token is expected to be read,
 * but the current token being read is not the expected type.
 */
public class UnexpectedTokenException extends ParseException {
  public UnexpectedTokenException(String msg){
    super(msg);
  }
}

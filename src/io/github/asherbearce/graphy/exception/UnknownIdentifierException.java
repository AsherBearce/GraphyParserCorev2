package io.github.asherbearce.graphy.exception;

/**
 * An exception that gets thrown whenever a function or variable can not be found.
 */
public class UnknownIdentifierException extends ParseException {
  public UnknownIdentifierException(String msg){
    super(msg);
  }
}

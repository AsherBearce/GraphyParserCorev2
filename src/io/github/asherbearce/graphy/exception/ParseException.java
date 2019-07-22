package io.github.asherbearce.graphy.exception;

/**
 * Generic exception thrown when parsing does not go as expected.
 */
public class ParseException extends Exception {
  public ParseException(String msg){
    super(msg);
  }
}

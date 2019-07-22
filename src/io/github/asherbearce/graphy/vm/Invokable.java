package io.github.asherbearce.graphy.vm;

import io.github.asherbearce.graphy.exception.ParseException;
import io.github.asherbearce.graphy.math.NumberValue;

/**
 * A simple interface that outlines how Invokable objects such as {@link Function} should work.
 */
public interface Invokable {

  /**
   * Returns the identifier that corresponds to this Invokable.
   * @return {@link String}
   */
  String getIdentifier();
  /**
   * Returns the number of arguments this function takes.
   * @return int
   */
  int getNumArgs();
  /**
   * Evaluates the function given a variable number of {@link Expression} as arguments.
   * @param args A variable number of {@link Expression} to be used as arguments.
   * @return {@link NumberValue}
   * @throws ParseException
   */
  NumberValue invoke(Expression... args) throws ParseException;
  /**
   * Evaluates the function given a variable number of {@link NumberValue} as arguments.
   * @param args A variable number of {@link NumberValue} to be used as arguments.
   * @return {@link NumberValue}
   * @throws ParseException
   */
  NumberValue invoke(NumberValue... args) throws ParseException;
}

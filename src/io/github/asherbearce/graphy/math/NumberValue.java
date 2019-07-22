package io.github.asherbearce.graphy.math;

/**
 * An interface for implementing a new type of number system
 * @param <T> Any type of number that implements this interface.
 */
public interface NumberValue<T extends NumberValue> {
  /**
   * Adds two numbers together
   * @param rhs The right hand side of this operation
   * @return {@link T}
   */
  T add(T rhs);
  /**
   * Subtracts two numbers together
   * @param rhs The right hand side of this operation
   * @return {@link T}
   */
  T sub(T rhs);
  /**
   * Multiplies two numbers together
   * @param rhs The right hand side of this operation
   * @return {@link T}
   */
  T mul(T rhs);
  /**
   * Divides two numbers together
   * @param rhs The right hand side of this operation
   * @return {@link T}
   */
  T div(T rhs);
  /**
   * Takes the power of two numbers
   * @param rhs The right hand side of this operation
   * @return {@link T}
   */
  T pow(T rhs);
  /**
   * Takes the conjugate of a number
   * @return {@link T}
   */
  T conjugate();
  /**
   * Takes the negative of a number
   * @return {@link}
   */
  T negative();
  /**
   * Takes the Dual part of a number.
   * @return {@link Real}
   */
  Real dual();
  /**
   * Takes the Real part of a number.
   * @return {@link Real}
   */
  Real real();
  /**
   * Takes the Imaginary part of a number.
   * @return {@link Real}
   */
  Real imaginary();
  /**
   * Takes the Dual-Imaginary part of a number.
   * @return {@link Real}
   */
  Real dualImaginary();
  /**
   * Gives the class that can enclose any given type.
   * @param clazz The class of the value to enclose
   * @return {@link Class}
   */
  Class<? extends NumberValue> enclosingType(Class<? extends NumberValue> clazz);
}

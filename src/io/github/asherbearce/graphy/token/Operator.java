package io.github.asherbearce.graphy.token;

/**
 * An interface that specifies what methods an operator token must have
 */
public interface Operator {

  /**
   * Returns if this current operator is left associative.
   * @return boolean
   */
  boolean isLeftAssociative();

  /**
   * Returns the precedence of the current operator.
   * @return int
   */
  int getPrecedence();
}

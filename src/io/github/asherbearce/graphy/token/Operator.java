package io.github.asherbearce.graphy.token;

public interface Operator {
  boolean isLeftAssociative();
  int getPrecedence();
}

package io.github.asherbearce.graphy.vm;

import io.github.asherbearce.graphy.exception.ParseException;
import io.github.asherbearce.graphy.math.NumberValue;

public interface Invokable {
  String getIdentifier();
  int getNumArgs();
  NumberValue invoke(Expression... args) throws ParseException;
  NumberValue invoke(NumberValue... args) throws ParseException;
}

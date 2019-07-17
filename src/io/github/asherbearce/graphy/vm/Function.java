package io.github.asherbearce.graphy.vm;

import io.github.asherbearce.graphy.exception.ParseException;
import io.github.asherbearce.graphy.math.NumberValue;
import java.util.HashMap;

public class Function implements Invokable{
  private String identifier;
  private int numArgs;
  private HashMap<String, Integer> parameters;
  private Expression[] assignedParams;
  private ComputeEnvironment env;
  private Expression body;
  private Expression condition;

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public int getNumArgs() {
    return numArgs;
  }

  public void setNumArgs(int numArgs) {
    this.numArgs = numArgs;
  }

  public ComputeEnvironment getEnv() {
    return env;
  }

  public void setEnv(ComputeEnvironment env) {
    this.env = env;
  }

  public void setBody(Expression body){
    this.body = body;
  }

  public NumberValue invoke(Expression... args) throws ParseException {
    assignedParams = args;
    return body.evaluate();
  }

  public NumberValue invoke(NumberValue... args) throws ParseException {
    return null;//TODO Fix this
  }
}

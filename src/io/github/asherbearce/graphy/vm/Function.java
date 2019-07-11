package io.github.asherbearce.graphy.vm;

import io.github.asherbearce.graphy.math.NumberValue;
import java.util.HashMap;
import io.github.asherbearce.graphy.exception.UnkownIdentifierException;

public class Function {
  private String identifier;
  private int numArgs;
  private HashMap<String, Integer> parameters;
  private Expression[] assignedParams;
  private ComputeEnvironment env;

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

  public NumberValue retrieveVariable(String identifier) throws UnkownIdentifierException{
    NumberValue result;
    if (parameters.containsKey(identifier)){
     result = assignedParams[parameters.get(identifier)].evaluate();
    } else if (getEnv().getFunction(identifier) != null &&
        getEnv().getFunction(identifier).getNumArgs() == 0){
      result = getEnv().getFunction(identifier).invoke();
    }
    else{
      throw new UnkownIdentifierException("Unknown identifier: " + identifier);
    }
    return result;
  }

  public NumberValue invoke(NumberValue... args){
    return null;//TODO Fix this
  }
}

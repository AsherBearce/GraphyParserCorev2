package io.github.asherbearce.graphy.vm;

import io.github.asherbearce.graphy.exception.ParseException;
import io.github.asherbearce.graphy.math.NumberValue;
import java.util.HashMap;

public class ComputeEnvironment {

  private HashMap<String, Invokable> functions;
  private HashMap<String, Invokable> builtIn;
  private static ComputeEnvironment instance;

  private ComputeEnvironment() {
    functions = new HashMap<>();
    builtIn = new HashMap<>();
  }

  public static ComputeEnvironment getInstance(){
    if (instance == null){
      instance = new ComputeEnvironment();
    }

    return instance;
  }

  public Invokable getFunction(String identifier) {
    Invokable result;

    if (functions.containsKey(identifier)) {
      result = functions.get(identifier);
    } else if (builtIn.containsKey(identifier)){
      result = builtIn.get(identifier);
    }
    else{
      result = null;
    }

    return result;
  }

  public void putFunction(Function newFunction) {
    String identifier = newFunction.getIdentifier();
    if (functions.containsKey(identifier)) {
      functions.remove(identifier);
    }
    functions.put(identifier, newFunction);
  }

  public void clearContent() {
    functions.clear();
  }
}
package io.github.asherbearce.graphy.vm;

import io.github.asherbearce.graphy.exception.ParseException;
import io.github.asherbearce.graphy.math.Dual;
import io.github.asherbearce.graphy.math.MathUtil;
import io.github.asherbearce.graphy.math.NumberValue;
import java.util.HashMap;

public class ComputeEnvironment {

  private HashMap<String, Invokable> functions;
  private HashMap<String, Invokable> builtIn;
  private static ComputeEnvironment instance;

  private ComputeEnvironment() {
    functions = new HashMap<>();
    builtIn = new HashMap<>();
    builtIn.put("ln",
        new Invokable() {
          @Override
          public String getIdentifier() {
            return "ln";
          }

          @Override
          public int getNumArgs() {
            return 1;
          }

          @Override
          public NumberValue invoke(Expression... args) throws ParseException {
            return MathUtil.log(args[0].evaluate());
          }

          @Override
          public NumberValue invoke(NumberValue... args) throws ParseException {
            return MathUtil.log(args[0]);
          }
        });
    builtIn.put("derivative",
        new Invokable() {
          @Override
          public String getIdentifier() {
            return "derivative";
          }

          @Override
          public int getNumArgs() {
            return 3;
          }

          @Override
          public NumberValue invoke(Expression... args) throws ParseException {
            NumberValue eval = args[2].evaluate();
            final Dual dual = new Dual(eval.real().getValue(), 1);
            String varNameOf = args[1].getVarNames()[0];//This is expected to be a single variable
            args[0].setGetterMethod(varNameOf, () -> dual);
            return args[0].evaluate().dual();
          }

          @Override
          public NumberValue invoke(NumberValue... args) throws ParseException {
            return null;
          }
        });

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
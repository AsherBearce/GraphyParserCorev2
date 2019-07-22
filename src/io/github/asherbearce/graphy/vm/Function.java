package io.github.asherbearce.graphy.vm;

import io.github.asherbearce.graphy.exception.ParseException;
import io.github.asherbearce.graphy.math.NumberValue;
import io.github.asherbearce.graphy.parsing.Parser;
import java.util.HashMap;

/**
 * A class for containing {@link Expression}s and evaluating them with other given {@link Expression}s as arguments.
 */
public class Function implements Invokable{
  private String identifier;
  private int numArgs;
  private HashMap<String, Integer> parameters;
  private Expression[] assignedParams;
  private ComputeEnvironment env;
  private Expression body;
  private Expression condition;

  /**
   * Constructs a new Function object.
   */
  public Function(){
    env = ComputeEnvironment.getInstance();
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }

  /**
   * Sets the function identifier for this function.
   * @param identifier The new identifier for this function.
   */
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  @Override
  public int getNumArgs() {
    return numArgs;
  }

  /**
   * Sets the {@link Expression} body of this function. The body is the main execution of a function.
   * @param body The {@link Expression} body to be assigned to this function.
   */
  public void setBody(Expression body){
    this.body = body;
  }

  /**
   * Prepares all the variables in the body {@link Expression} to refer to the assigned parameters of this function.
   * @param varNames A String[] of all the variable names inside used inside of the body {@link Expression}.
   */
  public void setupParameters(String[] varNames){
    parameters = new HashMap<>();
    numArgs = varNames.length;

    for(int i = 0; i < varNames.length; i++){
      parameters.put(varNames[i], i);
      final String varName = varNames[i];

      body.setGetterMethod(varNames[i],
          () -> assignedParams[parameters.get(varName)].evaluate()
      );
    }
  }

  @Override
  public NumberValue invoke(Expression... args) throws ParseException {
    assignedParams = args;
    return body.evaluate();
  }

  @Override
  public NumberValue invoke(NumberValue... args) throws ParseException {
    assignedParams = new Expression[args.length];

    for (int i = 0; i < assignedParams.length; i++){
      assignedParams[i] = Parser.fromNumber(args[i]);
    }

    return body.evaluate();
  }
}

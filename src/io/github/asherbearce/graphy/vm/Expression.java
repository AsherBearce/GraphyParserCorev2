package io.github.asherbearce.graphy.vm;

import io.github.asherbearce.graphy.exception.ParseException;
import io.github.asherbearce.graphy.math.NumberValue;
import io.github.asherbearce.graphy.vm.Instruction.InstructionType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

/**
 * A class that contains instructions and a record of variables to be executed.
 */
public class Expression {
  private LinkedList<Instruction> instructions;
  private HashMap<String, Getter> variables;

  private void setupLowerVariables(HashMap<String, Getter> vars){
    instructions.forEach(
        (instruction) -> {
          if (instruction.getType() == InstructionType.GET){
            String varName = (String)instruction.getArg(0);
            boolean globalVarsContainsKey = vars.containsKey(varName);
            boolean exprVarsContainsKey = this.variables.containsKey(varName);

            if (!globalVarsContainsKey) {
              vars.put(varName, () -> null);
            }
            if (!exprVarsContainsKey){
              variables.put(varName, () -> vars.get(varName).get());
            }

          } else if (instruction.getType() == InstructionType.CALL){
            Object[] args = instruction.getArgs();
            for (int i = 1; i < args.length; i++){
              ((Expression)args[i]).setupLowerVariables(variables);
            }
          }

        }
    );
  }

  /**
   * Gets a variable defined in the expression given its identifier.
   * @param name The identifier of a variable.
   * @return The {@link NumberValue} associated with the identifier, if it exists.
   * @throws ParseException
   */
  public NumberValue getVariable(String name) throws ParseException{
    if (variables.containsKey(name)){
      return variables.get(name).get();
    }
    else{
      return null;
    }
  }

  /**
   * Returns a String[] of all variable identifiers associated with this Expression.
   * @return String[]
   */
  public String[] getVarNames(){
    return variables.keySet().toArray(new String[0]);
  }

  /**
   * Constructs a new Expression given a {@link LinkedList}.
   * @param instructions The list of instructions.
   */
  public Expression(LinkedList<Instruction> instructions){
    this.instructions = instructions;
    variables = new HashMap<>();
  }

  /**
   * Sets up all variable identifiers from the set of instructions.
   */
  public void populateVars(){
    setupLowerVariables(variables);
  }

  /**
   * Sets the lambda statement for getting the value of the corresponding variable identifier.
   * @param name The name of the variable to set the {@link Getter} for.
   * @param newGetter The new getter for the variable.
   */
  public void setGetterMethod(String name, Getter newGetter){
    variables.replace(name, newGetter);
  }

  /**
   * Executes all instructions in order to determine the output value of this Expression.
   * @return {@link NumberValue}
   * @throws ParseException
   */
  public NumberValue evaluate() throws ParseException {
    Stack<NumberValue> virtualStack = new Stack<>();

    for (Instruction instruction : instructions){
      instruction.execute(virtualStack);
    }

    return virtualStack.pop();
  }

  /**
   * A functional interface for getting the values of variables.
   */
  public interface Getter{

    /**
     * Returns the result of getting a variable
     * @return {@link NumberValue}
     * @throws ParseException
     */
    NumberValue get() throws ParseException;
  }
}

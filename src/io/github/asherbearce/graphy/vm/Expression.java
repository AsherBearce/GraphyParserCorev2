package io.github.asherbearce.graphy.vm;

import io.github.asherbearce.graphy.exception.ParseException;
import io.github.asherbearce.graphy.math.NumberValue;
import io.github.asherbearce.graphy.vm.Instruction.InstructionType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

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
              ((Expression)args[i]).setupLowerVariables(vars);
            }
          }

        }
    );
  }

  public NumberValue getVariable(String name) throws ParseException{
    if (variables.containsKey(name)){
      return variables.get(name).get();
    }
    else{
      return null;
    }
  }

  public String[] getVarNames(){
    return variables.keySet().toArray(new String[0]);
  }

  public Expression(LinkedList<Instruction> instructions){
    this.instructions = instructions;
    variables = new HashMap<>();
  }

  public void populateVars(){
    HashMap<String, Getter> vars = new HashMap<>();
    setupLowerVariables(vars);
    variables = vars;
  }

  public void setGetterMethod(String name, Getter newGetter){
    variables.replace(name, newGetter);
  }

  public NumberValue evaluate() throws ParseException {
    Stack<NumberValue> virtualStack = new Stack<>();

    for (Instruction instruction : instructions){
      instruction.execute(virtualStack);
    }

    return virtualStack.pop();
  }

  public interface Getter{
    NumberValue get() throws ParseException;
  }
}

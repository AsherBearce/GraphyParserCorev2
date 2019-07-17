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
    //Automatically populate variables
    instructions.forEach(
        (instruction) -> {
          if (instruction.getType() == InstructionType.GET){
            variables.put((String)instruction.getArg(0), null);
          }
        }
    );
  }

  public void setGetterMethod(String name, Getter newGetter){
    variables.replace(name, newGetter);
  }

  public NumberValue evaluate() throws ParseException {
    Stack<NumberValue> virtualStack = new Stack<>();

    for (Instruction instruction : instructions){
      System.out.println(instruction.getType());
      instruction.execute(virtualStack);
    }

    return virtualStack.pop();
  }

  public interface Getter{
    NumberValue get() throws ParseException;
  }
}

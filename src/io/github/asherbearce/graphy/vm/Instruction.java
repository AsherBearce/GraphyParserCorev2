package io.github.asherbearce.graphy.vm;

import io.github.asherbearce.graphy.exception.UnkownIdentifierException;
import io.github.asherbearce.graphy.exception.ParseException;
import io.github.asherbearce.graphy.math.NumberValue;
import java.util.Stack;

public class Instruction {
  public enum InstructionType{
    PUSH,
    POP,
    GET,
    CALL,
    ADD,
    SUB,
    MUL,
    DIV,
    POW,
    NEG,
    CONJ
  }

  private final InstructionType instruction;
  private final Object[] args;
  private ComputeEnvironment env;
  private Function caller;

  public Instruction(InstructionType instruction, Object... args){
    this.instruction = instruction;
    this.args = args;
  }

  public void setEnv(ComputeEnvironment env) {
    this.env = env;
  }

  public void setCaller(Function caller) {
    this.caller = caller;
  }

  public void execute(Stack<NumberValue> stack) throws ParseException {
    switch (instruction){
      case PUSH:{
        stack.push((NumberValue)args[0]);
        break;
      }
      case POP:{
        stack.pop();
        break;
      }
      case GET:{
        stack.push(caller.retrieveVariable((String)args[0]));
        break;
      }
      case CALL:{
        //IMPORTANT!! PUSH PARAMETERS ONTO THE STACK IN REVERSE ORDER!!!
        String identifier = (String)args[0];
        Function func = env.getFunction(identifier);
        if (func != null){
          NumberValue[] functionArguments = new NumberValue[func.getNumArgs()];

          for (int i = 0; i < functionArguments.length; i++){
            functionArguments[i] = stack.pop();
          }

          stack.push(func.invoke(functionArguments));
        }
        else{
          throw new UnkownIdentifierException("Unknown identifier: " + identifier);
        }
        break;
      }
      case ADD:{
        NumberValue a = stack.pop();
        NumberValue b = stack.pop();
        stack.push(a.add(b));
        break;
      }
      case SUB:{
        NumberValue a = stack.pop();
        NumberValue b = stack.pop();
        stack.push(a.sub(b));
        break;
      }
      case MUL:{
        NumberValue a = stack.pop();
        NumberValue b = stack.pop();
        stack.push(a.mul(b));
        break;
      }
      case DIV:{
        NumberValue a = stack.pop();
        NumberValue b = stack.pop();
        stack.push(a.div(b));
        break;
      }
      case POW:{
        NumberValue a = stack.pop();
        NumberValue b = stack.pop();
        stack.push(a.pow(b));
        break;
      }
      case NEG:{
        NumberValue a = stack.pop();
        stack.push(a.negative());
        break;
      }
      case CONJ:{
        NumberValue a = stack.pop();
        stack.push(a.conjugate());
        break;
      }
      default:{
        throw new ParseException("Unknown instruction");
      }
    }
  }
}

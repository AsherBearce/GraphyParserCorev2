package io.github.asherbearce.graphy.vm;

import io.github.asherbearce.graphy.exception.UnkownIdentifierException;
import io.github.asherbearce.graphy.exception.ParseException;
import io.github.asherbearce.graphy.math.NumberValue;
import java.lang.reflect.Method;
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
  private Expression exprContainer;

  public Instruction(InstructionType instruction, Object... args){
    this.instruction = instruction;
    this.args = args;
  }

  public void setEnv(ComputeEnvironment env) {
    this.env = env;
  }

  public void setExpression(Expression expression){
    this.exprContainer = expression;
  }

  public Object getArg(int index){
    return args[index];
  }

  public InstructionType getType(){
    return this.instruction;
  }

  private NumberValue convertToCompatibleTypes(NumberValue a, NumberValue b){
    Class<? extends NumberValue> aClass = a.getClass();
    Class<? extends NumberValue> bClass = b.getClass();
    Class<? extends NumberValue> enclosing = a.enclosingType(bClass);

    try{
      Method aConverter = enclosing.getMethod("from", aClass);

      return (NumberValue)aConverter.invoke(null, a);
    }
    catch(ReflectiveOperationException e){
      //Do nothing
      return null;
    }
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
        stack.push(exprContainer.getVariable((String)args[0]));
        break;
      }
      case CALL:{
        String identifier = (String)args[0];
        Function func = env.getFunction(identifier);
        if (func != null){
          Expression[] functionArguments = new Expression[func.getNumArgs()];

          for (int i = 0; i < functionArguments.length; i++){
            functionArguments[i] = (Expression)args[i + 1];
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
        a = convertToCompatibleTypes(a, b);
        b = convertToCompatibleTypes(b, a);

        stack.push(b.add(a));
        break;
      }
      case SUB:{
        NumberValue a = stack.pop();
        NumberValue b = stack.pop();
        a = convertToCompatibleTypes(a, b);
        b = convertToCompatibleTypes(b, a);

        stack.push(b.sub(a));
        break;
      }
      case MUL:{
        NumberValue a = stack.pop();
        NumberValue b = stack.pop();
        a = convertToCompatibleTypes(a, b);
        b = convertToCompatibleTypes(b, a);

        stack.push(b.mul(a));
        break;
      }
      case DIV:{
        NumberValue a = stack.pop();
        NumberValue b = stack.pop();
        a = convertToCompatibleTypes(a, b);
        b = convertToCompatibleTypes(b, a);

        stack.push(b.div(a));
        break;
      }
      case POW:{
        NumberValue a = stack.pop();
        NumberValue b = stack.pop();
        a = convertToCompatibleTypes(a, b);
        b = convertToCompatibleTypes(b, a);

        stack.push(b.pow(a));
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

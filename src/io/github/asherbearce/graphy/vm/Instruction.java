package io.github.asherbearce.graphy.vm;

import io.github.asherbearce.graphy.exception.UnknownIdentifierException;
import io.github.asherbearce.graphy.exception.ParseException;
import io.github.asherbearce.graphy.math.NumberValue;
import java.lang.reflect.Method;
import java.util.Stack;

/**
 * A class for containing all the possible instructions that can be executed.
 */
public class Instruction {

  /**
   * A simple enum for listing all possible instruction types.
   */
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

  /**
   * Constructs a new Instruction object given the instruction type and the arguments that
   * correspond to that instruction.
   * @param instruction The enum instruction type
   * @param args The arguments that correspond to the instruction type.
   */
  public Instruction(InstructionType instruction, Object... args){
    this.instruction = instruction;
    this.args = args;
    env = ComputeEnvironment.getInstance();
  }

  /**
   * Sets the current {@link Expression} holder of this Instruction.
   * @param expression The new {@link Expression} holder to be assigned.
   */
  public void setExpression(Expression expression){
    this.exprContainer = expression;
  }

  /**
   * Returns the argument at a given index of this instruction.
   * @param index The index of the argument to be returned.
   * @return {@link Object}
   */
  public Object getArg(int index){
    return args[index];
  }

  /**
   * Returns the entire array of arguments being used by this instruction
   * @return Object[]
   */
  public Object[] getArgs(){
    return args;
  }

  /**
   * Retrieves the type of this instruction.
   * @return {@link InstructionType}
   */
  public InstructionType getType(){
    return this.instruction;
  }

  /**
   * Converts one {@link NumberValue} to the enclosing subclass of {@link NumberValue} so
   * mathematical operations can be performed on them
   * @param a The {@link NumberValue} to be converted
   * @param b The other {@link NumberValue} to determine the enclosing class of both a and b.
   * @return {@link NumberValue}
   */
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

  /**
   * Executes this instruction given a stack to operate on.
   * @param stack The stack to be used in executing this instruction
   * @throws ParseException
   */
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
        System.out.println(stack.peek().toString());
        break;
      }
      case CALL:{
        String identifier = (String)args[0];
        Invokable func = env.getFunction(identifier);
        if (func != null){
          Expression[] functionArguments = new Expression[func.getNumArgs()];

          for (int i = 0; i < functionArguments.length; i++){
            functionArguments[i] = (Expression)args[i + 1];
          }

          stack.push(func.invoke(functionArguments));
        }
        else{
          throw new UnknownIdentifierException("Unknown identifier: " + identifier);
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

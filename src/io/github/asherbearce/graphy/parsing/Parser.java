package io.github.asherbearce.graphy.parsing;

import io.github.asherbearce.graphy.exception.ParseException;
import io.github.asherbearce.graphy.exception.UnexpectedTokenException;
import io.github.asherbearce.graphy.exception.UnknownIdentifierException;
import io.github.asherbearce.graphy.math.NumberValue;
import io.github.asherbearce.graphy.token.IdentifierToken;
import io.github.asherbearce.graphy.token.NumberToken;
import io.github.asherbearce.graphy.token.OperatorTokens;
import io.github.asherbearce.graphy.token.Token;
import io.github.asherbearce.graphy.token.TokenTypes;
import io.github.asherbearce.graphy.vm.ComputeEnvironment;
import io.github.asherbearce.graphy.vm.Expression;
import io.github.asherbearce.graphy.vm.Function;
import io.github.asherbearce.graphy.vm.Instruction;
import io.github.asherbearce.graphy.vm.Instruction.InstructionType;
import io.github.asherbearce.graphy.vm.Invokable;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * A class that parses tokens into Expressions and Functions.
 */
public class Parser extends TokenHandler {
  private ComputeEnvironment env;

  /**
   * Constructs a new Parser object from a list of {@link Token} and {@link ComputeEnvironment}
   * @param tokens The list of tokens to be parsed
   */
  public Parser(LinkedList<Token> tokens) {
    super(tokens);
    this.env = ComputeEnvironment.getInstance();
  }

  private Expression[] getArgs(Invokable func) throws ParseException {
    int numArgs = func.getNumArgs();
    Expression[] args = new Expression[numArgs];
    int argNum = 0;
    nextToken();

    while (getCurrent().getTokenType() == TokenTypes.NUMBER ||
        getCurrent().getTokenType() == TokenTypes.IDENTIFIER) {
      args[argNum] = parseExpression();

      if (argNum < numArgs - 1){
        expectToken(TokenTypes.COMMA);
        nextToken();
      }

      argNum++;
    }
    expectToken(TokenTypes.CLOSE_PAREN);
    nextToken();

    return args;
  }

  private void parseAtom(LinkedList<Instruction> instructions) throws ParseException {
    TokenTypes thisTokenType = getCurrent().getTokenType();

    if (thisTokenType == TokenTypes.NUMBER) {
      instructions.addLast(new Instruction(InstructionType.PUSH,
          ((NumberToken) getCurrent()).getValue()));
      nextToken();
    } else if (thisTokenType == TokenTypes.OPEN_PAREN) {
      nextToken();
      parseExpression(instructions, 0);
      expectToken(TokenTypes.CLOSE_PAREN);
      nextToken();
    } else if (thisTokenType == TokenTypes.OPERATOR) {
      OperatorTokens op = (OperatorTokens) getCurrent();
      if (op == OperatorTokens.MINUS) {
        instructions.addLast(new Instruction(InstructionType.NEG));
      } else if (op == OperatorTokens.MULTIPLY) {
        instructions.addLast(new Instruction(InstructionType.CONJ));
      }
    } else if(thisTokenType == TokenTypes.IDENTIFIER) {
      String identifierName = ((IdentifierToken) getCurrent()).getValue();
      if (nextToken().getTokenType() == TokenTypes.OPEN_PAREN) {
        //Calling a function
        Invokable func = env.getFunction(identifierName);
        if (func == null){
          throw new UnknownIdentifierException("Unknown identifier: " + identifierName);
        }
        Expression[] exprAgs = getArgs(func);
        Object[] args = new Object[exprAgs.length + 1];
        args[0] = identifierName;

        for (int i = 1; i < args.length; i++){
          args[i] = exprAgs[i - 1];
        }
        instructions.addLast(new Instruction(
            InstructionType.CALL, args
        ));
      } else {
        //Referencing a variable
        instructions.addLast(new Instruction(InstructionType.GET, identifierName));
      }
    }
    else {
      throw new UnexpectedTokenException("Unexpected token, expected number or identifier, got "
          + thisTokenType);
    }
  }

  private void parseExpression(LinkedList<Instruction> instructions, int minPrecedence)
      throws ParseException{
    parseAtom(instructions);

    while (getCurrent().getTokenType() == TokenTypes.OPERATOR &&
        ((OperatorTokens)getCurrent()).getPrecedence() >= minPrecedence){
      OperatorTokens operator = (OperatorTokens)getCurrent();
      int prec = operator.getPrecedence();
      boolean leftAssociative = operator.isLeftAssociative();
      int nextMinPrec;

      if (leftAssociative){
        nextMinPrec = prec + 1;
      }
      else{
        nextMinPrec = prec;
      }

      nextToken();
      parseExpression(instructions, nextMinPrec);

      switch (operator){
        case PLUS:{
          instructions.addLast(new Instruction(InstructionType.ADD));
          break;
        }
        case MINUS:{
          instructions.addLast(new Instruction(InstructionType.SUB));
          break;
        }
        case MULTIPLY:{
          instructions.addLast(new Instruction(InstructionType.MUL));
          break;
        }
        case DIVIDE:{
          instructions.addLast(new Instruction(InstructionType.DIV));
          break;
        }
        case EXPONENT:{
          instructions.addLast(new Instruction(InstructionType.POW));
          break;
        }
      }
    }
  }

  /**
   * Constructs an expression from a single {@link NumberValue} input.
   * @param input The number from which an {@link Expression} should be parsed
   * @return
   */
  public static Expression fromNumber(NumberValue input){
    LinkedList<Instruction> compiledInstructions = new LinkedList<>();
    compiledInstructions.addLast(new Instruction(InstructionType.PUSH, input));
    return new Expression(compiledInstructions);
  }

  private Expression parseExpression() throws ParseException{
    LinkedList<Instruction> compiledInstructions = new LinkedList<>();
    parseExpression(compiledInstructions, 0);
    Expression result = new Expression(compiledInstructions);
    for (Instruction instruction : compiledInstructions){
      instruction.setExpression(result);
    }
    return result;
  }

  /**
   * Parses a new function from a list of {@link Token}.
   * @return {@link Function}
   * @throws ParseException
   */
  public Function parseFunction() throws ParseException{
    Function result = new Function();

    try{
      //This is a named function.
      expectToken(TokenTypes.IDENTIFIER);
      String identifier = ((IdentifierToken)getCurrent()).getValue();
      result.setIdentifier(identifier);
      nextToken();
      if (getCurrent().getTokenType() == TokenTypes.OPEN_PAREN){
        //Declaring 1 or more arguments
        LinkedList<String> args = new LinkedList<>();
        nextToken();

        while (getCurrent().getTokenType() == TokenTypes.IDENTIFIER){
          args.addLast(((IdentifierToken)getCurrent()).getValue());
          nextToken();
          if (getCurrent().getTokenType() != TokenTypes.CLOSE_PAREN){
            expectToken(TokenTypes.COMMA);
            nextToken();
          }
        }
        expectToken(TokenTypes.CLOSE_PAREN);

        String[] argsArray = args.toArray(new String[0]);

        nextToken();
        expectToken(TokenTypes.EQUALS);
        nextToken();
        Expression body = parseExpression();
        body.populateVars();

        if (!Arrays.deepEquals(argsArray, body.getVarNames())){
          throw new ParseException("Arguments do not match detected variables");
        }

        result.setBody(body);
        result.setupParameters(args.toArray(new String[0]));
      }else{
        //Declaring no arguments, declaring a variable.
        expectToken(TokenTypes.EQUALS);
        nextToken();
        Expression body = parseExpression();
        body.populateVars();
        result.setBody(body);
      }
    } catch(ParseException e){
      reset();
      result = new Function();
      Expression body = parseExpression();
      body.populateVars();
      result.setBody(body);
      result.setupParameters(body.getVarNames());
      result.setIdentifier(result.toString());//Give it a pretty much random name.
    }

    return result;
  }
}

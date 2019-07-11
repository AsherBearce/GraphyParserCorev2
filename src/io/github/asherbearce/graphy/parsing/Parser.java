package io.github.asherbearce.graphy.parsing;

import io.github.asherbearce.graphy.exception.ParseException;
import io.github.asherbearce.graphy.exception.UnkownIdentifierException;
import io.github.asherbearce.graphy.math.NumberValue;
import io.github.asherbearce.graphy.token.IdentifierToken;
import io.github.asherbearce.graphy.token.NumberToken;
import io.github.asherbearce.graphy.token.OperatorTokens;
import io.github.asherbearce.graphy.token.Token;
import io.github.asherbearce.graphy.token.TokenTypes;
import io.github.asherbearce.graphy.vm.Function;
import io.github.asherbearce.graphy.vm.Instruction;
import io.github.asherbearce.graphy.vm.Instruction.InstructionType;
import java.util.LinkedList;

public class Parser extends TokenHandler {

  private LinkedList<Instruction> compiledInstructions;

  public Parser(LinkedList<Token> tokens) {
    super(tokens);
    compiledInstructions = new LinkedList<>();
  }

  private NumberValue[] getArgs(Function func) throws ParseException {
    int numArgs = func.getNumArgs();
    NumberValue[] args = new NumberValue[numArgs];
    int argNum = 0;

    while (getCurrent().getTokenType() == TokenTypes.NUMBER ||
        getCurrent().getTokenType() == TokenTypes.IDENTIFIER) {

    }

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
    } else {
      String identifierName = ((IdentifierToken) getCurrent()).getValue();
      if (nextToken().getTokenType() == TokenTypes.OPEN_PAREN) {
        //Calling a function, Not sure how to handle this just yet
        //Also not sure how to handle built in functions just yet either
      } else {
        //Referencing a variable
        instructions.addLast(new Instruction(InstructionType.GET, identifierName));
      }
    }
  }

  public void parseExpression(LinkedList<Instruction> instructions, int minPrecedence)
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

  //TODO Test to make sure that this actually compiles correctly.
}

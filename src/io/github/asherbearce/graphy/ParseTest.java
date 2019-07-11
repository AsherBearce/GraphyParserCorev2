package io.github.asherbearce.graphy;

import io.github.asherbearce.graphy.exception.ParseException;
import io.github.asherbearce.graphy.parsing.Parser;
import io.github.asherbearce.graphy.parsing.Tokenizer;
import io.github.asherbearce.graphy.token.Token;
import io.github.asherbearce.graphy.vm.Instruction;
import java.util.LinkedList;

public class ParseTest {

  public static void main(String[] args) throws ParseException {
    String toCompile = "3 + x * 9 ^ 2";
    Tokenizer tokenizer = new Tokenizer(toCompile);
    LinkedList<Token> tokens = tokenizer.Tokenize();
    LinkedList<Instruction> instructions = new LinkedList<>();
    new Parser(tokens).parseExpression(instructions, 0);
    Instruction[] instructionArray = instructions.toArray(new Instruction[0]);
  }
}

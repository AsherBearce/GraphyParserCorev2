package io.github.asherbearce.graphy;

import io.github.asherbearce.graphy.exception.ParseException;
import io.github.asherbearce.graphy.math.Complex;
import io.github.asherbearce.graphy.math.Dual;
import io.github.asherbearce.graphy.math.NumberValue;
import io.github.asherbearce.graphy.math.Real;
import io.github.asherbearce.graphy.parsing.Parser;
import io.github.asherbearce.graphy.parsing.Tokenizer;
import io.github.asherbearce.graphy.token.Token;
import io.github.asherbearce.graphy.vm.Expression;
import io.github.asherbearce.graphy.vm.Function;
import java.util.LinkedList;

public class ParseTest {

  public static void main(String[] args) throws ParseException {
    String toCompile = "x ^ 2";
    Tokenizer tokenizer = new Tokenizer(toCompile);
    LinkedList<Token> tokens = tokenizer.Tokenize();
    Function test = new Parser(tokens).parseFunction();
    NumberValue result = test.invoke(new Dual(10, 1));
    System.out.println(result.toString());
  }
}

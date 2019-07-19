package io.github.asherbearce.graphy;

import io.github.asherbearce.graphy.exception.ParseException;
import io.github.asherbearce.graphy.math.Real;
import io.github.asherbearce.graphy.parsing.Parser;
import io.github.asherbearce.graphy.parsing.Tokenizer;
import io.github.asherbearce.graphy.token.Token;
import io.github.asherbearce.graphy.vm.ComputeEnvironment;
import io.github.asherbearce.graphy.vm.Function;
import java.util.LinkedList;

public class ParseTest {

  public static void main(String[] args) throws ParseException {
    ComputeEnvironment env = ComputeEnvironment.getInstance();

    String toCompile = "f(x) = x ^ 2";
    Tokenizer tokenizer = new Tokenizer(toCompile);
    LinkedList<Token> tokens = tokenizer.Tokenize();
    Function test = new Parser(tokens, env).parseFunction();
    env.putFunction(test);

    String toParse = "g(y) = derivative(f(y), y, y)";
    Tokenizer tokenizer1 = new Tokenizer(toParse);
    LinkedList<Token> tokens1 = tokenizer1.Tokenize();
    Function test1 = new Parser(tokens1, env).parseFunction();
    System.out.println(test1.invoke(new Real(3)).toString());
  }
}

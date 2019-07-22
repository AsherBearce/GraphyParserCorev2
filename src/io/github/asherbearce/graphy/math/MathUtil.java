package io.github.asherbearce.graphy.math;

/**
 * Implements methods for doing math on dual, complex, and complexdual numbers.
 */
public class MathUtil {
  //TODO implement methods for complexdual types

  private static Real log(Real in){
    return new Real(Math.log(in.getValue()));
  }

  private static Dual log(Dual in){
    double real = Math.log(in.real().getValue());
    double dual = in.dual().getValue() / in.real().getValue();

    return new Dual(real, dual);
  }

  private static Complex log(Complex in){
    double a = in.real().getValue();
    double b = in.imaginary().getValue();
    double real = Math.log(a * a + b * b) / 2;
    double imaginary = Math.atan(b / a);

    return new Complex(real, imaginary);
  }

  /**
   * Takes the natural log of any given {@link NumberValue}.
   * @param in The value to be taken the natural log of.
   * @return {@link NumberValue}
   */
  public static NumberValue log(NumberValue in){
    NumberValue result;

    if (in instanceof Real){
      result = log((Real)in);
    }
    else if(in instanceof Dual){
      result = log((Dual)in);
    }
    else if(in instanceof Complex){
      result = log((Complex)in);
    }
    else{
      result = null;
    }

    return result;
  }

  private static Real log10(Real in){
    return new Real(Math.log10(in.real().getValue()));
  }

  private static Dual log10(Dual in){
    double real = Math.log10(in.real().getValue());
    double imaginary = in.dual().getValue() / (in.real().getValue() * Math.log(10));

    return new Dual(real, imaginary);
  }

  private static Complex log10(Complex in){
    double a = in.real().getValue();
    double b = in.imaginary().getValue();
    double real = Math.log(a * a + b * b) / 2;
    double imaginary = Math.atan(b / a);
    double denom = Math.log(10);

    return new Complex(real / denom, imaginary / denom);
  }

  /**
   * Takes the log base 10 of any given {@link NumberValue}.
   * @param in The value to be taken the log base 10 of.
   * @return {@link NumberValue}
   */
  public static NumberValue log10(NumberValue in){
    NumberValue result;

    if (in instanceof Real){
      result = log10((Real)in);
    }
    else if(in instanceof Dual){
      result = log10((Dual)in);
    }
    else if(in instanceof Complex){
      result = log10((Complex)in);
    }
    else{
      result = null;
    }

    return result;
  }

  private static Real cos(Real in){
    return new Real(Math.cos(in.getValue()));
  }

  private static Dual cos(Dual in){
    double real = Math.cos(in.real().getValue());
    double dual = -Math.sin(in.real().getValue());

    return new Dual(real, in.dual().getValue() * dual);
  }

  private static Complex cos(Complex in){
    double real = Math.cos(in.real().getValue()) * Math.cosh(in.imaginary().getValue());
    double imaginary = -Math.sin(in.real().getValue()) * Math.sinh(in.imaginary().getValue());

    return new Complex(real, imaginary);
  }

  /**
   * Takes the cosine of any given {@link NumberValue}.
   * @param in The value to be taken the cosine of.
   * @return {@link NumberValue}
   */
  public static NumberValue cos(NumberValue in){
    NumberValue result;

    if (in instanceof Real){
      result = cos((Real)in);
    }
    else if(in instanceof Dual){
      result = cos((Dual)in);
    }
    else if(in instanceof Complex){
      result = cos((Complex)in);
    }
    else{
      result = null;
    }

    return result;
  }

  private static Real sin(Real in){
    return new Real(Math.sin(in.getValue()));
  }

  private static Dual sin(Dual in){
    double real = Math.sin(in.real().getValue());
    double dual = Math.cos(in.real().getValue());

    return new Dual(real, in.real().getValue() * dual);
  }

  private static Complex sin(Complex in){
    double real = Math.sin(in.real().getValue()) * Math.cosh(in.imaginary().getValue());
    double imaginary = Math.cos(in.real().getValue()) * Math.sinh(in.imaginary().getValue());

    return new Complex(real, imaginary);
  }

  /**
   * Takes the sine of any given {@link NumberValue}.
   * @param in The value to be taken the sine of.
   * @return {@link NumberValue}
   */
  public static NumberValue sin(NumberValue in){
    NumberValue result;

    if (in instanceof Real){
      result = sin((Real)in);
    }
    else if(in instanceof Dual){
      result = sin((Dual)in);
    }
    else if(in instanceof Complex){
      result = sin((Complex)in);
    }
    else{
      result = null;
    }

    return result;
  }

  private static Real tan(Real in){
    return new Real(Math.tan(in.getValue()));
  }

  private static Dual tan(Dual in){
    double real = Math.tan(in.real().getValue());
    double sec = 1 / Math.cos(in.real().getValue());
    double dual = in.dual().getValue() * sec * sec;

    return new Dual(real, dual);
  }

  private static Complex tan(Complex in){
    double denom = Math.cos(2 * in.real().getValue()) + Math.cosh(2 * in.imaginary().getValue());
    double real = Math.sin(2 * in.real().getValue()) / denom;
    double imaginary = Math.sinh(2 * in.imaginary().getValue()) / denom;

    return new Complex(real, imaginary);
  }

  /**
   * Takes the tangent of any given {@link NumberValue}.
   * @param in The value to be taken the tangent of.
   * @return {@link NumberValue}
   */
  public static NumberValue tan(NumberValue in){
    NumberValue result;

    if (in instanceof Real){
      result = tan((Real)in);
    }
    else if(in instanceof Dual){
      result = tan((Dual)in);
    }
    else if(in instanceof Complex){
      result = tan((Complex)in);
    }
    else{
      result = null;
    }

    return result;
  }
}

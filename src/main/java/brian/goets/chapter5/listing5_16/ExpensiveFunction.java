package brian.goets.chapter5.listing5_16;

import java.math.BigInteger;

class ExpensiveFunction implements Computable<String, BigInteger> {

  @Override
  public BigInteger compute(String arg) {
    // after deep thought...
    return new BigInteger(arg);
  }
}
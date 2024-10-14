package brian.goets.chapter2.listing3_12;

import net.jcip.annotations.Immutable;

import java.math.BigInteger;
import java.util.Arrays;

@Immutable
class OneValueCache {

  private final BigInteger lastNumber;
  private final BigInteger[] lastFactors;

  public OneValueCache(BigInteger i, BigInteger[] factors) {
    lastNumber = i;
    lastFactors = Arrays.copyOf(factors, factors.length);
  }

  public BigInteger[] getFactors(BigInteger i) {
    if (lastNumber == null || !lastNumber.equals(i)) {
      return null;
    } else {
      return Arrays.copyOf(lastFactors, lastFactors.length);
    }
  }
}

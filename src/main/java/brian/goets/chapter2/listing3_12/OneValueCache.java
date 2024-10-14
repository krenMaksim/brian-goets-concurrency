package brian.goets.chapter2.listing3_12;

import net.jcip.annotations.Immutable;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static java.util.function.Predicate.isEqual;

@Immutable
public class OneValueCache {

  private static final OneValueCache NULL_CACHE = new OneValueCache();

  public static OneValueCache newNullCache() {
    return NULL_CACHE;
  }

  private final BigInteger lastNumber;
  private final BigInteger[] lastFactors;

  private OneValueCache() {
    lastNumber = null;
    lastFactors = null;
  }

  public OneValueCache(BigInteger lastNumber, BigInteger[] factors) {
    Objects.requireNonNull(lastNumber);
    Objects.requireNonNull(factors);
    this.lastNumber = lastNumber;
    this.lastFactors = newCopy(factors);
  }

  public BigInteger[] getFactors(BigInteger lastNumber) {
    return Optional.ofNullable(lastNumber)
        .filter(isEqual(this.lastNumber))
        .map(number -> newCopy(lastFactors))
        .orElse(null);
  }

  private static BigInteger[] newCopy(BigInteger[] array) {
    return Arrays.copyOf(array, array.length);
  }
}

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

  private final BigInteger number;
  private final BigInteger[] factors;

  private OneValueCache() {
    number = null;
    factors = null;
  }

  public OneValueCache(BigInteger number, BigInteger[] factors) {
    Objects.requireNonNull(number);
    Objects.requireNonNull(factors);
    this.number = number;
    this.factors = newCopy(factors);
  }

  public BigInteger[] getFactors(BigInteger number) {
    return Optional.ofNullable(number)
        .filter(isEqual(this.number))
        .map(num -> newCopy(factors))
        .orElse(null);
  }

  private static BigInteger[] newCopy(BigInteger[] array) {
    return Arrays.copyOf(array, array.length);
  }
}

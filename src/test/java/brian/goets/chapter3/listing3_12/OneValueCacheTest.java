package brian.goets.chapter3.listing3_12;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigInteger;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OneValueCacheTest {

  private static Stream<Arguments> numbersForNullCache() {
    return Stream.of(
        Arguments.of(BigInteger.valueOf(42))
    );
  }

  @NullSource
  @ParameterizedTest
  @MethodSource("numbersForNullCache")
  void nullOneValueCacheInitialization(BigInteger number) {
    OneValueCache nullCache = OneValueCache.newNullCache();

    BigInteger[] factors = nullCache.getFactors(number);

    assertThat(factors).isNull();
  }

  private static Stream<Arguments> invalidParameters() {
    return Stream.of(
        Arguments.of(BigInteger.valueOf(42), null),
        Arguments.of(null, new BigInteger[] {BigInteger.valueOf(42)}),
        Arguments.of(null, null)
    );
  }

  @ParameterizedTest
  @MethodSource("invalidParameters")
  void tryToCreateOneValueWithInvalidParameters(BigInteger number, BigInteger[] factors) {
    assertThatThrownBy(() -> new OneValueCache(number, factors))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void getFactors() {
    BigInteger number = BigInteger.valueOf(42);
    BigInteger[] factors = new BigInteger[] {BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3)};
    OneValueCache cache = new OneValueCache(number, factors);

    BigInteger[] result = cache.getFactors(number);

    assertThat(result).isEqualTo(factors);
    assertThat(result).isNotSameAs(factors);
  }

  @Test
  void getFactorsInCaseMissingCache() {
    BigInteger number = BigInteger.valueOf(42);
    BigInteger[] factors = new BigInteger[] {BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3)};
    OneValueCache cache = new OneValueCache(BigInteger.valueOf(43), factors);

    BigInteger[] result = cache.getFactors(number);

    assertThat(result).isNull();
  }
}
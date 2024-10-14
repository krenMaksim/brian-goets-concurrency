package brian.goets.chapter2.listing3_12;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigInteger;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class OneValueCacheTest {

  private static Stream<Arguments> lastNumbersForNullCache() {
    return Stream.of(
        Arguments.of(BigInteger.valueOf(42))
    );
  }

  @NullSource
  @ParameterizedTest
  @MethodSource("lastNumbersForNullCache")
  void nullOneValueCacheInitialization(BigInteger number) {
    OneValueCache nullCache = OneValueCache.newNullCache();

    BigInteger[] factors = nullCache.getFactors(number);

    assertThat(factors).isNull();
  }
}
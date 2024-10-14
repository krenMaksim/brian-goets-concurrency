package brian.goets.chapter2.listing3_12;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;

class OneValueCacheTest {

  private static Stream<Arguments> nullValues() {
    return Stream.of(
        Arguments.of(null, null),
        Arguments.of(BigInteger.valueOf(42), null),
        Arguments.of(null, new BigInteger[] {BigInteger.valueOf(42), BigInteger.valueOf(43)})
    );
  }

  @ParameterizedTest
  @MethodSource("nullValues")
  void nullValuesProvided(BigInteger lastNumber, BigInteger[] factors) {
    assertThatCode(() -> new OneValueCache(null, null))
        .doesNotThrowAnyException();
  }
}
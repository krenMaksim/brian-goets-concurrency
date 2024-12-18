package brian.goets.chapter2.listing2_5;

import brian.goets.chapter3.listing3_13.VolatileCachedFactorizer;
import org.junit.jupiter.api.RepeatedTest;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import static brian.goets.chapter2.listing2_5.ServletHelper.extractFactors;
import static brian.goets.chapter2.listing2_5.ServletHelper.newServletRequestWithFactorNumber;
import static brian.goets.chapter2.listing2_5.ServletHelper.newServletResponse;
import static brian.goets.test.util.TaskIterator.executeNumberOfTimes;
import static org.assertj.core.api.Assertions.assertThat;

class CachingFactorizerTest {

  private static final int NUMBER_OF_ITERATIONS = 10_000;

  @RepeatedTest(10)
  void doGivenNumberOfRequestsOverUnsafeCachingFactorizer() throws InterruptedException {
    CachingFactorizer factorizer = new UnsafeCachingFactorizer();

    List<Result> results = doGivenNumberOfConcurrentIterations(factorizer, NUMBER_OF_ITERATIONS);

    results.forEach(result -> assertThat(result.factors).containsExactly(result.factorNumber));
  }

  @RepeatedTest(10)
  void doGivenNumberOfRequestsOverSyncCachingFactorizer() throws InterruptedException {
    CachingFactorizer factorizer = new SyncCachingFactorizer();

    List<Result> results = doGivenNumberOfConcurrentIterations(factorizer, NUMBER_OF_ITERATIONS);

    results.forEach(result -> assertThat(result.factors).containsExactly(result.factorNumber));
  }

  @RepeatedTest(10)
  void doGivenNumberOfRequestsOverVolatileCachedFactorizer() throws InterruptedException {
    CachingFactorizer factorizer = new VolatileCachedFactorizer();

    List<Result> results = doGivenNumberOfConcurrentIterations(factorizer, NUMBER_OF_ITERATIONS);

    results.forEach(result -> assertThat(result.factors).containsExactly(result.factorNumber));
  }

  @RepeatedTest(10)
  void doGivenNumberOfRequestsOverMemoizerCachingFactorizer() throws InterruptedException {
    CachingFactorizer factorizer = new MemoizerCachingFactorizer();

    List<Result> results = doGivenNumberOfConcurrentIterations(factorizer, NUMBER_OF_ITERATIONS);

    results.forEach(result -> assertThat(result.factors).containsExactly(result.factorNumber));
  }

  private List<Result> doGivenNumberOfConcurrentIterations(CachingFactorizer factorizer, int iterations) throws InterruptedException {
    return executeNumberOfTimes(() -> {
      BigInteger factorNumber = newRandomBigInteger();
      ServletRequest request = newServletRequestWithFactorNumber(factorNumber);
      ServletResponse response = newServletResponse();

      factorizer.service(request, response);
      BigInteger[] factors = extractFactors(response);

      return new Result(factorNumber, factors);
    }, iterations);
  }

  private static BigInteger newRandomBigInteger() {
    int min = 42;
    int max = 44;
    int random = ThreadLocalRandom.current().nextInt(min, max + 1);
    return new BigInteger(String.valueOf(random));
  }

  private record Result(BigInteger factorNumber, BigInteger[] factors) {}
}
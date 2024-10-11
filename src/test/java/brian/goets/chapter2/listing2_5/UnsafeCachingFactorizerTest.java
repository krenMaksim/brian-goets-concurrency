package brian.goets.chapter2.listing2_5;

import org.junit.jupiter.api.RepeatedTest;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import static brian.goets.test.util.TaskIterator.executeNumberOfTimes;
import static org.assertj.core.api.Assertions.assertThat;

class UnsafeCachingFactorizerTest {

  private static final int NUMBER_OF_ITERATIONS = 100;

  @RepeatedTest(10)
  void doGivenNumberOfRequestsOverUnsafeCachingFactorizer() throws InterruptedException {
    UnsafeCachingFactorizer factorizer = new UnsafeCachingFactorizer();

    List<Result> results = doGivenNumberOfConcurrentInitializations(factorizer, NUMBER_OF_ITERATIONS);

    results.forEach(result -> assertThat(result.factors).containsExactly(result.factorNumber));
  }

  private List<Result> doGivenNumberOfConcurrentInitializations(UnsafeCachingFactorizer factorizer, int iterations) throws InterruptedException {
    return executeNumberOfTimes(() -> {
      String number = String.valueOf(ThreadLocalRandom.current().nextInt(10, 12 + 1));
      ServletRequest request = ServletHelper.newServletRequestWithFactorNumber(new BigInteger(number));
      ServletResponse response = ServletHelper.newServletResponse();

      factorizer.service(request, response);
      BigInteger[] factors = ServletHelper.extractFactors(response);
      return new Result(new BigInteger(number), factors);
    }, iterations);
  }

  private record Result(BigInteger factorNumber, BigInteger[] factors) {}
}
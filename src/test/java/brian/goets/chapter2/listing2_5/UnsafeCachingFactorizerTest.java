package brian.goets.chapter2.listing2_5;

import brian.goets.test.util.TaskIterator;
import org.junit.jupiter.api.RepeatedTest;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import static brian.goets.test.util.TaskIterator.submitForExecutionForNumberOfTimes;
import static org.assertj.core.api.Assertions.assertThat;

class UnsafeCachingFactorizerTest {

  private static final int NUMBER_OF_ITERATIONS = 100;

  @RepeatedTest(10)
  void doGivenNumberOfRequestsOverUnsafeCachingFactorizer() throws InterruptedException {
    UnsafeCachingFactorizer factorizer = new UnsafeCachingFactorizer();

    List<Result> results = doGivenNumberOfConcurrentInitializations(factorizer, NUMBER_OF_ITERATIONS);

    results.forEach(res -> res.assertThatInputMatchesResult());
  }

  private List<Result> doGivenNumberOfConcurrentInitializations(UnsafeCachingFactorizer factorizer, int iterations) throws InterruptedException {
    List<Result> createdInstances =
        submitForExecutionForNumberOfTimes(() -> {
          String number = String.valueOf(ThreadLocalRandom.current().nextInt(10, 12 + 1));
          ServletRequest request = ServletHelper.newServletRequestWithFactorNumber(new BigInteger(number));
          ServletResponse response = ServletHelper.newServletResponse();

          factorizer.service(request, response);
          BigInteger[] factors = ServletHelper.extractFactors(response);
          return new Result(new BigInteger(number), factors);
        }, iterations).stream()
            .map(TaskIterator::getTaskResult)
            .collect(Collectors.toList());
    return createdInstances;
  }

  static class Result {

    private final BigInteger factorNumber;
    private final BigInteger[] factors;

    public Result(BigInteger factorNumber, BigInteger[] factors) {
      this.factorNumber = factorNumber;
      this.factors = factors;
    }

    public void assertThatInputMatchesResult() {
      assertThat(factors).isEqualTo(new BigInteger[] {factorNumber});
    }
  }
}
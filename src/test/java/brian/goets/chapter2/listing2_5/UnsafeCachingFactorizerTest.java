package brian.goets.chapter2.listing2_5;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import static brian.goets.test.util.TaskIterator.submitForExecutionForNumberOfTimes;
import static org.assertj.core.api.Assertions.assertThat;

class UnsafeCachingFactorizerTest {

  private static final int NUMBER_OF_ITERATIONS = 10_000;

  @Test
  void doSomething() throws IOException {
    UnsafeCachingFactorizer factorizer = new UnsafeCachingFactorizer();
    ServletRequest request = ServletHelper.newServletRequestWithFactorNumber(new BigInteger("42"));
    ServletResponse response = ServletHelper.newServletResponse();

    factorizer.service(request, response);
    BigInteger[] factors = ServletHelper.extractFactors(response);

    assertThat(factors).isEqualTo(new BigInteger[] {new BigInteger("42")});
  }

  @RepeatedTest(1)
  void doGivenNumberOfRequestsOverUnsafeCachingFactorizer() throws InterruptedException {
    UnsafeCachingFactorizer factorizer = new UnsafeCachingFactorizer();

    submitForExecutionForNumberOfTimes(() -> {
      String number = String.valueOf(ThreadLocalRandom.current().nextInt(10, 12 + 1));
      System.out.println("Generated " + number);
      ServletRequest request = ServletHelper.newServletRequestWithFactorNumber(new BigInteger(number));
      ServletResponse response = ServletHelper.newServletResponse();

      factorizer.service(request, response);
      BigInteger[] factors = ServletHelper.extractFactors(response);

      assertThat(factors).isEqualTo(new BigInteger[] {new BigInteger("44")});
      return true;
    }, NUMBER_OF_ITERATIONS);
  }

  class Result {

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
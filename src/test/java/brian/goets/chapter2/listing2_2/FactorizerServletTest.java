package brian.goets.chapter2.listing2_2;

import org.junit.jupiter.api.RepeatedTest;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import static brian.goets.test.util.TaskIterator.submitForExecutionForNumberOfTimes;
import static org.assertj.core.api.Assertions.assertThat;

class FactorizerServletTest {

  private static final int NUMBER_OF_ITERATIONS = 10_000;

  @RepeatedTest(10)
  void doGivenNumberOfIterationsOverUnsafeCountingFactorizer() throws InterruptedException {
    FactorizerServlet factorizer = new UnsafeCountingFactorizer();

    long counter = doGivenNumberOfConcurrentIterations(factorizer, NUMBER_OF_ITERATIONS);

    assertThat(counter).isNotEqualTo(NUMBER_OF_ITERATIONS);
  }

  @RepeatedTest(10)
  void doGivenNumberOfIterationsOverAtomicCountingFactorizer() throws InterruptedException {
    FactorizerServlet factorizer = new AtomicCountingFactorizer();

    long counter = doGivenNumberOfConcurrentIterations(factorizer, NUMBER_OF_ITERATIONS);

    assertThat(counter).isEqualTo(NUMBER_OF_ITERATIONS);
  }

  private long doGivenNumberOfConcurrentIterations(FactorizerServlet factorizer, int iterations) throws InterruptedException {
    ServletRequest reqDummy = null;
    ServletResponse respDummy = null;
    submitForExecutionForNumberOfTimes(() -> {
      factorizer.service(reqDummy, respDummy);
      return true;
    }, iterations);
    long counter = factorizer.getCount();
    System.out.println("Counter: " + counter);
    return counter;
  }
}
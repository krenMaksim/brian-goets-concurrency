package brian.goets.chapter2.listing2_2;

import org.junit.jupiter.api.RepeatedTest;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class UnsafeCountingFactorizerTest {

  private static final int NUMBER_OF_ITERATIONS = 10_000;
  private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

  private ExecutorService exec = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS);

  @RepeatedTest(10)
  void sendGivenNumberRequests() throws InterruptedException {
    UnsafeCountingFactorizer factorizer = new UnsafeCountingFactorizer();

    long counter = doGivenNumberOfConcurrentIterations(factorizer, NUMBER_OF_ITERATIONS);

    assertThat(counter).isNotEqualTo(NUMBER_OF_ITERATIONS);
  }

  private long doGivenNumberOfConcurrentIterations(UnsafeCountingFactorizer factorizer, int iterations) throws InterruptedException {
    ServletRequest req = null;
    ServletResponse resp = null;
    submitForExecutionForNumberOfTimes(() -> {
      factorizer.service(req, resp);
      return true;
    }, iterations);
    long counter = factorizer.getCount();
    System.out.println("Counter: " + counter);
    return counter;
  }

  private <T> void submitForExecutionForNumberOfTimes(Callable<T> task, int numberOfIterations) throws InterruptedException {
    List<Callable<T>> tasks = IntStream.rangeClosed(1, numberOfIterations)
        .mapToObj(i -> task)
        .collect(Collectors.toList());

    exec.invokeAll(tasks);

    exec.shutdown();
    exec.awaitTermination(30, TimeUnit.SECONDS);
  }
}
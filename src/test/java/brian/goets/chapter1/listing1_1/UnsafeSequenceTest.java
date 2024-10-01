package brian.goets.chapter1.listing1_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class UnsafeSequenceTest {

  private static final int NUMBER_OF_ITERATIONS = 10_000;

  private ExecutorService exec;

  @BeforeEach
  void setUp() {
    int availableProcessors = Runtime.getRuntime().availableProcessors();
    exec = Executors.newFixedThreadPool(availableProcessors);
  }

  @RepeatedTest(10)
  void doGivenNumberOfIterationsOverUnsafeSequence() throws InterruptedException {
    UnsafeSequence unsafeSequence = new UnsafeSequence();

    submitForExecutionForNumberOfTimes(() -> unsafeSequence.getNext(), NUMBER_OF_ITERATIONS);
    int result = unsafeSequence.getNext();

    System.out.println("Result: " + result);
    assertThat(result).isNotEqualTo(NUMBER_OF_ITERATIONS);
  }

  @RepeatedTest(10)
  void doGivenNumberOfIterationsOverUnsafeVolatileSequence() throws InterruptedException {
    UnsafeVolatileSequence unsafeSequence = new UnsafeVolatileSequence();

    submitForExecutionForNumberOfTimes(() -> unsafeSequence.getNext(), NUMBER_OF_ITERATIONS);
    int result = unsafeSequence.getNext();

    System.out.println("Result: " + result);
    assertThat(result).isNotEqualTo(NUMBER_OF_ITERATIONS);
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
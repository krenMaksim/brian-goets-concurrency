package brian.goets.chapter1.listing1_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class SequenceTest {

  private static final int NUMBER_OF_ITERATIONS = 10_000;

  private ExecutorService exec;

  @BeforeEach
  void setUp() {
    int availableProcessors = Runtime.getRuntime().availableProcessors();
    exec = Executors.newFixedThreadPool(availableProcessors);
  }

  @Nested
  class NotThreadSafeSequenceTest {

    @RepeatedTest(10)
    void doGivenNumberOfIterationsOverUnsafeSequence() throws InterruptedException {
      Sequence sequence = new UnsafeSequence();

      int lastNumber = doGivenNumberOfConcurrentIterations(sequence, NUMBER_OF_ITERATIONS);

      assertThat(lastNumber).isNotEqualTo(NUMBER_OF_ITERATIONS);
    }

    @RepeatedTest(10)
    void doGivenNumberOfIterationsOverUnsafeVolatileSequence() throws InterruptedException {
      Sequence sequence = new UnsafeVolatileSequence();

      int lastNumber = doGivenNumberOfConcurrentIterations(sequence, NUMBER_OF_ITERATIONS);

      assertThat(lastNumber).isNotEqualTo(NUMBER_OF_ITERATIONS);
    }
  }

  @Nested
  class ThreadSafeSequenceTest {

    @RepeatedTest(10)
    void doGivenNumberOfIterationsOverSynchronizedSequence() throws InterruptedException {
      Sequence sequence = new SynchronizedSequence();

      int lastNumber = doGivenNumberOfConcurrentIterations(sequence, NUMBER_OF_ITERATIONS);

      assertThat(lastNumber).isEqualTo(NUMBER_OF_ITERATIONS);
    }

    @RepeatedTest(10)
    void doGivenNumberOfIterationsOverAtomicSequence() throws InterruptedException {
      Sequence sequence = new AtomicSequence();

      int lastNumber = doGivenNumberOfConcurrentIterations(sequence, NUMBER_OF_ITERATIONS);

      assertThat(lastNumber).isEqualTo(NUMBER_OF_ITERATIONS);
    }

    @RepeatedTest(10)
    void doGivenNumberOfIterationsOverSyncBySemaphoreSequence() throws InterruptedException {
      Sequence sequence = new SyncBySemaphoreSequence();

      int lastNumber = doGivenNumberOfConcurrentIterations(sequence, NUMBER_OF_ITERATIONS);

      assertThat(lastNumber).isEqualTo(NUMBER_OF_ITERATIONS);
    }

    @RepeatedTest(10)
    void doGivenNumberOfIterationsOverSyncViaBlockSequence() throws InterruptedException {
      Sequence sequence = new SyncViaBlockSequence();

      int lastNumber = doGivenNumberOfConcurrentIterations(sequence, NUMBER_OF_ITERATIONS);

      assertThat(lastNumber).isEqualTo(NUMBER_OF_ITERATIONS);
    }

    // TBD Lock lock = new ReentrantLock();
  }

  private int doGivenNumberOfConcurrentIterations(Sequence sequence, int iterations) throws InterruptedException {
    submitForExecutionForNumberOfTimes(() -> sequence.getNext(), iterations);
    int lastNumber = sequence.getNext();
    System.out.println("LastNumber: " + lastNumber);
    return lastNumber;
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
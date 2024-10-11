package brian.goets.chapter1.listing1_1;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;

import static brian.goets.test.util.TaskIterator.submitForExecutionNumberOfTimes;
import static org.assertj.core.api.Assertions.assertThat;

class SequenceTest {

  private static final int NUMBER_OF_ITERATIONS = 10_000;

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

    @RepeatedTest(10)
    void doGivenNumberOfIterationsOverSyncByReentrantLockSequence() throws InterruptedException {
      Sequence sequence = new SyncByReentrantLockSequence();

      int lastNumber = doGivenNumberOfConcurrentIterations(sequence, NUMBER_OF_ITERATIONS);

      assertThat(lastNumber).isEqualTo(NUMBER_OF_ITERATIONS);
    }

    @RepeatedTest(10)
    void doGivenNumberOfIterationsOverSyncByCasSequence() throws InterruptedException {
      Sequence sequence = new SyncByCasSequence();

      int lastNumber = doGivenNumberOfConcurrentIterations(sequence, NUMBER_OF_ITERATIONS);

      assertThat(lastNumber).isEqualTo(NUMBER_OF_ITERATIONS);
    }
  }

  private int doGivenNumberOfConcurrentIterations(Sequence sequence, int iterations) throws InterruptedException {
    submitForExecutionNumberOfTimes(() -> sequence.getNext(), iterations);
    int lastNumber = sequence.getNext();
    System.out.println("LastNumber: " + lastNumber);
    return lastNumber;
  }
}
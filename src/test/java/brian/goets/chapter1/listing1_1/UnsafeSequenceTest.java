package brian.goets.chapter1.listing1_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class UnsafeSequenceTest {

  private ExecutorService exec;

  @BeforeEach
  void setUp() {
    int availableProcessors = Runtime.getRuntime().availableProcessors();
    exec = Executors.newFixedThreadPool(availableProcessors);
  }

  //
  //  @Test
  @RepeatedTest(10)
  void getNext() throws InterruptedException {
    int numberOfIterations = 1_000;
    UnsafeSequence unsafeSequence = new UnsafeSequence();

    IntStream.rangeClosed(1, numberOfIterations).forEach(i -> exec.execute(() -> unsafeSequence.getNext()));
    exec.shutdown();
    exec.awaitTermination(30, TimeUnit.SECONDS);
    int result = unsafeSequence.getNext();

    assertThat(result).isNotEqualTo(numberOfIterations);
  }
}
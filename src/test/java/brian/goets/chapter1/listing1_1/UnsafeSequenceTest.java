package brian.goets.chapter1.listing1_1;

import org.junit.jupiter.api.RepeatedTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class UnsafeSequenceTest {

  //
  //  @Test
  @RepeatedTest(10)
  void getNext() {
    int cores = Runtime.getRuntime().availableProcessors();
    ExecutorService exec = Executors.newFixedThreadPool(cores);

    var unsafeSequence = new UnsafeSequence();

    Runnable task = () -> {
      unsafeSequence.getNext();
      //      System.out.println(Thread.currentThread().getName() + " " + unsafeSequence.getNext());
    };

    int numberOfIterations = 1_000;
    IntStream.rangeClosed(1, numberOfIterations).forEach(i -> exec.execute(task));

    exec.shutdown();

    while (!exec.isTerminated()) {
      // Noop. Waiting
    }

    //    System.out.println(exec.isTerminated());
    int result = unsafeSequence.getNext();
    //    System.out.println(result);

    assertThat(result).isNotEqualTo(numberOfIterations);
  }
}
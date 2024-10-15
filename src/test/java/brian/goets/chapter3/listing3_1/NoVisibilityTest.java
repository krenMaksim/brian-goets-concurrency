package brian.goets.chapter3.listing3_1;

import org.junit.jupiter.api.RepeatedTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/*
  In both cases I could not reproduce visibility problem described in the book.
  What might be considered as another prove that concurrency issues difficult to spot.
 */
class NoVisibilityTest {

  private static final int NUMBER_OF_ITERATIONS = 10_000;

  @RepeatedTest(100)
  void tryToSetStaticVariables() throws InterruptedException {
    ExecutorService exec = Executors.newFixedThreadPool(20);

    IntStream.rangeClosed(1, NUMBER_OF_ITERATIONS)
        .forEach(i -> exec.execute(new NoVisibility.ReaderThread()));

    NoVisibility.number = 42;
    NoVisibility.ready = true;

    exec.shutdown();
    exec.awaitTermination(30, TimeUnit.SECONDS);
  }

  @RepeatedTest(NUMBER_OF_ITERATIONS)
  void executeMainMethod() {
    NoVisibility.main(null);
  }
}
package brian.goets.chapter2.listing3_1;

import brian.goets.test.util.TaskIterator;
import org.junit.jupiter.api.RepeatedTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

class NoVisibilityTest {

  @RepeatedTest(100)
  void tryToSetStaticVariables() throws InterruptedException {
    Thread thread = new NoVisibility.ReaderThread();
    thread.start();
    NoVisibility.number = 42;
    NoVisibility.ready = true;
    thread.join();
  }

  @RepeatedTest(100)
  void tryToSetStaticVariables2() throws InterruptedException {
    //    new NoVisibility.ReaderThread().start();

    TaskIterator.submitForExecutionNumberOfTimes(() -> {
      new NoVisibility.ReaderThread().run();
      return true;
    }, 1000);

    NoVisibility.number = 42;
    NoVisibility.ready = true;
  }

  @RepeatedTest(100)
  void tryToSetStaticVariables3() throws InterruptedException {
    //    new NoVisibility.ReaderThread().start();

    Runnable ru = () -> {
      try {
        TaskIterator.submitForExecutionNumberOfTimes(() -> {
          new NoVisibility.ReaderThread().run();
          return true;
        }, 1000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    };
    Thread thread = new Thread(ru);
    thread.start();

    NoVisibility.number = 42;
    NoVisibility.ready = true;
    System.out.println("ddddd");
    thread.join();
  }

  @RepeatedTest(100)
  void tryToSetStaticVariables4() throws InterruptedException {
    ExecutorService exec = Executors.newFixedThreadPool(10);

    System.out.println("execute");
    IntStream.rangeClosed(1, 100000)
        .forEach(i -> exec.execute(new NoVisibility.ReaderThread()));
    System.out.println("after execute");

    TimeUnit.NANOSECONDS.sleep(100);

    NoVisibility.number = 42;
    NoVisibility.ready = true;
    System.out.println("ddddd");

    exec.shutdown();
    exec.awaitTermination(30, TimeUnit.SECONDS);
  }
}
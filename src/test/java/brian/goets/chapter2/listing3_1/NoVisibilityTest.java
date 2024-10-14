package brian.goets.chapter2.listing3_1;

import brian.goets.test.util.TaskIterator;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
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

  // TBD mention that I could not reproduce expected issue via test
  @RepeatedTest(100)
  void tryToSetStaticVariables4() throws InterruptedException {
    ExecutorService exec = Executors.newFixedThreadPool(20);

    System.out.println("execute");
    IntStream.rangeClosed(1, 1000)
        .forEach(i -> exec.execute(new NoVisibility.ReaderThread()));
    System.out.println("after execute");

    TimeUnit.NANOSECONDS.sleep(100);

    NoVisibility.number = 42;
    NoVisibility.ready = true;
    System.out.println("ddddd");

    exec.shutdown();
    exec.awaitTermination(30, TimeUnit.SECONDS);
  }

  @RepeatedTest(10)
  void tryToSetStaticVariables5() throws InterruptedException {
    ExecutorService exec = Executors.newFixedThreadPool(10);

    Callable<Boolean> task = () -> {
      new NoVisibility.ReaderThread().run();
      return true;
    };

    Callable<Boolean> task2 = () -> {
      NoVisibility.number = 42;
      NoVisibility.ready = true;
      return true;
    };

    List<Callable<Boolean>> tasks = IntStream.rangeClosed(1, 9)
        .mapToObj(i -> task)
        .collect(Collectors.toList());

    tasks.add(task2);

    System.out.println("execute");
    exec.invokeAll(tasks);
    System.out.println("after execute");

    exec.shutdown();
    exec.awaitTermination(30, TimeUnit.SECONDS);
  }

  // TBD it might be option 2
  @RepeatedTest(10_000)
  void tryToSetStaticVariables7() {
    NoVisibility.main(null);
  }
}
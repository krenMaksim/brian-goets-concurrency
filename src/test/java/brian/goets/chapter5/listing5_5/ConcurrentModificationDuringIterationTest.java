package brian.goets.chapter5.listing5_5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static brian.goets.test.util.TaskIterator.AVAILABLE_PROCESSORS;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConcurrentModificationDuringIterationTest {

  private ExecutorService exec;

  @BeforeEach
  void setUp() {
    exec = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS);
  }

  @Test
  void iterateOverSynchronizedList() throws ExecutionException {
    List<Widget> widgetList = IntStream.rangeClosed(1, 10_000)
        .mapToObj(i -> new Widget())
        .collect(Collectors.toCollection(() -> Collections.synchronizedList(new ArrayList<>())));

    Future<?> future = exec.submit(() -> {
      for (Widget w : widgetList) {
        System.out.println(w);
      }
    });

    exec.submit(() -> {
      widgetList.remove(9_000);
    });

    assertThatThrownBy(() -> future.get())
        .isInstanceOf(ExecutionException.class)
        .hasCauseInstanceOf(ConcurrentModificationException.class);
  }

  @AfterEach
  void tearDown() throws InterruptedException {
    exec.shutdown();
    exec.awaitTermination(30, TimeUnit.SECONDS);
  }

  private static class Widget {}
}

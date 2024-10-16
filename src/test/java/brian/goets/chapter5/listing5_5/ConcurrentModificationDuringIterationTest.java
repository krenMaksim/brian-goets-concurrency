package brian.goets.chapter5.listing5_5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static brian.goets.test.util.TaskIterator.AVAILABLE_PROCESSORS;
import static java.util.Collections.synchronizedList;
import static java.util.stream.Collectors.toCollection;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConcurrentModificationDuringIterationTest {

  private ExecutorService exec;
  private Stream<Widget> widgetStream;

  @BeforeEach
  void setUp() {
    exec = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS);
    widgetStream = IntStream.rangeClosed(1, 10_000)
        .mapToObj(i -> new Widget());
  }

  @Test
  void iterateOverSynchronizedList() throws ExecutionException {
    List<Widget> widgetList = widgetStream.collect(toCollection(() -> synchronizedList(new ArrayList<>())));

    Future<?> result = iterateOverWidgetsWithConcurrentModification(widgetList);

    assertThatThrownBy(() -> result.get())
        .isInstanceOf(ExecutionException.class)
        .hasCauseInstanceOf(ConcurrentModificationException.class);
  }

  @Test
  void iterateOverCopyOnWriteArrayList() throws ExecutionException {
    List<Widget> widgetList = widgetStream.collect(toCollection(CopyOnWriteArrayList::new));

    Future<?> result = iterateOverWidgetsWithConcurrentModification(widgetList);

    assertThatCode(() -> result.get()).doesNotThrowAnyException();
  }

  private Future<?> iterateOverWidgetsWithConcurrentModification(List<Widget> widgets) {
    Future<?> result = exec.submit(() -> {
      for (Widget w : widgets) {
        System.out.println(w);
      }
    });

    exec.submit(() -> {
      widgets.remove(widgets.size() - 1);
    });

    return result;
  }

  @AfterEach
  void tearDown() throws InterruptedException {
    exec.shutdown();
    exec.awaitTermination(30, TimeUnit.SECONDS);
  }

  private static class Widget {}
}

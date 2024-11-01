package brian.goets.test.util;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TaskIterator {

  public static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

  public static <T> List<Future<T>> submitForExecutionNumberOfTimes(Callable<T> task, int numberOfIterations) throws InterruptedException {
    ExecutorService exec = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS);

    List<Callable<T>> tasks = IntStream.rangeClosed(1, numberOfIterations)
        .mapToObj(i -> task)
        .collect(Collectors.toList());

    List<Future<T>> executedTasks = exec.invokeAll(tasks);

    exec.shutdown();
    exec.awaitTermination(30, TimeUnit.SECONDS);

    return executedTasks;
  }

  public static <T> List<T> executeNumberOfTimes(Callable<T> task, int numberOfIterations) throws InterruptedException {
    return submitForExecutionNumberOfTimes(task, numberOfIterations)
        .stream().map(TaskIterator::getTaskResult)
        .collect(Collectors.toList());
  }

  public static <T> T getTaskResult(Future<T> future) {
    try {
      return future.get();
    } catch (InterruptedException e) {
      // restore interrupted status
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}

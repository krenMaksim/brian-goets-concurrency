package brian.goets.chapter6.point3.point5;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CompletionServiceInvestigation {

  private static final int TASKS_NUMBER = 3;

  private static final Callable<String> task = () -> {
    TimeUnit.SECONDS.sleep(1);
    String threadName = Thread.currentThread().getName();
    System.out.println(threadName);
    TimeUnit.SECONDS.sleep(1);

    return threadName;
  };

  public static void main(String[] args) {
    ExecutorService ex = Executors.newFixedThreadPool(2);
    CompletionService<String> completionService = new ExecutorCompletionService<>(ex);

    IntStream.rangeClosed(1, TASKS_NUMBER).forEach(i -> completionService.submit(task));

    IntStream.rangeClosed(1, TASKS_NUMBER).forEach(i -> {
      try {
        String result = completionService.take().get();

        System.out.println("result: " + result);
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    });

    ex.shutdown();
  }
}

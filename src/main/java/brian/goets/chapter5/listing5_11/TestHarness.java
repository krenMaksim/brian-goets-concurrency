package brian.goets.chapter5.listing5_11;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

class TestHarness {

  public long timeTasks(int nThreads, Runnable task) throws InterruptedException {
    CountDownLatch startGate = new CountDownLatch(1);
    CountDownLatch endGate = new CountDownLatch(nThreads);

    IntStream.rangeClosed(1, nThreads)
        .mapToObj(i -> new Worker(startGate, endGate, task))
        .forEach(Thread::start);

    TimeUnit.SECONDS.sleep(10);
    println("MAIN LATCH");

    long start = System.nanoTime();
    startGate.countDown();
    endGate.await();
    long end = System.nanoTime();
    return end - start;
  }

  private static class Worker extends Thread {

    private final CountDownLatch startGate;
    private final CountDownLatch endGate;
    private final Runnable task;

    public Worker(CountDownLatch startGate, CountDownLatch endGate, Runnable task) {
      this.startGate = startGate;
      this.endGate = endGate;
      this.task = task;
    }

    @Override
    public void run() {
      try {
        println("Ready");
        startGate.await();
        try {
          task.run();
          println("Done");
        } finally {
          endGate.countDown();
        }
      } catch (InterruptedException ignored) {
      }
    }
  }

  private static void println(String message) {
    System.out.println(String.format("[%s] - %s", Thread.currentThread().getName(), message));
  }
}

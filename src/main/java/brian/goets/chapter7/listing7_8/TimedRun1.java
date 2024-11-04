package brian.goets.chapter7.listing7_8;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class TimedRun1 {

  private static final ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(1);

  // Don’t do this. Answer why.
  public static void timedRun(Runnable r, long timeout, TimeUnit unit) {
    Thread taskThread = Thread.currentThread();

    cancelExec.schedule(() -> taskThread.interrupt(), timeout, unit);

    r.run();
  }
}
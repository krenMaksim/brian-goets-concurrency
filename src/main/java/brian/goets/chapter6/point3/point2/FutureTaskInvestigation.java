package brian.goets.chapter6.point3.point2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTaskInvestigation {

    public static void main(String[] args) {
        ExecutorService ex = Executors.newFixedThreadPool(2);

        Callable<String> task = () -> {
            TimeUnit.SECONDS.sleep(5);
            String threadName = Thread.currentThread()
                                      .getName();
            System.out.println(threadName);
            TimeUnit.SECONDS.sleep(5);

            return threadName;
        };

        FutureTask<String> futureTask = new FutureTask<>(task);
        ex.submit(futureTask);

        ex.shutdown();
    }
}

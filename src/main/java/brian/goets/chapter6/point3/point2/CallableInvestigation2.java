package brian.goets.chapter6.point3.point2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CallableInvestigation2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService ex = Executors.newFixedThreadPool(2);

        Callable<String> task = () -> {
            TimeUnit.SECONDS.sleep(5);
            String threadName = Thread.currentThread()
                                      .getName();
            System.out.println(threadName);
            TimeUnit.SECONDS.sleep(5);

            return threadName;
        };

        Future<String> result = ex.submit(task);
        System.out.println("result: " + result.get());

        ex.shutdown();
    }
}

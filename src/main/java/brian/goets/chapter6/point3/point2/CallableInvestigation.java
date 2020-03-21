package brian.goets.chapter6.point3.point2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CallableInvestigation {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService ex = Executors.newFixedThreadPool(2);

        Callable<?> task = () -> {
            TimeUnit.SECONDS.sleep(5);
            String threadName = Thread.currentThread()
                                      .getName();
            System.out.println(threadName);
            TimeUnit.SECONDS.sleep(5);

            return threadName;
        };

        IntStream.rangeClosed(1, 2)
                 .forEach(i -> {
                     System.out.println(i + " dcurrent" + task);
                     ex.submit(task);
                 });

        ex.shutdown();
    }
}

package brian.goets.chapter6.point2.point4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ExecutorServiceInvestigation2 {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService ex = Executors.newFixedThreadPool(1);

        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread()
                                         .getId());
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        IntStream.rangeClosed(1, 2)
                 .forEach(i -> {
                     System.out.println(i + " dcurrent" + task);
                     ex.execute(task);
                 });

        ex.shutdown();

        ex.execute(task);
        // List<Runnable> tasks = ex.shutdownNow();
        // System.out.println("not executed: " + tasks);
    }
}

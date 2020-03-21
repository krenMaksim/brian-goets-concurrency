package brian.goets.chapter6.point2.point4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ExecutorServiceInvestigation3 {

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


        while (true) {
            boolean isTerminated = ex.isTerminated();
            System.out.println("isTerminated: " + isTerminated);
            if (isTerminated) {
                ex.shutdown();
                break;
            }
            TimeUnit.SECONDS.sleep(3);
        }
    }
}

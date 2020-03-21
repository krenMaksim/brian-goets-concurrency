package brian.goets.chapter6.point2.point4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceInvestigation {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService ex = Executors.newFixedThreadPool(2);

        Runnable task = () -> {
            System.out.println(Thread.currentThread()
                                     .getId());
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        };

        while (true) {
            TimeUnit.SECONDS.sleep(1);
            ex.execute(task);
        }
    }
}

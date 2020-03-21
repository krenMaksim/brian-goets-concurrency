package brian.goets.chapter6.point2;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorInvestigation {

    public static void main(String[] args) throws InterruptedException {
        Executor ex = Executors.newFixedThreadPool(4);

        Runnable task = () -> {
            System.out.println(Thread.currentThread().getId());
        };

        while (true) {
            TimeUnit.SECONDS.sleep(1);
            ex.execute(task);
        }
    }
}

package brian.goets.chapter5.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TestHarness {
    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        System.out.println(String.format("%s ready", Thread.currentThread().getName()));
                        startGate.await();
                        try {
                            task.run();
                            System.out.println(String.format("%s done", Thread.currentThread().getName()));
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            };
            t.start();
        }
        
        TimeUnit.SECONDS.sleep(10);
        System.out.println(String.format("%s MAIN LATCH", Thread.currentThread().getName()));
        

        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        return end - start;
    }
}

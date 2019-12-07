package brian.goets.chapter5.latch;

public class LatchMain {

    public static void main(String[] args) throws InterruptedException {
        TestHarness testHarness = new TestHarness();

        int numberThreads = 50;

        Runnable task = () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println(String.format("i=%d; %s", i, Thread.currentThread().getName()));
                                                                      
            }
        };

        System.out.println("main thread start");

        testHarness.timeTasks(numberThreads, task);

        System.out.println("main thread finish");
    }
}

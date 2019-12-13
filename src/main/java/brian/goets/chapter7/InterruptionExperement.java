package brian.goets.chapter7;

public class InterruptionExperement {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new SleepTask());
        t.start();

        Thread.sleep(1_000);
        System.out.println("Thread interrupt");
        System.out.println(t.getName() + " isInterrupted():" + t.isInterrupted());
        t.interrupt();
        System.out.println(t.getName() + " isInterrupted():" + t.isInterrupted());
        System.out.println("Finish main thread");
    }

    static class SleepTask implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("Thread is sleepping");
                Thread.sleep(100_000);
            } catch (InterruptedException e) {
                Thread.interrupted();
                System.out.println("111 isInterrupted:" + Thread.currentThread()
                                                                .isInterrupted());
                run();
                // System.out.println("InterruptedException has been thrown");
                // e.printStackTrace();
            }
        }
    }
}

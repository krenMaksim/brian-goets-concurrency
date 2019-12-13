package brian.goets.chapter7.listing7_5;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException consumed) {
            /* Allow thread to exit */
            consumed.printStackTrace();
        }
    }

    public void cancel() {
        interrupt();
    }
}
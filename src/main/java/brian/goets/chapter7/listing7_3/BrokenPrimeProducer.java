package brian.goets.chapter7.listing7_3;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

class BrokenPrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled = false;

    BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!cancelled) {
                queue.put(p = p.nextProbablePrime());
            }
            System.out.println("BrokenPrimeProducer has been cancelled");
        } catch (InterruptedException consumed) {

        }
    }

    public void cancel() {
        cancelled = true;
    }
}
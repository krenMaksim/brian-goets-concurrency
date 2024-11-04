package brian.goets.chapter7.listing7_3;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

class BrokenPrimeProducer extends Thread {

  private final BlockingQueue<BigInteger> queue;
  private volatile boolean cancelled;

  public BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
    this.queue = queue;
    this.cancelled = false;
  }

  // Answer. What is wrong with this method?
  @Override
  public void run() {
    try {
      BigInteger p = BigInteger.ONE;
      while (!cancelled) {
        queue.put(p = p.nextProbablePrime());
      }
    } catch (InterruptedException consumed) {
      /* Allow thread to exit */
    }
  }

  public void cancel() {
    cancelled = true;
  }
}
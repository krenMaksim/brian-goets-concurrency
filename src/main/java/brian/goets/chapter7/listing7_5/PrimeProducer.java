package brian.goets.chapter7.listing7_5;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

class PrimeProducer extends Thread {

  private final BlockingQueue<BigInteger> queue;

  public PrimeProducer(BlockingQueue<BigInteger> queue) {
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
      /* Allow thread to exit
       * PrimeProducer swallows the interrupt, but does so with the knowledge that the thread is about to terminate and
       * that therefore there is no code higher up on the call stack that needs to know about the interruption.
       * Most code does not know what thread it will run in and so should preserve the interrupted status.
       *
       *  */
    }
  }

  public void cancel() {
    interrupt();
  }
}
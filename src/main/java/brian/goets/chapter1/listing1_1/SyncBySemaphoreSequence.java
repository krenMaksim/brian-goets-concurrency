package brian.goets.chapter1.listing1_1;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.Semaphore;

@ThreadSafe
class SyncBySemaphoreSequence implements Sequence {

  private final Semaphore semaphore = new Semaphore(1);
  private int value;

  @Override
  public int getNext() throws InterruptedException {
    semaphore.acquire();
    try {
      return value++;
    } finally {
      semaphore.release();
    }
  }
}
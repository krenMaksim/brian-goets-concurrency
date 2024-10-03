package brian.goets.chapter1.listing1_1;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.Semaphore;

@ThreadSafe
class SyncBySemaphoreSequence {

  private final Semaphore semaphore = new Semaphore(1);
  private int value;

  public int getNext() throws InterruptedException {
    int next;
    semaphore.acquire();
    next = value++;
    semaphore.release();
    return next;
  }
}
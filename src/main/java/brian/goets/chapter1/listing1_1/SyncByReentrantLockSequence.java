package brian.goets.chapter1.listing1_1;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@ThreadSafe
class SyncByReentrantLockSequence implements Sequence {

  private final Lock lock = new ReentrantLock();
  private int value;

  @Override
  public int getNext() {
    lock.lock();
    try {
      return value++;
    } finally {
      lock.unlock();
    }
  }
}
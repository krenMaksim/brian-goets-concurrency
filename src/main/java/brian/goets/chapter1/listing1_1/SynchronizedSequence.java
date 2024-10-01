package brian.goets.chapter1.listing1_1;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class SynchronizedSequence {

  private int value;

  public synchronized int getNext() {
    return value++;
  }
}
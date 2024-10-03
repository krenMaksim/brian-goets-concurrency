package brian.goets.chapter1.listing1_1;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class SyncViaBlockSequence {

  private int value;

  public int getNext() {
    synchronized (this) {
      return value++;
    }
  }
}
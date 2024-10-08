package brian.goets.chapter1.listing1_1;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
class SyncViaBlockSequence implements Sequence {

  private int value;

  @Override
  public int getNext() {
    synchronized (this) {
      return value++;
    }
  }
}
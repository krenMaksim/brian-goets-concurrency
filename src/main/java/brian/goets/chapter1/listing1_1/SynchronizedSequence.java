package brian.goets.chapter1.listing1_1;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
class SynchronizedSequence implements Sequence {

  private int value;

  @Override
  public synchronized int getNext() {
    return value++;
  }
}
package brian.goets.chapter1.listing1_1;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
class UnsafeVolatileSequence implements Sequence {

  private volatile int value;

  @Override
  public int getNext() {
    return value++;
  }
}
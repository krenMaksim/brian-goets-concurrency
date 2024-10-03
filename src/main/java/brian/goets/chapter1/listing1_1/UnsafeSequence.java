package brian.goets.chapter1.listing1_1;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
class UnsafeSequence implements Sequence {

  private int value;

  @Override
  public int getNext() {
    return value++;
  }
}
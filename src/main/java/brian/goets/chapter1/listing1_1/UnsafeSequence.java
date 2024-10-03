package brian.goets.chapter1.listing1_1;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
class UnsafeSequence {

  private int value;

  public int getNext() {
    return value++;
  }
}
package brian.goets.chapter2.listing3_2;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
class SynchronizedPojo {

  private int value;

  public synchronized int getValue() {
    return value;
  }

  public synchronized void setValue(int value) {
    this.value = value;
  }
}

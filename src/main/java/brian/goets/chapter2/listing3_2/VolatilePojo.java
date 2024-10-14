package brian.goets.chapter2.listing3_2;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
class VolatilePojo {

  private volatile int value;

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}

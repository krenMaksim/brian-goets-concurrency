package brian.goets.chapter2.listing3_2;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
class AtomicPojo {

  private final AtomicInteger value = new AtomicInteger();

  public int getValue() {
    return value.get();
  }

  public void setValue(int value) {
    this.value.set(value);
  }
}

package brian.goets.chapter1.listing1_1;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
class SyncByCasSequence implements Sequence {

  private final AtomicInteger value = new AtomicInteger();

  @Override
  public int getNext() {
    // See page 348 Listing 15.5
    while (true) {
      int value = this.value.get();
      int next = value + 1;
      if (this.value.compareAndSet(value, next)) {
        return value;
      }
    }
  }
}
package brian.goets.chapter1.listing1_1;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
class AtomicSequence implements Sequence {

  private final AtomicInteger value = new AtomicInteger();

  @Override
  public int getNext() {
    return value.getAndIncrement();
  }
}
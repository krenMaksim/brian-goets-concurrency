package brian.goets.chapter3.listing3_2;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
class SimplePojo {

  private int value;

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}

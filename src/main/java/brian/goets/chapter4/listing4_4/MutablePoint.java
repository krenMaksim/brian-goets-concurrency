package brian.goets.chapter4.listing4_4;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
class MutablePoint {

  public int x, y;

  public MutablePoint() {
    x = 0;
    y = 0;
  }

  public MutablePoint(MutablePoint p) {
    this.x = p.x;
    this.y = p.y;
  }
}
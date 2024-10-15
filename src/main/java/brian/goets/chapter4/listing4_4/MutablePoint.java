package brian.goets.chapter4.listing4_4;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
class MutablePoint {

  private int x;
  private int y;

  public MutablePoint() {
    x = 0;
    y = 0;
  }

  public MutablePoint(MutablePoint point) {
    this.x = point.x;
    this.y = point.y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }
}
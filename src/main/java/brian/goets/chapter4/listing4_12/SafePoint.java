package brian.goets.chapter4.listing4_12;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
class SafePoint {

  private int x;
  private int y;

  /*
    It should be fine to have constructor like

    public SafePoint(int x, int y) {
      this.x = x;
      this.y = y;
    }

    , shouldn't it?
   */
  public SafePoint(int x, int y) {
    this.set(x, y);
  }

  public synchronized int[] get() {
    return new int[] {x, y};
  }

  public synchronized void set(int x, int y) {
    this.x = x;
    this.y = y;
  }
}
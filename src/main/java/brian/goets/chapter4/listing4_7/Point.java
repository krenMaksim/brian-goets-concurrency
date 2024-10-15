package brian.goets.chapter4.listing4_7;

import net.jcip.annotations.Immutable;

@Immutable
class Point {

  private int x;
  private int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
}
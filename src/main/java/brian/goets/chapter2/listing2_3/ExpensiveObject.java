package brian.goets.chapter2.listing2_3;

import java.util.concurrent.TimeUnit;

class ExpensiveObject {

  public static ExpensiveObject newInstanceDelayed() {
    try {
      TimeUnit.MILLISECONDS.sleep(100);
      return new ExpensiveObject();
    } catch (InterruptedException e) {
      return null;
    }
  }
}

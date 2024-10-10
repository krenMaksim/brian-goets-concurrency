package brian.goets.chapter2.listing2_3;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
class LazyInitDoubleCheck implements LazyInit {

  private ExpensiveObject instance = null;

  @Override
  public ExpensiveObject getInstance() {
    if (instance == null) {
      synchronized (this) {
        if (instance == null) {
          instance = ExpensiveObject.newInstanceDelayed();
        }
        return instance;
      }
    }
    return instance;
  }
}

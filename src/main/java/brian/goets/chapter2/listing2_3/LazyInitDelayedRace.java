package brian.goets.chapter2.listing2_3;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
class LazyInitDelayedRace implements LazyInit {

  private ExpensiveObject instance = null;

  @Override
  public ExpensiveObject getInstance() {
    if (instance == null) {
      instance = ExpensiveObject.newInstanceDelayed();
    }
    return instance;
  }
}

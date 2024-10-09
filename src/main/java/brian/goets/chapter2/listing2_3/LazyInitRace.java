package brian.goets.chapter2.listing2_3;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
class LazyInitRace implements LazyInit {

  private ExpensiveObject instance = null;

  @Override
  public ExpensiveObject getInstance() {
    if (instance == null) {
      instance = new ExpensiveObject();
    }
    return instance;
  }
}

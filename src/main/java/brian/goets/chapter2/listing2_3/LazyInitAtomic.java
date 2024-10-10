package brian.goets.chapter2.listing2_3;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
class LazyInitAtomic implements LazyInit {

  private final AtomicReference<ExpensiveObject> instance = new AtomicReference<>();

  @Override
  public ExpensiveObject getInstance() {
    return instance.updateAndGet(expensiveObject -> {
      if (expensiveObject == null) {
        return ExpensiveObject.newInstanceDelayed();
      }
      return expensiveObject;
    });
  }
}

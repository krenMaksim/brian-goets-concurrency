package brian.goets.chapter2.listing2_3;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
class LazyInitCas implements LazyInit {

  private AtomicReference<ExpensiveObject> instance = new AtomicReference<>();

  @Override
  public ExpensiveObject getInstance() {
    while (true) {
      if (instance.get() == null) {
        ExpensiveObject initializedExpensiveObject = ExpensiveObject.newInstanceDelayed();
        if (!instance.compareAndSet(null, initializedExpensiveObject)) {
          System.out.println("Retry initialization of ExpensiveObject");
          continue;
        }
      }
      return instance.get();
    }
  }
}

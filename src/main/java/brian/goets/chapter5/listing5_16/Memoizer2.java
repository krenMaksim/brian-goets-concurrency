package brian.goets.chapter5.listing5_16;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Memoizer2<A, V> implements Computable<A, V> {

  private final Computable<A, V> c;
  private final Map<A, V> cache;

  public Memoizer2(Computable<A, V> c) {
    this.c = c;
    this.cache = new ConcurrentHashMap<>();
  }

  @Override
  public V compute(A arg) throws InterruptedException {
    V result = cache.get(arg);
    if (result == null) {
      result = c.compute(arg);
      cache.put(arg, result);
    }
    return result;
  }
}
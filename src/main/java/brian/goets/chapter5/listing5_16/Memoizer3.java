package brian.goets.chapter5.listing5_16;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static brian.goets.chapter5.listing5_13.LaunderThrowable.launderThrowable;

class Memoizer3<A, V> implements Computable<A, V> {

  private final Computable<A, V> c;
  private final Map<A, Future<V>> cache;

  public Memoizer3(Computable<A, V> c) {
    this.c = c;
    this.cache = new ConcurrentHashMap<>();
  }

  @Override
  public V compute(final A arg) throws InterruptedException {
    Future<V> f = cache.get(arg);
    if (f == null) {
      Callable<V> eval = () -> c.compute(arg);
      FutureTask<V> ft = new FutureTask<>(eval);
      f = ft;
      cache.put(arg, ft);
      ft.run(); // call to c.compute happens here
    }
    try {
      return f.get();
    } catch (ExecutionException e) {
      throw launderThrowable(e.getCause());
    }
  }
}


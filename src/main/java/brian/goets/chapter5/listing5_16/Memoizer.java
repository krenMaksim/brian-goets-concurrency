package brian.goets.chapter5.listing5_16;

import brian.goets.chapter5.listing5_13.LaunderThrowable;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

class Memoizer<A, V> implements Computable<A, V> {

  private final Computable<A, V> c;
  private final ConcurrentMap<A, Future<V>> cache;
  
  public Memoizer(Computable<A, V> c) {
    this.c = c;
    this.cache = new ConcurrentHashMap<>();
  }

  @Override
  public V compute(final A arg) throws InterruptedException {
    while (true) {
      Future<V> f = cache.get(arg);
      if (f == null) {
        Callable<V> eval = () -> c.compute(arg);
        FutureTask<V> ft = new FutureTask<>(eval);
        f = cache.putIfAbsent(arg, ft);
        if (f == null) {
          f = ft;
          ft.run();
        }
      }
      try {
        return f.get();
      } catch (CancellationException e) {
        cache.remove(arg, f);
      } catch (ExecutionException e) {
        throw LaunderThrowable.launderThrowable(e.getCause());
      }
    }
  }
}


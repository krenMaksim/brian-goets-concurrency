package brian.goets.chapter5.scalable.cache.util;

public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
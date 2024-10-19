package brian.goets.chapter5.listing5_16;

interface Computable<A, V> {

  V compute(A arg) throws InterruptedException;
}
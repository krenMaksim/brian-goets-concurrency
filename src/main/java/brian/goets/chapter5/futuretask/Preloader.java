package brian.goets.chapter5.futuretask;

import brian.goets.chapter5.listing5_13.LaunderThrowable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class Preloader {

  private final FutureTask<ProductInfo> future = new FutureTask<>(new Callable<ProductInfo>() {
    public ProductInfo call() throws DataLoadException {
      return loadProductInfo();
    }
  });
  private final Thread thread = new Thread(future);

  public void start() {
    thread.start();
  }

  public ProductInfo get() throws DataLoadException, InterruptedException {
    try {
      return future.get();
    } catch (ExecutionException e) {
      Throwable cause = e.getCause();
      if (cause instanceof DataLoadException) {
        throw (DataLoadException) cause;
      } else {
        throw LaunderThrowable.launderThrowable(cause);
      }
    }
  }

  private ProductInfo loadProductInfo() throws DataLoadException {
    for (int i = 0; i < 10; i++) {
      System.out.println(String.format("i=%d; %s", i, Thread.currentThread().getName()));
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    return null;
  }

  static interface ProductInfo {
  }
}



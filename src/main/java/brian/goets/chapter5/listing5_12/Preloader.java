package brian.goets.chapter5.listing5_12;

import brian.goets.chapter5.listing5_13.LaunderThrowable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class Preloader {

  private final FutureTask<ProductInfo> future;
  private final Thread thread;

  public Preloader() {
    future = new FutureTask<>(Preloader::loadProductInfo);
    thread = new Thread(future);
  }

  private static ProductInfo loadProductInfo() throws DataLoadException {
    return new ProductInfo();
  }

  public void start() {
    thread.start();
  }

  public ProductInfo get() throws DataLoadException, InterruptedException {
    try {
      return future.get();
    } catch (ExecutionException e) {
      Throwable cause = e.getCause();
      if (cause instanceof DataLoadException dataLoadException) {
        throw dataLoadException;
      } else {
        throw LaunderThrowable.launderThrowable(cause);
      }
    }
  }

  static class ProductInfo {

  }

  static class DataLoadException extends Exception {

  }
}



package brian.goets.chapter5.listing5_12;

import brian.goets.chapter5.listing5_13.LaunderThrowable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class Preloader {

  ProductInfo loadProductInfo() throws DataLoadException {
    return null;
  }

  private final FutureTask<ProductInfo> future =
      new FutureTask<ProductInfo>(new Callable<ProductInfo>() {
        public ProductInfo call() throws DataLoadException {
          return loadProductInfo();
        }
      });
  private final Thread thread = new Thread(future);

  public void start() {thread.start();}

  public ProductInfo get()
      throws DataLoadException, InterruptedException {
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

  static class ProductInfo {

  }

  static class DataLoadException extends Exception {

  }
}



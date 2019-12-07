package brian.goets.chapter5.futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class Preloader {
    ProductInfo loadProductInfo() throws DataLoadException {
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

    interface ProductInfo {
    }
}


class LaunderThrowable {
    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException)
            return (RuntimeException) t;
        else if (t instanceof Error)
            throw (Error) t;
        else
            throw new IllegalStateException("Not unchecked", t);
    }
}


@SuppressWarnings("serial")
class DataLoadException extends Exception {
}

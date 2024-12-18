package brian.goets.chapter7.listing7_12;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

abstract class SocketUsingTask<T> implements CancellableTask<T> {

  @GuardedBy("this")
  private Socket socket;

  protected synchronized void setSocket(Socket s) {
    socket = s;
  }

  @Override
  public synchronized void cancel() {
    try {
      if (socket != null) {
        socket.close();
      }
    } catch (IOException ignored) {
    }
  }

  @Override
  public RunnableFuture<T> newTask() {
    return new FutureTask<T>(this) {
      @Override
      @SuppressWarnings("finally")
      public boolean cancel(boolean mayInterruptIfRunning) {
        try {
          SocketUsingTask.this.cancel();
        } finally {
          return super.cancel(mayInterruptIfRunning);
        }
      }
    };
  }
}

interface CancellableTask<T> extends Callable<T> {

  void cancel();

  RunnableFuture<T> newTask();
}

@ThreadSafe
class CancellingExecutor extends ThreadPoolExecutor {

  public CancellingExecutor(int corePoolSize,
      int maximumPoolSize,
      long keepAliveTime,
      TimeUnit unit,
      BlockingQueue<Runnable> workQueue) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
  }

  @Override
  protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
    if (callable instanceof CancellableTask<T> cancellableTask) {
      return cancellableTask.newTask();
    } else {
      return super.newTaskFor(callable);
    }
  }
}

package brian.goets.chapter7.listing7_13;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class LogWriter {

  private static final int CAPACITY = 1000;

  private final BlockingQueue<String> queue;
  private final LoggerThread logger;

  public LogWriter(Writer writer) {
    this.queue = new LinkedBlockingQueue<>(CAPACITY);
    this.logger = new LoggerThread(writer);
  }

  public void start() {
    logger.start();
  }

  public void log(String msg) throws InterruptedException {
    queue.put(msg);
  }

  private class LoggerThread extends Thread {

    private final PrintWriter writer;

    public LoggerThread(Writer writer) {
      this.writer = new PrintWriter(writer, true);
    }

    public void run() {
      try {
        while (true) {
          writer.println(queue.take());
        }
      } catch (InterruptedException ignored) {
      } finally {
        writer.close();
      }
    }
  }
}
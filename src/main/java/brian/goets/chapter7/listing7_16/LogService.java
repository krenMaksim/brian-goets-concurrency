package brian.goets.chapter7.listing7_16;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class LogService {

  private final ExecutorService exec;
  private final PrintWriter writer;

  public LogService(Writer writer) {
    this.exec = Executors.newSingleThreadExecutor();
    this.writer = new PrintWriter(writer);
  }

  public void stop() throws InterruptedException {
    try {
      exec.shutdown();
      exec.awaitTermination(1, TimeUnit.MINUTES);
    } finally {
      writer.close();
    }
  }

  public void log(String msg) {
    exec.execute(() -> writer.println(msg));
  }
}

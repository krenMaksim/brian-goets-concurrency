package brian.goets.chapter6.listing6_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static brian.goets.chapter6.listing6_1.SingleThreadWebServer.handleRequest;

class TaskExecutionWebServer {

  private static final int THREADS = 100;
  private static final Executor exec = Executors.newFixedThreadPool(THREADS);

  public static void main(String[] args) throws IOException {
    @SuppressWarnings("resource")
    ServerSocket socket = new ServerSocket(8081);
    while (true) {
      final Socket connection = socket.accept();
      Runnable task = new Runnable() {
        public void run() {
          handleRequest(connection);
        }
      };
      exec.execute(task);
    }
  }
}
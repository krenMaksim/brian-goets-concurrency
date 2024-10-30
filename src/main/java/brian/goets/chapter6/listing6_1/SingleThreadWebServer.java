package brian.goets.chapter6.listing6_1;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

class SingleThreadWebServer {

  public static void main(String[] args) throws IOException {
    @SuppressWarnings("resource")
    ServerSocket socket = new ServerSocket(8081);
    while (true) {
      try (Socket connection = socket.accept()) {
        handleRequest(connection);
      }
    }
  }

  private static void handleRequest(Socket connection) {
    try {
      String content = convert(connection.getInputStream());
      System.out.println(content);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String convert(InputStream inputStream) throws IOException {
    StringBuilder input = new StringBuilder();
    while (inputStream.available() > 0) {
      input.append((char) inputStream.read());
    }
    return input.toString();
  }
}

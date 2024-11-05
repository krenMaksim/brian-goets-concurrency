package brian.goets.chapter7.listing7_11;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

class ReaderThread extends Thread {

  private static final int BUFF_SIZE = 512;
  private final Socket socket;
  private final InputStream in;

  public ReaderThread(Socket socket) throws IOException {
    this.socket = socket;
    this.in = socket.getInputStream();
  }

  @Override
  public void interrupt() {
    try {
      socket.close();
    } catch (IOException ignored) {

    } finally {
      super.interrupt();
    }
  }

  @Override
  public void run() {
    try {
      byte[] buf = new byte[BUFF_SIZE];
      while (true) {
        int count = in.read(buf);
        if (count < 0) {
          break;
        } else if (count > 0) {
          processBuffer(buf, count);
        }
      }
    } catch (IOException e) {
      /* Allow thread to exit */
    }
  }

  private void processBuffer(byte[] buf, int count) {}
}
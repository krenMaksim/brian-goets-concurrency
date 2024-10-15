package brian.goets.chapter3.listing3_1;

class NoVisibility {

  public static boolean ready;
  public static int number;

  public static class ReaderThread extends Thread {

    public void run() {
      while (!ready) {
        Thread.yield();
      }
      System.out.println(number);
    }
  }

  public static void main(String[] args) {
    new ReaderThread().start();
    number = 42;
    ready = true;
  }
}
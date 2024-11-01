package brian.goets.chapter5.listing5_8;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class ProducerConsumer {

  static class FileCrawler implements Runnable {

    private static final FileFilter DEFAULT_FILTER = file -> true;

    private final BlockingQueue<File> fileQueue;
    private final FileFilter fileFilter;
    private final File root;

    public FileCrawler(BlockingQueue<File> fileQueue, FileFilter fileFilter, File root) {
      this.fileQueue = fileQueue;
      this.root = root;
      this.fileFilter = file -> file.isDirectory() || fileFilter.accept(file);
    }

    public FileCrawler(BlockingQueue<File> fileQueue, File root) {
      this(fileQueue, DEFAULT_FILTER, root);
    }

    private boolean alreadyIndexed(File f) {
      return false;
    }

    public void run() {
      try {
        crawl(root);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    private void crawl(File root) throws InterruptedException {
      File[] entries = root.listFiles(fileFilter);
      if (entries != null) {
        for (File entry : entries) {
          if (entry.isDirectory()) {
            crawl(entry);
          } else if (!alreadyIndexed(entry)) {
            fileQueue.put(entry);
          }
        }
      }
    }
  }

  static class Indexer implements Runnable {

    private final BlockingQueue<File> queue;

    public Indexer(BlockingQueue<File> queue) {
      this.queue = queue;
    }

    public void run() {
      try {
        while (true) {
          indexFile(queue.take());
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    public void indexFile(File file) {
      System.out.println(String.format("Index the file '%s' ...", file));
    }
  }

  private static final int BOUND = 10;
  private static final int N_CONSUMERS = Runtime.getRuntime().availableProcessors();

  public static void startIndexing(File[] roots) {
    BlockingQueue<File> queue = new LinkedBlockingQueue<>(BOUND);
    for (File root : roots) {
      new Thread(new FileCrawler(queue, root)).start();
    }

    for (int i = 0; i < N_CONSUMERS; i++) {
      new Thread(new Indexer(queue)).start();
    }
  }
}

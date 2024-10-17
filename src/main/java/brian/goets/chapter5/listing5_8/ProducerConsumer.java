package brian.goets.chapter5.listing5_8;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

class ProducerConsumer {

  static class FileCrawler implements Runnable {

    private final BlockingQueue<File> fileQueue;
    private final FileFilter fileFilter;
    private final File root;

    public FileCrawler(BlockingQueue<File> fileQueue, FileFilter fileFilter, File root) {
      this.fileQueue = fileQueue;
      this.root = root;
      this.fileFilter = file -> file.isDirectory() || fileFilter.accept(file);
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

    private void crawl2(File root) {
      Path startPath = root.toPath();

      try (Stream<Path> stream = Files.walk(startPath)) {
        stream.forEach(path -> {
          if (Files.isDirectory(path)) {
            System.out.println("Directory: " + path);
          } else {
            try {
              fileQueue.put(path.toFile());
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
            //            System.out.println("File: " + path);
          }
        });
      } catch (IOException e) {
        e.printStackTrace();
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
    FileFilter filter = file -> true;

    for (File root : roots) {
      new Thread(new FileCrawler(queue, filter, root)).start();
    }

    for (int i = 0; i < N_CONSUMERS; i++) {
      new Thread(new Indexer(queue)).start();
    }
  }
}

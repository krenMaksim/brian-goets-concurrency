package brian.goets.chapter5.listing5_8;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static brian.goets.test.util.TaskIterator.AVAILABLE_PROCESSORS;
import static org.assertj.core.api.Assertions.assertThatCode;

class ProducerConsumerTest {

  private File[] roots = {new File("./")};

  @Test
  void startIndexing() throws InterruptedException {
    assertThatCode(() -> ProducerConsumer.startIndexing(roots))
        .doesNotThrowAnyException();

    TimeUnit.SECONDS.sleep(30);
  }

  private static final int BOUND = 10;
  private static final int N_CONSUMERS = Runtime.getRuntime().availableProcessors();

  @Test
  void startIndexingUsingExecutors() throws InterruptedException {
    ExecutorService exec = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS);

    BlockingQueue<File> queue = new LinkedBlockingQueue<>(BOUND);

    for (File root : roots) {
      new Thread(new ProducerConsumer.FileCrawler(queue, root)).start();
    }

    for (int i = 0; i < N_CONSUMERS; i++) {
      new Thread(new ProducerConsumer.Indexer(queue)).start();
    }

    exec.shutdown();
    exec.awaitTermination(30, TimeUnit.SECONDS);
  }

  // rewrite startIndexing based on executors

  @AfterEach
  void tearDown() throws InterruptedException {
    // it is needed to allocate some time for files to be processed

  }
}
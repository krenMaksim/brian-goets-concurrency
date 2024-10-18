package brian.goets.chapter5.listing5_8;

import brian.goets.chapter5.listing5_8.ProducerConsumer.FileCrawler;
import brian.goets.chapter5.listing5_8.ProducerConsumer.Indexer;
import brian.goets.test.util.TaskIterator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
  private static final int N_CONSUMERS = 10;

  @Test
  void startIndexingUsingExecutors() throws InterruptedException {
    ExecutorService exec = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS);

    BlockingQueue<File> queue = new LinkedBlockingQueue<>(BOUND);

    List<Future<?>> crawlers = Stream.of(roots)
        .map(root -> new FileCrawler(queue, root))
        .map(exec::submit)
        .collect(Collectors.toList());

    List<Future<?>> indexers = IntStream.rangeClosed(1, N_CONSUMERS)
        .mapToObj(i -> new Indexer(queue))
        .map(exec::submit)
        .collect(Collectors.toList());

    crawlers.forEach(TaskIterator::getTaskResult);

    while (true) {
      if (queue.isEmpty()) {
        indexers.forEach(indexerTask -> indexerTask.cancel(true));
        break;
      }
    }

    exec.shutdown();
    exec.awaitTermination(30, TimeUnit.SECONDS);
  }
}
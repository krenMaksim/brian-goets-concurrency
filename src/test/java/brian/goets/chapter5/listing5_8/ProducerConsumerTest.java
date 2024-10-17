package brian.goets.chapter5.listing5_8;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThatCode;

class ProducerConsumerTest {

  @Test
  void startIndexing() {
    File[] roots = {new File("./")};

    assertThatCode(() -> ProducerConsumer.startIndexing(roots))
        .doesNotThrowAnyException();
  }

  @AfterEach
  void tearDown() throws InterruptedException {
    TimeUnit.SECONDS.sleep(30);
  }
}
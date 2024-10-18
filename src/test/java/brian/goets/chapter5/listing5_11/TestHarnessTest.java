package brian.goets.chapter5.listing5_11;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class TestHarnessTest {

  private static final int NUMBER_THREADS = 50;

  @BeforeEach
  void setUp() {
    System.out.println("Main thread started");
  }

  @Test
  void timeTasks() {
    TestHarness testHarness = new TestHarness();

    assertThatCode(() -> testHarness.timeTasks(NUMBER_THREADS, this::doSomeJob))
        .doesNotThrowAnyException();
  }

  private void doSomeJob() {
    for (int i = 1; i <= 5; i++) {
      System.out.println(String.format("i=%d; %s", i, Thread.currentThread().getName()));
    }
  }

  @AfterEach
  void tearDown() {
    System.out.println("Main thread finished");
  }
}
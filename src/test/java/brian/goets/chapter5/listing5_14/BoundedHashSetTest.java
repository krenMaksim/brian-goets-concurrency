package brian.goets.chapter5.listing5_14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThatCode;

class BoundedHashSetTest {

  private static final int BOUND = 10;
  private BoundedHashSet<Integer> boundedHashSet;

  @BeforeEach
  void setUp() {
    boundedHashSet = new BoundedHashSet<>(BOUND);
    ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
    exec.schedule(() -> boundedHashSet.remove(5), 10, TimeUnit.SECONDS);
  }

  @Test
  void tryToHitSetLimit() {
    assertThatCode(() -> {
      IntStream.rangeClosed(1, BOUND + 1).forEach(i -> {
        try {
          boundedHashSet.add(i);
        } catch (InterruptedException e) {
          // NOOP
        }
      });
    }).doesNotThrowAnyException();
  }
}
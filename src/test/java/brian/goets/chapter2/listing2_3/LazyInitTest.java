package brian.goets.chapter2.listing2_3;

import org.junit.jupiter.api.RepeatedTest;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static brian.goets.test.util.TaskIterator.submitForExecutionForNumberOfTimes;
import static org.assertj.core.api.Assertions.assertThat;

class LazyInitTest {

  private static final int SINGLE_INSTANCE = 1;
  private static final int NUMBER_OF_ITERATIONS = 10;

  @RepeatedTest(100)
  void doGivenNumberOfInitializationsViaLazyInitRace() throws InterruptedException {
    LazyInit lazyInit = new LazyInitRace();

    int createdInstancesNumber = doGivenNumberOfConcurrentInitializations(lazyInit, NUMBER_OF_ITERATIONS);

    assertThat(createdInstancesNumber).isNotEqualTo(SINGLE_INSTANCE);
  }

  private int doGivenNumberOfConcurrentInitializations(LazyInit lazyInit, int iterations) throws InterruptedException {
    Set<ExpensiveObject> createdInstances = ConcurrentHashMap.newKeySet();
    submitForExecutionForNumberOfTimes(() -> {
      ExpensiveObject instance = lazyInit.getInstance();
      createdInstances.add(instance);
      return instance;
    }, iterations);
    System.out.println("Created instances: " + createdInstances);
    return createdInstances.size();
  }
}
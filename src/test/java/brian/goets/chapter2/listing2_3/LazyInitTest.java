package brian.goets.chapter2.listing2_3;

import org.junit.jupiter.api.RepeatedTest;

import java.util.Set;
import java.util.stream.Collectors;

import static brian.goets.test.util.TaskIterator.executeNumberOfTimes;
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

  @RepeatedTest(10)
  void doGivenNumberOfInitializationsViaLazyInitDelayedRace() throws InterruptedException {
    LazyInit lazyInit = new LazyInitDelayedRace();

    int createdInstancesNumber = doGivenNumberOfConcurrentInitializations(lazyInit, NUMBER_OF_ITERATIONS);

    assertThat(createdInstancesNumber).isNotEqualTo(SINGLE_INSTANCE);
  }

  @RepeatedTest(10)
  void doGivenNumberOfInitializationsViaLazyInitAtomic() throws InterruptedException {
    LazyInit lazyInit = new LazyInitAtomic();

    int createdInstancesNumber = doGivenNumberOfConcurrentInitializations(lazyInit, NUMBER_OF_ITERATIONS);

    assertThat(createdInstancesNumber).isEqualTo(SINGLE_INSTANCE);
  }

  @RepeatedTest(10)
  void doGivenNumberOfInitializationsViaLazyInitDoubleCheck() throws InterruptedException {
    LazyInit lazyInit = new LazyInitDoubleCheck();

    int createdInstancesNumber = doGivenNumberOfConcurrentInitializations(lazyInit, NUMBER_OF_ITERATIONS);

    assertThat(createdInstancesNumber).isEqualTo(SINGLE_INSTANCE);
  }

  @RepeatedTest(10)
  void doGivenNumberOfInitializationsViaLazyInitCas() throws InterruptedException {
    LazyInit lazyInit = new LazyInitCas();

    int createdInstancesNumber = doGivenNumberOfConcurrentInitializations(lazyInit, NUMBER_OF_ITERATIONS);

    assertThat(createdInstancesNumber).isEqualTo(SINGLE_INSTANCE);
  }

  private int doGivenNumberOfConcurrentInitializations(LazyInit lazyInit, int iterations) throws InterruptedException {
    Set<ExpensiveObject> createdInstances = executeNumberOfTimes(lazyInit::getInstance, iterations)
        .stream()
        .collect(Collectors.toSet());
    System.out.println("Created instances: " + createdInstances);
    return createdInstances.size();
  }
}
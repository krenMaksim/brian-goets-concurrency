package brian.goets.chapter5.listing5_5;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ConcurrentModificationDuringIterationTest {

  @Test
  void doSomething() {
    List<Widget> widgetList
        = Collections.synchronizedList(new ArrayList<>());

    // run submit iteration

    // submit deletion of elements
  }

  private static class Widget {}
}

package brian.goets.chapter6.listing6_10;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class RendererTest {

  @Test
  void renderPage() {
    String page = "Some page";
    Renderer renderer = new SingleThreadRenderer();

    assertThatCode(() -> renderer.renderPage(page)).doesNotThrowAnyException();
  }
}
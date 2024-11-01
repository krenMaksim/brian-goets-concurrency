package brian.goets.chapter6.listing6_10;

import brian.goets.chapter6.listing6_13.FutureRenderer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class RendererTest {

  private static final String PAGE = "Some page";

  @Test
  void renderPageViaSingleThreadRenderer() {
    Renderer renderer = new SingleThreadRenderer();

    assertThatCode(() -> renderer.renderPage(PAGE)).doesNotThrowAnyException();
  }

  @Test
  void renderPageViaFutureRenderer() {
    Renderer renderer = new FutureRenderer();

    assertThatCode(() -> renderer.renderPage(PAGE)).doesNotThrowAnyException();
  }
}
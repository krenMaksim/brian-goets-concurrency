package brian.goets.chapter6.listing6_13;

import brian.goets.chapter6.listing6_10.Renderer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FutureRenderer extends Renderer {

  private final ExecutorService executorService;

  public FutureRenderer() {
    this.executorService = Executors.newFixedThreadPool(10);
  }

  public void renderPage(CharSequence source) {
    renderText(source);
    scanForImageInfo(source).stream()
        .map(ImageInfo::downloadImage)
        .forEach(this::renderImage);
  }
}

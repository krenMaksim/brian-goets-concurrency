package brian.goets.chapter6.listing6_13;

import brian.goets.chapter6.listing6_10.Renderer;

public class FutureRenderer extends Renderer {

  public void renderPage(CharSequence source) {
    renderText(source);
    scanForImageInfo(source).stream()
        .map(ImageInfo::downloadImage)
        .forEach(this::renderImage);
  }
}

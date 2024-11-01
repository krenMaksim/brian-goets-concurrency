package brian.goets.chapter6.listing6_10;

class SingleThreadRenderer extends Renderer {

  public void renderPage(CharSequence source) {
    renderText(source);
    scanForImageInfo(source).stream()
        .map(ImageInfo::downloadImage)
        .forEach(this::renderImage);
  }
}

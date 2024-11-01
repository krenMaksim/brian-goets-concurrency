package brian.goets.chapter6.listing6_10;

class SingleThreadRenderer extends Renderer {

  public void renderPage(CharSequence page) {
    renderText(page);
    scanForImageInfo(page).stream()
        .map(ImageInfo::downloadImage)
        .forEach(this::renderImage);
  }
}

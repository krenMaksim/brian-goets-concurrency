package brian.goets.chapter6.listing6_10;

import java.util.List;

abstract class SingleThreadRenderer {

  void renderPage(CharSequence source) {
    renderText(source);
    scanForImageInfo(source)
        .stream()
        .map(ImageInfo::downloadImage)
        .forEach(this::renderImage);
  }

  interface ImageData {
  }

  interface ImageInfo {

    ImageData downloadImage();
  }

  abstract void renderText(CharSequence s);

  abstract List<ImageInfo> scanForImageInfo(CharSequence s);

  abstract void renderImage(ImageData i);
}

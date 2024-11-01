package brian.goets.chapter6.listing6_10;

import java.util.List;
import java.util.stream.Collectors;

abstract class Renderer {

  protected abstract void renderPage(CharSequence source);

  protected void renderText(CharSequence text) {
    System.out.println("[text: " + text + "]");
  }

  protected List<ImageInfo> scanForImageInfo(CharSequence text) {
    return text.chars()
        .mapToObj(i -> new ImageInfoImpl())
        .collect(Collectors.toUnmodifiableList());
  }

  protected void renderImage(ImageData imageData) {
    System.out.println("[image: " + imageData + "]");
  }

  interface ImageData {
  }

  interface ImageInfo {

    ImageData downloadImage();
  }

  private static class ImageInfoImpl implements ImageInfo {

    @Override
    public ImageData downloadImage() {
      return new ImageData() {
        @Override
        public int hashCode() {
          return super.hashCode();
        }
      };
    }
  }
}

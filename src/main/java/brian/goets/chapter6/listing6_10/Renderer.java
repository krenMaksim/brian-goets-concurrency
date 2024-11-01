package brian.goets.chapter6.listing6_10;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public abstract class Renderer {

  public abstract void renderPage(CharSequence source);

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

  public interface ImageData {
  }

  public interface ImageInfo {

    ImageData downloadImage();
  }

  private static class ImageInfoImpl implements ImageInfo {

    @Override
    public ImageData downloadImage() {
      imitateImageDownloading();
      return new ImageDataImpl();
    }

    private static void imitateImageDownloading() {
      LongStream.rangeClosed(1, 10_000_000_000L).sum();
    }

    private static class ImageDataImpl implements ImageData {

    }
  }
}

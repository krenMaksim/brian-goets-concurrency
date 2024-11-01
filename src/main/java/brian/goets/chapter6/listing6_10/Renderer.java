package brian.goets.chapter6.listing6_10;

import java.util.List;
import java.util.stream.LongStream;

public abstract class Renderer {

  public abstract void renderPage(CharSequence source);

  protected void renderText(CharSequence text) {
    System.out.printf("[%s] \t [text: %s]%n", Thread.currentThread().getName(), text);
  }

  protected List<ImageInfo> scanForImageInfo(CharSequence text) {
    return text.chars()
        .mapToObj(i -> new ImageInfoImpl())
        .map(ImageInfo.class::cast)
        .toList();
  }

  protected void renderImage(ImageData imageData) {
    System.out.printf("[%s] \t [image: %s]%n", Thread.currentThread().getName(), imageData);
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

    private void imitateImageDownloading() {
      System.out.printf("[%s] \t [downloading image: %s]%n", Thread.currentThread().getName(), this);
      LongStream.rangeClosed(1, 10_000_000_000L).sum();
    }

    private static class ImageDataImpl implements ImageData {

    }
  }
}

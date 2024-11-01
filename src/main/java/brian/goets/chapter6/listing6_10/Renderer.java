package brian.goets.chapter6.listing6_10;

import java.util.List;
import java.util.stream.LongStream;

public abstract class Renderer {

  public abstract void renderPage(CharSequence source);

  protected void renderText(CharSequence text) {
    System.out.printf("[%s] \t\t\t [rendering text: %s]%n", Thread.currentThread().getName(), text);
  }

  protected List<ImageInfo> scanForImageInfo(CharSequence text) {
    return text.chars()
        .mapToObj(i -> new ImageInfoImpl())
        .map(ImageInfo.class::cast)
        .toList();
  }

  protected void renderImage(ImageData imageData) {
    System.out.printf("[%s] \t\t\t [rendering image: %s]%n", Thread.currentThread().getName(), imageData);
  }

  public interface ImageData {
  }

  public interface ImageInfo {

    ImageData downloadImage();
  }

  private static class ImageInfoImpl implements ImageInfo {

    @Override
    public ImageData downloadImage() {
      ImageData imageData = new ImageDataImpl();
      imitateImageDownloading(imageData);
      return imageData;
    }

    private static void imitateImageDownloading(ImageData imageData) {
      System.out.printf("[%s] \t\t\t [downloading image: %s]%n", Thread.currentThread().getName(), imageData);
      LongStream.rangeClosed(1, 5_000_000_000L).sum();
    }

    private static class ImageDataImpl implements ImageData {

    }
  }
}

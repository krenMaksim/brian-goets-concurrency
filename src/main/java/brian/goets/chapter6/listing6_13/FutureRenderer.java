package brian.goets.chapter6.listing6_13;

import brian.goets.chapter6.listing6_10.Renderer;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static brian.goets.chapter5.listing5_13.LaunderThrowable.launderThrowable;

public class FutureRenderer extends Renderer {

  private final ExecutorService executor;

  public FutureRenderer() {
    this.executor = Executors.newFixedThreadPool(10);
  }

  public void renderPage(CharSequence page) {
    Future<List<ImageData>> downloadingImagesTask = executor.submit(() -> downloadImages(page));

    renderText(page);

    getTaskResult(downloadingImagesTask)
        .forEach(this::renderImage);
  }

  private List<ImageData> downloadImages(CharSequence source) {
    return scanForImageInfo(source).stream()
        .map(ImageInfo::downloadImage)
        .collect(Collectors.toUnmodifiableList());
  }

  private static List<ImageData> getTaskResult(Future<List<ImageData>> task) {
    try {
      return task.get();
    } catch (InterruptedException e) {
      // Re-assert the thread’s interrupted status
      Thread.currentThread().interrupt();
      // We don’t need the result, so cancel the task too
      task.cancel(true);
      return List.of();
    } catch (ExecutionException e) {
      throw launderThrowable(e.getCause());
    }
  }
}

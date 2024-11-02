package brian.goets.chapter6.listing6_15;

import brian.goets.chapter6.listing6_10.Renderer;

import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import static brian.goets.chapter5.listing5_13.LaunderThrowable.launderThrowable;

public class ExecutorCompletionServiceRenderer extends Renderer {

  private final CompletionService<ImageData> completionService;

  public ExecutorCompletionServiceRenderer() {
    ExecutorService executor = Executors.newFixedThreadPool(10);
    completionService = new ExecutorCompletionService<>(executor);
  }

  public void renderPage(CharSequence page) {
    List<ImageInfo> imagesInfo = scanForImageInfo(page);
    imagesInfo.forEach(imageInfo -> completionService.submit(imageInfo::downloadImage));

    renderText(page);

    Stream.generate(this::getCompletedTaskResult)
        .limit(imagesInfo.size())
        .forEach(this::renderImage);
  }

  private ImageData getCompletedTaskResult() {
    try {
      Future<ImageData> task = completionService.take();
      return task.get();
    } catch (InterruptedException e) {
      // Re-assert the thread’s interrupted status
      Thread.currentThread().interrupt();
      // The task does not need to be cancelled due to only completed result is returned
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw launderThrowable(e.getCause());
    }
  }
}

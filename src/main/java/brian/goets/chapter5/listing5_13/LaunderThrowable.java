package brian.goets.chapter5.listing5_13;

public class LaunderThrowable {

  /**
   * Coerce an unchecked Throwable to a RuntimeException
   * <p/>
   * If the Throwable is an Error, throw it; if it is a
   * RuntimeException return it, otherwise throw IllegalStateException
   */
  public static RuntimeException launderThrowable(Throwable t) {
    if (t instanceof RuntimeException runtimeException) {
      return runtimeException;
    } else if (t instanceof Error error) {
      throw error;
    } else {
      throw new IllegalStateException("Not unchecked", t);
    }
  }
}
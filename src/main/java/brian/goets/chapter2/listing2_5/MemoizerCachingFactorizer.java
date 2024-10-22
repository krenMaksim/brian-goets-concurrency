package brian.goets.chapter2.listing2_5;

import brian.goets.chapter5.listing5_16.Memoizer;
import net.jcip.annotations.ThreadSafe;

import java.math.BigInteger;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@ThreadSafe
class MemoizerCachingFactorizer extends CachingFactorizer {

  private final Memoizer<BigInteger, BigInteger[]> cache;

  public MemoizerCachingFactorizer() {
    cache = new Memoizer<>(this::factor);
  }

  @Override
  public void service(ServletRequest req, ServletResponse resp) {
    BigInteger factorNumber = extractFromRequest(req);
    BigInteger[] factors = calculateFactors(factorNumber);
    encodeIntoResponse(resp, factors);
  }

  private BigInteger[] calculateFactors(BigInteger factorNumber) {
    try {
      return cache.compute(factorNumber);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }
}

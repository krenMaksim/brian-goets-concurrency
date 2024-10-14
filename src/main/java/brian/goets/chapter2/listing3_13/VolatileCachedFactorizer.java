package brian.goets.chapter2.listing3_13;

import brian.goets.chapter2.listing2_5.CachingFactorizer;
import brian.goets.chapter2.listing3_12.OneValueCache;
import net.jcip.annotations.ThreadSafe;

import java.math.BigInteger;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@ThreadSafe
public class VolatileCachedFactorizer extends CachingFactorizer {

  private volatile OneValueCache cache = new OneValueCache(null, null);

  @Override
  public void service(ServletRequest req, ServletResponse resp) {
    BigInteger i = extractFromRequest(req);
    BigInteger[] factors = cache.getFactors(i);
    if (factors == null) {
      factors = factor(i);
      cache = new OneValueCache(i, factors);
    }
    encodeIntoResponse(resp, factors);
  }
}

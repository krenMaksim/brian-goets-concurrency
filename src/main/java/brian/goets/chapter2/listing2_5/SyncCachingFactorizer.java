package brian.goets.chapter2.listing2_5;

import net.jcip.annotations.ThreadSafe;

import java.math.BigInteger;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@ThreadSafe
class SyncCachingFactorizer extends CachingFactorizer {

  private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
  private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

  @Override
  public void service(ServletRequest req, ServletResponse resp) {
    BigInteger factorNumber = extractFromRequest(req);
    BigInteger[] factors = fetchFromCache(factorNumber);

    if (Objects.isNull(factors)) {
      factors = factor(factorNumber);
      updateCache(factorNumber, factors);
    }
    encodeIntoResponse(resp, factors);
  }

  private synchronized BigInteger[] fetchFromCache(BigInteger factorNumber) {
    if (factorNumber.equals(lastNumber.get())) {
      return lastFactors.get();
    }
    return null;
  }

  private synchronized void updateCache(BigInteger factorNumber, BigInteger[] factors) {
    lastNumber.set(factorNumber);
    lastFactors.set(factors);
  }
}
package brian.goets.chapter2.listing2_5;

import net.jcip.annotations.ThreadSafe;

import java.math.BigInteger;
import java.util.Objects;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@ThreadSafe
class SyncCachingFactorizer extends CachingFactorizer {

  private BigInteger lastNumber;
  private BigInteger[] lastFactors;

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

  // Here we have atomic check and get
  private synchronized BigInteger[] fetchFromCache(BigInteger factorNumber) {
    if (factorNumber.equals(lastNumber)) {
      return lastFactors.clone(); // I'm still hesitant whether we need to clone here
    }
    return null;
  }

  // Here we have atomic double set
  private synchronized void updateCache(BigInteger factorNumber, BigInteger[] factors) {
    lastNumber = factorNumber;
    lastFactors = factors.clone(); // I'm still hesitant whether we need to clone here
  }
}
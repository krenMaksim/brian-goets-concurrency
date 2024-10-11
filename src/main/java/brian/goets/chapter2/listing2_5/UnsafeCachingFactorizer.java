package brian.goets.chapter2.listing2_5;

import net.jcip.annotations.NotThreadSafe;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@NotThreadSafe
class UnsafeCachingFactorizer extends CachingFactorizer {

  private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
  private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

  @Override
  public void service(ServletRequest req, ServletResponse resp) {
    BigInteger i = extractFromRequest(req);
    if (i.equals(lastNumber.get())) {
      encodeIntoResponse(resp, lastFactors.get());
    } else {
      BigInteger[] factors = factor(i);
      lastNumber.set(i);
      lastFactors.set(factors);
      encodeIntoResponse(resp, factors);
    }
  }
}
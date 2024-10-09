package brian.goets.chapter2.listing2_2;

import net.jcip.annotations.NotThreadSafe;

import java.math.BigInteger;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@NotThreadSafe
class UnsafeCountingFactorizer extends FactorizerServlet {

  private long count = 0;

  @Override
  public void service(ServletRequest req, ServletResponse resp) {
    BigInteger i = extractFromRequest(req);
    BigInteger[] factors = factor(i);
    ++count;
    encodeIntoResponse(resp, factors);
  }

  @Override
  public long getCount() {
    return count;
  }
}
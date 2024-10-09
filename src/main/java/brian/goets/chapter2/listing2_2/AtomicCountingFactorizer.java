package brian.goets.chapter2.listing2_2;

import net.jcip.annotations.ThreadSafe;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@ThreadSafe
class AtomicCountingFactorizer extends FactorizerServlet {

  private final AtomicLong count = new AtomicLong();

  @Override
  public void service(ServletRequest req, ServletResponse resp) {
    BigInteger i = extractFromRequest(req);
    BigInteger[] factors = factor(i);
    count.incrementAndGet();
    encodeIntoResponse(resp, factors);
  }

  @Override
  public long getCount() {
    return count.get();
  }
}
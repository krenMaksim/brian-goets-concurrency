package brian.goets.chapter2.listing2_2;

import net.jcip.annotations.NotThreadSafe;

import java.math.BigInteger;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@NotThreadSafe
class UnsafeCountingFactorizer extends GenericServlet implements Servlet {

  private long count = 0;

  public long getCount() {
    return count;
  }

  @Override
  public void service(ServletRequest req, ServletResponse resp) {
    BigInteger i = extractFromRequest(req);
    BigInteger[] factors = factor(i);
    ++count;
    encodeIntoResponse(resp, factors);
  }

  private void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {
  }

  private BigInteger extractFromRequest(ServletRequest req) {
    return new BigInteger("7");
  }

  private BigInteger[] factor(BigInteger i) {
    return new BigInteger[] {i};
  }
}
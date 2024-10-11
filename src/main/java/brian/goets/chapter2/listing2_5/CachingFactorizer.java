package brian.goets.chapter2.listing2_5;

import java.math.BigInteger;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

abstract class CachingFactorizer extends GenericServlet implements Servlet {

  public abstract void service(ServletRequest req, ServletResponse resp);

  void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    ServletHelper.updateResponseWithFactors(resp, factors);
  }

  BigInteger extractFromRequest(ServletRequest req) {
    return ServletHelper.extractFactorNumber(req);
  }

  BigInteger[] factor(BigInteger i) {
    // Doesn't really factor
    return new BigInteger[] {i};
  }
}
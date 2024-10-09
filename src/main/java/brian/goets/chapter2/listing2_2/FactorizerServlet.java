package brian.goets.chapter2.listing2_2;

import java.math.BigInteger;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

abstract class FactorizerServlet extends GenericServlet implements Servlet {

  @Override
  public abstract void service(ServletRequest req, ServletResponse resp);

  public abstract long getCount();

  void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {
    // STUB
  }

  BigInteger extractFromRequest(ServletRequest req) {
    return new BigInteger("7");
  }

  BigInteger[] factor(BigInteger i) {
    return new BigInteger[] {i};
  }
}

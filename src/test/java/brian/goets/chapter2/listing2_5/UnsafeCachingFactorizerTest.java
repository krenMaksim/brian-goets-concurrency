package brian.goets.chapter2.listing2_5;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class UnsafeCachingFactorizerTest {

  @Test
  void doSomething() throws IOException {
    UnsafeCachingFactorizer factorizer = new UnsafeCachingFactorizer();
    ServletRequest request = ServletHelper.newServletRequestWithFactorNumber(new BigInteger("42"));
    ServletResponse response = ServletHelper.newServletResponse();

    factorizer.service(request, response);
    BigInteger[] factors = ServletHelper.extractFactors(response);

    assertThat(factors).isEqualTo(new BigInteger[] {new BigInteger("42")});
  }
}
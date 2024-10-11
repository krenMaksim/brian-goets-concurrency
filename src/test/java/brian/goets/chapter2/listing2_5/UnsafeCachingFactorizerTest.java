package brian.goets.chapter2.listing2_5;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.math.BigInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

class UnsafeCachingFactorizerTest {

  @Test
  void doSomething() throws IOException {
    UnsafeCachingFactorizer fact = new UnsafeCachingFactorizer();
    ServletRequest request = Mockito.mock(ServletRequest.class);
    Mockito.when(request.getAttribute("value")).thenReturn(new BigInteger("666"));
    ServletResponse response = Mockito.mock(ServletResponse.class);
    Mockito.when(response.getContentType()).thenReturn("list of factors");
  }

  static class ServletHelper {

    private static final String VALUE = "value";
    public static final String FACTORS_DELIMITER = " ";

    public static ServletRequest newRequestWithNumber(BigInteger number) {
      ServletRequest request = Mockito.mock(ServletRequest.class);
      Mockito.when(request.getAttribute(VALUE)).thenReturn(number);
      return request;
    }

    public static BigInteger extractNumber(ServletRequest request) {
      return (BigInteger) request.getAttribute(VALUE);
    }

    public static ServletResponse newResponseWithFactors(BigInteger[] factors) {
      ServletResponse response = Mockito.mock(ServletResponse.class);
      String fac = Stream.of(factors)
          .map(BigInteger::toString)
          .collect(Collectors.joining(FACTORS_DELIMITER));
      Mockito.when(response.getContentType()).thenReturn(fac);
      return response;
    }

    public static BigInteger[] extractFactors(ServletResponse response) {
      String[] fac = response.getContentType().split(FACTORS_DELIMITER);
      return Stream.of(fac)
          .map(BigInteger::new)
          .toArray(size -> new BigInteger[size]);
    }
  }
}
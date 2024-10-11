package brian.goets.chapter2.listing2_5;

import org.mockito.Mockito;

import java.math.BigInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

class ServletHelper {

  private static final String FACTOR_NUMBER = "factorNumber";
  public static final String FACTORS_DELIMITER = " ";

  public static ServletRequest newServletRequestWithFactorNumber(BigInteger number) {
    ServletRequest request = Mockito.mock(ServletRequest.class);
    Mockito.when(request.getAttribute(FACTOR_NUMBER)).thenReturn(number);
    return request;
  }

  public static BigInteger extractFactorNumber(ServletRequest request) {
    return (BigInteger) request.getAttribute(FACTOR_NUMBER);
  }

  public static ServletResponse newServletResponse() {
    return Mockito.mock(ServletResponse.class);
  }

  public static void updateResponseWithFactors(ServletResponse response, BigInteger[] factors) {
    String factorsStr = Stream.of(factors)
        .map(BigInteger::toString)
        .collect(Collectors.joining(FACTORS_DELIMITER));
    Mockito.when(response.getContentType()).thenReturn(factorsStr);
  }

  public static BigInteger[] extractFactors(ServletResponse response) {
    String[] factors = response.getContentType().split(FACTORS_DELIMITER);
    return Stream.of(factors)
        .map(BigInteger::new)
        .toArray(size -> new BigInteger[size]);
  }
}

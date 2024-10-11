package brian.goets.chapter2.listing2_5;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.math.BigInteger;
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
}
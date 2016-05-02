package com.zbiljic.failure;

import com.zbiljic.failure.http.HttpStatus;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;
import static org.testng.Assert.assertFalse;

/**
 * @author Nemanja Zbiljic
 */
public class FailureTest extends AbstractFailureTest {

  @Test
  public void testShouldUseDefaultDetail() {
    final Failure failure = new InsufficientFundsFailure(10, -20);

    assertFalse(failure.getDetail().isPresent());
  }

  @Test
  public void testShouldUseDefaultInstance() {
    final Failure failure = new InsufficientFundsFailure(10, -20);

    assertFalse(failure.getInstance().isPresent());
  }

  @Test
  public void testShouldRender() {
    final ThrowableFailure failure = Failure.builder(SERVICE)
        .withStatus(HttpStatus.NOT_FOUND)
        .withTitle("Not Found")
        .build();

    assertThat(failure, hasToString("EXAMPLE{404, 0, Not Found}"));
  }

  @Test
  public void testShouldRenderDetailAndInstance() {
    final ThrowableFailure failure = Failure.builder(SERVICE)
        .withTitle("Not Found")
        .withStatus(HttpStatus.NOT_FOUND)
        .withDetail("Resource 123")
        .withInstance("fail123")
        .build();

    assertThat(failure, hasToString("EXAMPLE{404, 0, Not Found, Resource 123, instance=fail123}"));
  }

  @Test
  public void testShouldRenderCopyOfFailure() {
    final ThrowableFailure failure = Failure.builder(SERVICE)
        .withTitle("Not Found")
        .withStatus(HttpStatus.NOT_FOUND)
        .withDetail("Resource 123")
        .withInstance("fail123")
        .build();

    assertThat(failure, hasToString("EXAMPLE{404, 0, Not Found, Resource 123, instance=fail123}"));

    final Failure failureCopy = Failure.builder(failure)
        .build();

    assertThat(failureCopy, hasToString("EXAMPLE{404, 0, Not Found}"));
  }

}
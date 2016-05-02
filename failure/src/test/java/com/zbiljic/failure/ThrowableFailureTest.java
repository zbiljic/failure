package com.zbiljic.failure;

import com.zbiljic.failure.http.HttpStatus;

import org.testng.annotations.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * @author Nemanja Zbiljic
 */
public class ThrowableFailureTest extends AbstractFailureTest {

  @Test
  public void testShouldReturnThrowableFailureCause() throws Exception {
    final ThrowableFailure failure = Failure.builder(SERVICE)
        .withTitle("Preauthorization Failed")
        .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        .withCause(Failure.builder(SERVICE)
            .withTitle("Expired Credit Card")
            .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
            .build())
        .build();

    assertNotNull(failure.getCause());
  }

  @Test
  public void testShouldReturnNullCause() throws Exception {
    final ThrowableFailure failure = Failure.builder(SERVICE)
        .withTitle("Preauthorization Failed")
        .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        .build();

    assertNull(failure.getCause());
  }

  @Test
  public void shouldReturnTitleAsMessage() throws Exception {
    final ThrowableFailure failure = Failure.builder(SERVICE)
        .withTitle("Preauthorization Failed")
        .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        .build();

    assertThat(failure.getMessage(), is("Preauthorization Failed"));
  }

  @Test
  public void testShouldReturnTitleAndDetailAsMessage() throws Exception {
    final ThrowableFailure failure = Failure.builder(SERVICE)
        .withTitle("Preauthorization Failed")
        .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        .withDetail("CVC invalid")
        .build();

    assertThat(failure.getMessage(), is("Preauthorization Failed: CVC invalid"));
  }

  @Test
  public void testShouldReturnCausesMessage() throws Exception {
    final ThrowableFailure failure = Failure.builder(SERVICE)
        .withTitle("Preauthorization Failed")
        .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        .withCause(Failure.builder(SERVICE)
            .withTitle("Expired Credit Card")
            .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
            .build())
        .build();

    assertNotNull(failure.getCause());
    assertThat(failure.getCause().getMessage(), is("Expired Credit Card"));
  }

  @Test
  public void testShouldPrintStackTrace() throws Exception {
    final ThrowableFailure failure = Failure.builder(SERVICE)
        .withTitle("Preauthorization Failed")
        .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        .withCause(Failure.builder(SERVICE)
            .withTitle("Expired Credit Card")
            .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
            .build())
        .build();

    final String stacktrace = getStackTrace(failure);

    assertThat(stacktrace, startsWith("EXAMPLE{422, 0, Preauthorization Failed}"));
    assertThat(stacktrace, containsString("Caused by: EXAMPLE{422, 0, Expired Credit Card}"));
  }

  @Test
  public void testShouldProcessStackTrace() {
    final ThrowableFailure failure = Failure.builder(SERVICE)
        .withTitle("Preauthorization Failed")
        .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        .withCause(Failure.builder(SERVICE)
            .withTitle("Expired Credit Card")
            .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
            .build())
        .build();

    final String stacktrace = getStackTrace(failure);

    assertThat(stacktrace, not(containsString("org.testng")));
  }

  private String getStackTrace(final Throwable throwable) {
    final StringWriter writer = new StringWriter();
    throwable.printStackTrace(new PrintWriter(writer));
    return writer.toString();
  }

}
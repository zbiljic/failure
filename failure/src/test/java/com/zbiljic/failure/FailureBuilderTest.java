package com.zbiljic.failure;

import com.zbiljic.failure.http.HttpStatus;

import org.testng.annotations.Test;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Nemanja Zbiljic
 */
public class FailureBuilderTest extends AbstractFailureTest {

  @Test
  public void testShouldCreateFailure() throws Exception {
    final Failure failure = Failure.builder(SERVICE)
        .withTitle("Out of Stock")
        .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        .build();

    assertThat(failure.getService(), is(SERVICE));
    assertThat(failure.getTitle(), is("Out of Stock"));
    assertThat(failure.getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
    assertThat(failure.getCode(), equalTo(0));
    assertThat(failure.getDetail(), equalTo(empty()));
    assertThat(failure.getInstance(), equalTo(empty()));
  }

  @Test
  public void testShouldCreateFailureWithDetail() throws Exception {
    final Failure failure = Failure.builder(SERVICE)
        .withTitle("Out of Stock")
        .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        .withDetail("Item B00027Y5QG is no longer available")
        .build();

    assertThat(failure.getDetail(), equalTo(Optional.of("Item B00027Y5QG is no longer available")));
  }

  @Test
  public void testShouldCreateFailureWithInstance() throws Exception {
    final Failure failure = Failure.builder(SERVICE)
        .withTitle("Out of Stock")
        .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        .withInstance("fail_B00027Y5QG")
        .build();

    assertThat(failure.getInstance(), equalTo(Optional.of("fail_B00027Y5QG")));
  }

  @Test
  public void shouldCreateFailureWithCause() throws Exception {
    final ThrowableFailure failure = Failure.builder(SERVICE)
        .withTitle("Preauthorization Failed")
        .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        .withCause(Failure.builder(SERVICE)
            .withTitle("Expired Credit Card")
            .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
            .build())
        .build();

    assertThat(failure.getCause(), notNullValue());

    final ThrowableFailure cause = failure.getCause();
    assertThat(cause.getService(), is(SERVICE));
    assertThat(cause.getTitle(), is("Expired Credit Card"));
    assertThat(cause.getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testThrowOnMissingTitle() throws Exception {
    Failure.builder(SERVICE).build();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testThrowOnMissingStatus() throws Exception {
    Failure.builder(SERVICE)
        .withTitle("Not found")
        .build();
  }
}
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
public class DefaultFailureTest extends AbstractFailureTest {

  @Test(expectedExceptions = NullPointerException.class)
  public void testShouldThrowOnNullService() throws Exception {
    throw new DefaultFailure(null, "Not Found", HttpStatus.NOT_FOUND, 0, empty(), empty());
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testShouldThrowOnNullTitle() throws Exception {
    throw new DefaultFailure(SERVICE, null, HttpStatus.NOT_FOUND, 0, empty(), empty());
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testShouldThrowOnNullStatus() throws Exception {
    throw new DefaultFailure(SERVICE, "Not Found", null, 0, empty(), empty());
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testShouldThrowOnNullDetail() throws Exception {
    throw new DefaultFailure(SERVICE, "Not Found", HttpStatus.NOT_FOUND, 0, null, empty());
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testShouldThrowOnNullInstance() throws Exception {
    throw new DefaultFailure(SERVICE, "Not Found", HttpStatus.NOT_FOUND, 0, empty(), null);
  }

  @Test
  public void testShouldImplementProblem() throws Exception {
    final Failure failure = new DefaultFailure(SERVICE,
        "Not Found",
        HttpStatus.NOT_FOUND,
        0,
        Optional.of("Resource could not be found"),
        Optional.of("fail_123"));

    assertThat(failure.getService(), is(SERVICE));
    assertThat(failure.getTitle(), is("Not Found"));
    assertThat(failure.getStatus(), equalTo(HttpStatus.NOT_FOUND.value()));
    assertThat(failure.getCode(), equalTo(0));
    assertThat(failure.getDetail(), notNullValue());
    assertThat(failure.getDetail(), equalTo(Optional.of("Resource could not be found")));
    assertThat(failure.getInstance(), notNullValue());
    assertThat(failure.getInstance(), equalTo(Optional.of("fail_123")));
  }
}
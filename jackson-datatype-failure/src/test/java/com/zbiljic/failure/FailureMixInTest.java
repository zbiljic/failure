package com.zbiljic.failure;

import com.zbiljic.failure.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import org.testng.annotations.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;

/**
 * @author Nemanja Zbiljic
 */
public class FailureMixInTest {

  static final String SERVICE = "EXAMPLE";

  private static final ObjectMapper mapper = new ObjectMapper()
      .registerModule(new Jdk8Module())
      .registerModule(new FailureModule());

  public FailureMixInTest() {
    mapper.registerSubtypes(InsufficientFundsFailure.class);
  }

  private URL getResource(String name) {
    return getClass().getClassLoader().getResource(name);
  }

  @Test
  public void testShouldSerializeDefaultFailure() throws JsonProcessingException {
    final Failure failure = Failure.builder(SERVICE)
        .withTitle("Not Found")
        .withStatus(HttpStatus.NOT_FOUND)
        .build();
    final String json = mapper.writeValueAsString(failure);

    with(json)
        .assertThat("$.*", hasSize(4))
        .assertThat("$.service", is(SERVICE))
        .assertThat("$.title", is("Not Found"))
        .assertThat("$.status", is(404))
        .assertThat("$.code", is(0));
  }

  @Test
  public void testShouldSerializeCustomFailure() throws JsonProcessingException {
    final Failure failure = Failure.builder(SERVICE)
        .withTitle("Out of Stock")
        .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        .withCode(9999)
        .withDetail("Item B00027Y5QG is no longer available")
        .build();

    final String json = mapper.writeValueAsString(failure);

    with(json)
        .assertThat("$.*", hasSize(5))
        .assertThat("$.code", is(9999));
  }

  @Test
  public void testShouldSerializeFailureCause() throws JsonProcessingException {
    final Failure failure = Failure.builder(SERVICE)
        .withTitle("Preauthorization Failed")
        .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        .withCause(Failure.builder(SERVICE)
            .withTitle("Expired Credit Card")
            .withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
            .withDetail("Credit card is expired as of 2015-09-16T00:00:00Z")
            .build())
        .build();

    final String json = mapper.writeValueAsString(failure);

    with(json)
        .assertThat("$.cause.service", is(SERVICE))
        .assertThat("$.cause.title", is("Expired Credit Card"))
        .assertThat("$.cause.status", is(422))
        .assertThat("$.cause.code", is(0))
        .assertThat("$.cause.detail", is("Credit card is expired as of 2015-09-16T00:00:00Z"));
  }

  @Test
  public void testShouldNotSerializeStacktraceByDefault() throws JsonProcessingException {
    final Failure failure = Failure.builder(SERVICE)
        .withTitle("Foo")
        .withStatus(HttpStatus.BAD_REQUEST)
        .withCause(Failure.builder(SERVICE)
            .withTitle("Bar")
            .withStatus(HttpStatus.BAD_REQUEST)
            .build())
        .build();

    final String json = mapper.writeValueAsString(failure);

    with(json)
        .assertNotDefined("$.stacktrace");
  }

  @Test
  public void testShouldSerializeStacktrace() throws JsonProcessingException {
    final Failure failure = Failure.builder(SERVICE)
        .withTitle("Foo")
        .withStatus(HttpStatus.BAD_REQUEST)
        .withCause(Failure.builder(SERVICE)
            .withTitle("Bar")
            .withStatus(HttpStatus.BAD_REQUEST)
            .build())
        .build();

    final ObjectMapper mapper = new ObjectMapper()
        .registerModule(new Jdk8Module())
        .registerModule(new FailureModule().withStacktraces());

    final String json = mapper.writeValueAsString(failure);

    with(json)
        .assertThat("$.stacktrace", is(instanceOf(List.class)))
        .assertThat("$.stacktrace[0]", is(instanceOf(String.class)));
  }

  @Test
  public void testShouldDeserializeDefaultFailure() throws IOException {
    final URL resource = getResource("out-of-stock.json");
    final Failure raw = mapper.readValue(resource, Failure.class);

    assertThat(raw, instanceOf(DefaultFailure.class));
    final DefaultFailure failure = (DefaultFailure) raw;

    assertThat(failure.getService(), is(SERVICE));
    assertThat(failure.getTitle(), is("Out of Stock"));
    assertThat(failure.getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
    assertThat(failure.getCode(), equalTo(0));
    assertThat(failure.getDetail(), notNullValue());
    assertThat(failure.getDetail(), equalTo(Optional.of("Item B00027Y5QG is no longer available")));
    assertThat(failure.getInstance(), is(Optional.empty()));
  }

  @Test
  public void testShouldDeserializeExceptional() throws IOException {
    final URL resource = getResource("out-of-stock.json");
    final Exceptional exceptional = mapper.readValue(resource, Exceptional.class);

    assertThat(exceptional, instanceOf(DefaultFailure.class));
    final DefaultFailure failure = (DefaultFailure) exceptional;

    assertThat(failure.getService(), is(SERVICE));
    assertThat(failure.getTitle(), is("Out of Stock"));
    assertThat(failure.getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
    assertThat(failure.getCode(), equalTo(0));
    assertThat(failure.getDetail(), notNullValue());
    assertThat(failure.getDetail(), equalTo(Optional.of("Item B00027Y5QG is no longer available")));
    assertThat(failure.getInstance(), is(Optional.empty()));
  }

  @Test
  public void testShouldDeserializeSpecificFailure() throws IOException {
    final URL resource = getResource("insufficient-funds.json");
    final Failure failure = mapper.readValue(resource, Failure.class);

    assertThat(failure, instanceOf(InsufficientFundsFailure.class));
  }

  @Test
  public void testShouldDeserializeCause() throws IOException {
    final URL resource = getResource("cause.json");
    final ThrowableFailure failure = mapper.readValue(resource, ThrowableFailure.class);

    assertThat(failure.getCause(), notNullValue());
    final ThrowableFailure cause = failure.getCause();

    assertThat(cause, is(notNullValue()));
    assertThat(cause, instanceOf(DefaultFailure.class));

    final DefaultFailure c = (DefaultFailure) cause;

    assertThat(c.getService(), is(SERVICE));
    assertThat(c.getTitle(), is("Expired Credit Card"));
    assertThat(c.getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
    assertThat(c.getCode(), equalTo(0));
    assertThat(c.getDetail(), notNullValue());
    assertThat(c.getDetail(), equalTo(Optional.of("Credit card is expired as of 2015-09-16T00:00:00Z")));
    assertThat(c.getInstance(), is(Optional.empty()));
  }

  @Test
  public void testShouldDeserializeWithProcessedStackTrace() throws IOException {
    final URL resource = getResource("cause.json");
    final ThrowableFailure failure = mapper.readValue(resource, ThrowableFailure.class);

    final String stackTrace = getStackTrace(failure);
    final String[] stackTraceElements = stackTrace.split("\n");

    assertThat(stackTraceElements[1], startsWith("\tat com.zbiljic.failure.FailureMixInTest"));
  }

  private String getStackTrace(final Throwable throwable) {
    final StringWriter writer = new StringWriter();
    throwable.printStackTrace(new PrintWriter(writer));
    return writer.toString();
  }

}
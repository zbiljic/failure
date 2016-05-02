package com.zbiljic.failure;

import com.zbiljic.failure.http.HttpStatus;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nullable;

/**
 * @author Nemanja Zbiljic
 */
public final class FailureBuilder {

  private String service;
  private String title;
  private HttpStatus status;
  private int code;
  private Optional<String> detail = Optional.empty();
  private Optional<String> instance = Optional.empty();
  @Nullable
  private ThrowableFailure cause;

  /**
   * @see Failure#builder(String)
   */
  FailureBuilder(String service) {
    this.service = Objects.requireNonNull(service, "service must not be null");
  }

  public FailureBuilder withTitle(final String title) {
    this.title = title;
    return this;
  }

  public FailureBuilder withStatus(@Nullable final HttpStatus status) {
    this.status = status;
    return this;
  }

  public FailureBuilder withStatus(int statusCode) {
    this.status = HttpStatus.valueOf(statusCode);
    return this;
  }

  public FailureBuilder withCode(int code) {
    this.code = code;
    return this;
  }

  public FailureBuilder withDetail(@Nullable final String detail) {
    this.detail = Optional.ofNullable(detail);
    return this;
  }

  public FailureBuilder withInstance(@Nullable final String instance) {
    this.instance = Optional.ofNullable(instance);
    return this;
  }

  public FailureBuilder withCause(@Nullable final ThrowableFailure cause) {
    this.cause = cause;
    return this;
  }

  public ThrowableFailure build() {
    return new DefaultFailure(service, title, status, code, detail, instance, cause);
  }

}

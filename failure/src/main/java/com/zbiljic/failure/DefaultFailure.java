package com.zbiljic.failure;

import com.zbiljic.failure.http.HttpStatus;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * @author Nemanja Zbiljic
 */
@Immutable
public final class DefaultFailure extends ThrowableFailure {

  private final String service;
  private final HttpStatus status;
  private final int code;
  private final String title;
  private final Optional<String> detail;
  private final Optional<String> instance;

  DefaultFailure(final String service,
                 final String title,
                 final int statusCode,
                 final int code,
                 final Optional<String> detail,
                 final Optional<String> instance) {
    this(service, title, HttpStatus.valueOf(statusCode), code, detail, instance, null);
  }

  DefaultFailure(final String service,
                 final String title,
                 final HttpStatus status,
                 final int code,
                 final Optional<String> detail,
                 final Optional<String> instance) {
    this(service, title, status, code, detail, instance, null);
  }

  DefaultFailure(final String service,
                 final String title,
                 final HttpStatus status,
                 final int code,
                 final Optional<String> detail,
                 final Optional<String> instance,
                 @Nullable final ThrowableFailure cause) {
    super(cause);
    this.service = Objects.requireNonNull(service, "service must not be null");
    this.title = Objects.requireNonNull(title, "title must not be null");
    this.status = Objects.requireNonNull(status, "status must not be null");
    this.code = code;
    this.detail = Objects.requireNonNull(detail, "detail must not be null");
    this.instance = Objects.requireNonNull(instance, "instance must not be null");
  }

  @Override
  public String getService() {
    return service;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public int getStatus() {
    return status.value();
  }

  @Override
  public int getCode() {
    return code;
  }

  @Override
  public Optional<String> getDetail() {
    return detail;
  }

  @Override
  public Optional<String> getInstance() {
    return instance;
  }
}

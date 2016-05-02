package com.zbiljic.failure;

import com.zbiljic.failure.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

/**
 * @author Nemanja Zbiljic
 */
abstract class DefaultFailureMixIn {

  @JsonCreator
  DefaultFailureMixIn(
      @JsonProperty(value = "service", required = true) final String service,
      @JsonProperty(value = "title", required = true) final String title,
      @JsonProperty(value = "status", required = true) final HttpStatus status,
      @JsonProperty("code") final int code,
      @JsonProperty("detail") final Optional<String> detail,
      @JsonProperty("instance") final Optional<String> instance,
      @JsonProperty("cause") ThrowableFailure cause) {
    throw new DefaultFailure(service, title, status, code, detail, instance, cause);
  }

}

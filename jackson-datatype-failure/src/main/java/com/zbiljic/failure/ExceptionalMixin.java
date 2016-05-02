package com.zbiljic.failure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author Nemanja Zbiljic
 */
@JsonIgnoreProperties(ignoreUnknown = true)
interface ExceptionalMixin {

  @JsonIgnore
  String getMessage();

  @JsonIgnore
  String getLocalizedMessage();

  @JsonInclude(NON_NULL)
  ThrowableFailure getCause();

  @JsonIgnore
  StackTraceElement[] getStackTrace();

  @JsonIgnore
  Throwable[] getSuppressed();

}

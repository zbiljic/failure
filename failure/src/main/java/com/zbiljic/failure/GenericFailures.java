package com.zbiljic.failure;

import com.zbiljic.failure.http.HttpStatus;

/**
 * @author Nemanja Zbiljic
 */
final class GenericFailures {

  static FailureBuilder create(final String service, final HttpStatus status) {
    return Failure.builder(service)
        .withTitle(status.getReasonPhrase())
        .withStatus(status)
        .withCode(status.value());
  }

}

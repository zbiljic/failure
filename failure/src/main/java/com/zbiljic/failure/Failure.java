package com.zbiljic.failure;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.concurrent.Immutable;

/**
 * Failure error details for HTTP APIs
 *
 * @author Nemanja Zbiljic
 */
@Immutable
public interface Failure {

  /**
   * A simple id for service that is specific for this failure. It MUST NOT change from occurrence
   * to occurrence of the failure.
   */
  String getService();

  /**
   * A short, human-readable summary of the failure. It SHOULD NOT change from occurrence to
   * occurrence of the failure.
   */
  String getTitle();

  /**
   * The corresponding HTTP status code.
   */
  int getStatus();

  /**
   * An service-specific error code that can be used to obtain more information.
   */
  int getCode();

  /**
   * A clear, plain text explanation with technical details specific to this occurrence of the
   * failure.
   */
  default Optional<String> getDetail() {
    return Optional.empty();
  }

  /**
   * An string that identifies the specific occurrence of the failure.
   */
  default Optional<String> getInstance() {
    return Optional.empty();
  }

  static FailureBuilder builder(String service) {
    return new FailureBuilder(service);
  }

  static FailureBuilder builder(Failure failure) {
    return new FailureBuilder(failure.getService())
        .withStatus(failure.getStatus())
        .withCode(failure.getCode())
        .withTitle(failure.getTitle());
  }

  /**
   * String builder for the failure.
   *
   * @param failure The failure
   * @return A string representation of the failure
   */
  static String toString(final Failure failure) {

    final StringBuilder helper = new StringBuilder(Objects.toString(failure.getService()));
    helper.append("{");
    helper.append(failure.getStatus());
    helper.append(", ");
    helper.append(failure.getCode());
    helper.append(", ");
    helper.append(failure.getTitle());
    if (failure.getDetail().isPresent()) {
      helper.append(", ");
      helper.append(failure.getDetail().get());
    }
    if (failure.getInstance().isPresent()) {
      helper.append(", ");
      helper.append("instance=");
      helper.append(failure.getInstance().get());
    }
    helper.append("}");

    return helper.toString();
  }

}

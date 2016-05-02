package com.zbiljic.failure;

import com.zbiljic.failure.http.HttpStatus;

import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author Nemanja Zbiljic
 */
public final class FailureModule extends SimpleModule {

  private final boolean stacktraces;

  public FailureModule() {
    this(false);
  }

  public FailureModule(boolean stacktraces) {
    super(FailureModule.class.getSimpleName(), PackageVersion.VERSION);
    this.stacktraces = stacktraces;
  }

  @Override
  public void setupModule(SetupContext context) {
    setMixInAnnotation(Exceptional.class, stacktraces
        ? ExceptionalWithStacktraceMixin.class
        : ExceptionalMixin.class);

    setMixInAnnotation(DefaultFailure.class, DefaultFailureMixIn.class);
    setMixInAnnotation(Failure.class, FailureMixIn.class);

    addSerializer(HttpStatus.class, new HttpStatusSerializer());
    addDeserializer(HttpStatus.class, new HttpStatusDeserializer());

    super.setupModule(context);
  }

  public FailureModule withStacktraces() {
    return withStacktraces(true);
  }

  public FailureModule withStacktraces(final boolean stacktraces) {
    return new FailureModule(stacktraces);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return this == o;
  }
}

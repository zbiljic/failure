package com.zbiljic.failure;

import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import static com.zbiljic.failure.spi.StackTraceProcessor.COMPOUND;
import static java.util.Arrays.asList;

/**
 * @author Nemanja Zbiljic
 */
@Immutable
public abstract class ThrowableFailure extends RuntimeException implements Failure, Exceptional {

  private static final Collector<CharSequence, ?, String> JOINING = Collectors.joining(": ");

  public ThrowableFailure() {
    this(null);
  }

  public ThrowableFailure(@Nullable final ThrowableFailure cause) {
    super(cause);

    final Collection<StackTraceElement> stackTrace = COMPOUND.process(asList(getStackTrace()));
    setStackTrace(stackTrace.toArray(new StackTraceElement[stackTrace.size()]));
  }

  @Override
  public String getMessage() {
    return Stream.of(getTitle(), getDetail().orElse(null))
        .filter(o -> o != null)
        .collect(JOINING);
  }

  @Override
  public ThrowableFailure getCause() {
    // cast is safe, since the only way to set this is our constructor
    return (ThrowableFailure) super.getCause();
  }

  @Override
  public String toString() {
    return Failure.toString(this);
  }

}

package com.zbiljic.failure;

/**
 * An extension of the {@link Failure} interface for failures that extend {@link Exception}. Since
 * {@link Exception} is a concrete type any class can only extend one exception type. {@link
 * ThrowableFailure} is one choice, but we don't want to force people to extend from this but choose
 * their own super class. For this they can implement this interface and get the same handling as
 * {@link ThrowableFailure} for free. A common use case would be:
 *
 * <pre>{@code
 * public final class MissingException extends ServiceException implements Exceptional
 * }</pre>
 *
 * @author Nemanja Zbiljic
 * @see Exception
 * @see Failure
 * @see ThrowableFailure
 */
public interface Exceptional extends Failure {

  Exceptional getCause();

  default Exception propagate() throws Exception {
    throw propagateAs(Exception.class);
  }

  default <X extends Throwable> X propagateAs(final Class<X> type) throws X {
    throw type.cast(this);
  }

}

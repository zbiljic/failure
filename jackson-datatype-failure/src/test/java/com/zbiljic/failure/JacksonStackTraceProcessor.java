package com.zbiljic.failure;

import com.zbiljic.failure.spi.StackTraceProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Nemanja Zbiljic
 */
public final class JacksonStackTraceProcessor implements StackTraceProcessor {

  @Override
  public Collection<StackTraceElement> process(final Collection<StackTraceElement> collection) {
    final List<StackTraceElement> elements = new ArrayList<>(collection);

    return elements.stream()
        .filter(startsWith("sun.reflect", "java.lang.reflect", "com.fasterxml.jackson").negate())
        .findFirst()
        .map(elements::indexOf)
        .map(subList(elements))
        .orElse(elements);
  }

  private Predicate<StackTraceElement> startsWith(final String... prefixes) {
    return element -> Arrays.stream(prefixes)
        .anyMatch(prefix -> element.getClassName().startsWith(prefix));
  }

  private <T> Function<Integer, List<T>> subList(final List<T> list) {
    return index -> list.subList(index, list.size());
  }

}

package com.zbiljic.failure;

import com.zbiljic.failure.spi.StackTraceProcessor;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

/**
 * @author Nemanja Zbiljic
 */
public final class TestNGStackTraceProcessor implements StackTraceProcessor {

  @Override
  public Collection<StackTraceElement> process(final Collection<StackTraceElement> elements) {
    return elements.stream()
        .filter(element -> !element.getClassName().startsWith("org.testng"))
        .collect(toList());
  }
}

package com.zbiljic.failure;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * @author Nemanja Zbiljic
 */
interface ExceptionalWithStacktraceMixin extends ExceptionalMixin {

  @Override
  @JsonProperty("stacktrace")
  @JsonSerialize(contentUsing = ToStringSerializer.class)
  StackTraceElement[] getStackTrace();

}

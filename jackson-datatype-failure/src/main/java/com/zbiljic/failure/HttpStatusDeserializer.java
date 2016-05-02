package com.zbiljic.failure;

import com.zbiljic.failure.http.HttpStatus;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @author Nemanja Zbiljic
 */
final class HttpStatusDeserializer extends JsonDeserializer<HttpStatus> {

  @Override
  public HttpStatus deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
    try {
      return HttpStatus.valueOf(parser.getIntValue());
    } catch (IllegalArgumentException e) {
      return null;
    }
  }
}

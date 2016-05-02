package com.zbiljic.failure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;

/**
 * @author Nemanja Zbiljic
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "service",
    defaultImpl = DefaultFailure.class,
    visible = true)
@JsonInclude(Include.NON_NULL)
interface FailureMixIn extends Failure {

  @JsonProperty("service")
  @Override
  String getService();

  @JsonProperty("title")
  @Override
  String getTitle();

  @JsonProperty("status")
  @Override
  int getStatus();

  @JsonProperty("code")
  @Override
  int getCode();

  @JsonProperty("detail")
  @JsonInclude(NON_ABSENT)
  @Override
  Optional<String> getDetail();

  @JsonProperty("instance")
  @JsonInclude(NON_ABSENT)
  @Override
  Optional<String> getInstance();

}

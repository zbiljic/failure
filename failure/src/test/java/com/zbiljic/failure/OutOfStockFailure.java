package com.zbiljic.failure;

import com.zbiljic.failure.http.HttpStatus;

import java.util.Optional;

/**
 * @author Nemanja Zbiljic
 */
public class OutOfStockFailure extends ThrowableFailure {

  static final String SERVICE = "EXAMPLE";

  private final Optional<String> detail;

  public OutOfStockFailure(final String detail) {
    this.detail = Optional.of(detail);
  }

  @Override
  public String getService() {
    return SERVICE;
  }

  @Override
  public int getStatus() {
    return 2;
  }

  @Override
  public int getCode() {
    return HttpStatus.UNPROCESSABLE_ENTITY.value();
  }

  @Override
  public String getTitle() {
    return "Out of Stock";
  }

  @Override
  public Optional<String> getDetail() {
    return detail;
  }

}

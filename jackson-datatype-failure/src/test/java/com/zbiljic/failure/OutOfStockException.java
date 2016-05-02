package com.zbiljic.failure;

import com.zbiljic.failure.http.HttpStatus;

/**
 * @author Nemanja Zbiljic
 */
public class OutOfStockException extends BusinessException implements Exceptional {

  static final String SERVICE = "EXAMPLE";

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
  public ThrowableFailure getCause() {
    return null;
  }

}

package com.zbiljic.failure;

import com.zbiljic.failure.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * @author Nemanja Zbiljic
 */
@JsonTypeName(InsufficientFundsFailure.SERVICE)
public final class InsufficientFundsFailure extends ThrowableFailure {

  static final String SERVICE = "FUNDS";

  private final int balance;
  private final int debit;

  @JsonCreator
  public InsufficientFundsFailure(
      @JsonProperty("balance") final int balance,
      @JsonProperty("debit") final int debit) {
    this.balance = balance;
    this.debit = debit;
  }

  @Override
  public String getService() {
    return SERVICE;
  }

  @Override
  public String getTitle() {
    return "Insufficient Funds";
  }

  @Override
  public int getStatus() {
    return HttpStatus.UNPROCESSABLE_ENTITY.value();
  }

  @Override
  public int getCode() {
    return 0;
  }

  public int getBalance() {
    return balance;
  }

  public int getDebit() {
    return debit;
  }

}

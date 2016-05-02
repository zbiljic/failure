package com.zbiljic.failure;

import com.zbiljic.failure.http.HttpStatus;

/**
 * @author Nemanja Zbiljic
 */
public class InsufficientFundsFailure extends ThrowableFailure {

  static final String SERVICE = "EXAMPLE";

  private final int balance;
  private final int debit;

  public InsufficientFundsFailure(final int balance, final int debit) {
    this.balance = balance;
    this.debit = debit;
  }

  @Override
  public String getService() {
    return SERVICE;
  }

  @Override
  public int getStatus() {
    return 1;
  }

  @Override
  public int getCode() {
    return HttpStatus.UNPROCESSABLE_ENTITY.value();
  }

  @Override
  public String getTitle() {
    return "Insufficient Funds";
  }

  public int getBalance() {
    return balance;
  }

  public int getDebit() {
    return debit;
  }

}

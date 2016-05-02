package com.zbiljic.failure;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * @author Nemanja Zbiljic
 */
public class ExceptionalTest {

  @Test
  public void testShouldBeAbleToThrowAndCatchThrowableProblem() {
    try {
      throw unit().propagate();
    } catch (final InsufficientFundsFailure failure) {
      assertEquals(failure.getBalance(), 10);
    } catch (final OutOfStockFailure failure) {
      fail("Should not have been out-of-stock");
    } catch (final Exception e) {
      fail("Should not have been unspecific failure");
    }
  }

  private Exceptional unit() {
    return new InsufficientFundsFailure(10, -20);
  }

}
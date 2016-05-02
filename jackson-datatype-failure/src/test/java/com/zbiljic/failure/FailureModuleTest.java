package com.zbiljic.failure;

import org.testng.annotations.Test;

/**
 * @author Nemanja Zbiljic
 */
public class FailureModuleTest {

  @Test
  public void testDefaultConstructorShouldBuildIndexCorrectly() {
    new FailureModule();
  }

}
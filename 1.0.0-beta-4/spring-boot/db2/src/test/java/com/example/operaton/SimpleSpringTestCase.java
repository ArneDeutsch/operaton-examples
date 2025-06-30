package com.example.operaton;

import org.operaton.bpm.engine.spring.test.Spring5TestHelper;
import org.operaton.bpm.engine.spring.test.SpringProcessEngineTestCase;
import org.operaton.bpm.engine.spring.test.SpringTestHelper;

/**
 * Minimal adapter returning the default test helper to avoid ServiceLoader lookup.
 */
abstract class SimpleSpringTestCase extends SpringProcessEngineTestCase {
  @Override
  protected SpringTestHelper lookupTestHelper() {
    return new Spring5TestHelper();
  }

  @Override
  protected String[] getConfigLocations() {
    return new String[] {"classpath:operaton.cfg.xml"};
  }
}

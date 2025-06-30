package com.example.operaton;

import org.operaton.bpm.engine.spring.test.Spring5TestHelper;
import org.operaton.bpm.engine.spring.test.SpringProcessEngineTestCase;
import org.operaton.bpm.engine.spring.test.SpringTestHelper;
import org.springframework.test.context.ContextConfiguration;

/**
 * Minimal adapter returning the default test helper to avoid ServiceLoader lookup.
 */
@ContextConfiguration(locations = "classpath:operaton.cfg.xml")
abstract class SimpleSpringTestCase extends SpringProcessEngineTestCase {
  @Override
  protected SpringTestHelper lookupTestHelper() {
    return new Spring5TestHelper();
  }
}

package org.operaton.bpm.spring.test;

import org.operaton.bpm.engine.ProcessEngine;
import org.operaton.bpm.engine.RuntimeService;
import org.operaton.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Minimal stub for examples requiring Spring integration tests.
 * Provides injected process engine services via Spring Boot.
 */
@SpringBootTest
public abstract class SpringProcessEngineTestCase {

  @Autowired
  protected ProcessEngine processEngine;

  @Autowired
  protected RuntimeService runtimeService;

  @Autowired
  protected TaskService taskService;
}

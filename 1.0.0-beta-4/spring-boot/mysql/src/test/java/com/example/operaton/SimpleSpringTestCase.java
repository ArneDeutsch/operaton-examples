package com.example.operaton;

import org.operaton.bpm.engine.ProcessEngine;
import org.operaton.bpm.engine.RuntimeService;
import org.operaton.bpm.engine.TaskService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInfo;

/**
 * Lightweight base class that loads {@code operaton.cfg.xml} and exposes the
 * engine and services for tests. It avoids the heavier
 * {@code SpringProcessEngineTestCase} which relies on additional helpers that
 * are missing from the examples.
 */
abstract class SimpleSpringTestCase {

  protected ConfigurableApplicationContext context;
  protected ProcessEngine processEngine;
  protected RuntimeService runtimeService;
  protected TaskService taskService;

  @BeforeEach
  void setUp(TestInfo info) {
    context = new ClassPathXmlApplicationContext("operaton.cfg.xml");
    processEngine = context.getBean(ProcessEngine.class);
    runtimeService = processEngine.getRuntimeService();
    taskService = processEngine.getTaskService();
  }

  @AfterEach
  void tearDown(TestInfo info) {
    if (processEngine != null) {
      processEngine.close();
    }
    if (context != null) {
      context.close();
    }
  }
}

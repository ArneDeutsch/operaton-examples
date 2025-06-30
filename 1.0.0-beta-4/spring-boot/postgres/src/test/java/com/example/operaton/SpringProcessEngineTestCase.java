package com.example.operaton;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.operaton.bpm.engine.ProcessEngine;
import org.operaton.bpm.engine.ProcessEngineConfiguration;
import org.operaton.bpm.engine.RuntimeService;
import org.operaton.bpm.engine.TaskService;

/** Minimal replacement for the spring testing base class.
 *  Builds a process engine from the operaton.cfg.xml file for each test. */
public abstract class SpringProcessEngineTestCase {

  protected ProcessEngine processEngine;
  protected RuntimeService runtimeService;
  protected TaskService taskService;

  @BeforeEach
  public void initProcessEngine() {
    ProcessEngineConfiguration config =
        ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("operaton.cfg.xml");
    processEngine = config.buildProcessEngine();
    runtimeService = processEngine.getRuntimeService();
    taskService = processEngine.getTaskService();
  }

  @AfterEach
  public void closeProcessEngine() {
    if (processEngine != null) {
      processEngine.close();
    }
  }
}

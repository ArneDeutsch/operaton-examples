package com.example.operaton;

import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.operaton.bpm.engine.RuntimeService;
import org.operaton.bpm.engine.TaskService;
import org.operaton.bpm.engine.ProcessEngine;
import org.operaton.bpm.engine.test.Deployment;
import org.operaton.bpm.engine.test.junit5.ProcessEngineExtension;
import org.operaton.bpm.engine.variable.VariableMap;
import org.operaton.bpm.engine.variable.Variables;
import org.operaton.bpm.engine.runtime.ProcessInstance;
import org.operaton.bpm.engine.task.Task;

import static org.junit.jupiter.api.Assertions.*;
import static org.operaton.bpm.engine.variable.Variables.*;

@ExtendWith(ProcessEngineExtension.class)
class InvoiceTestCase {

  ProcessEngine processEngine;
  RuntimeService runtimeService;
  TaskService taskService;

  @Deployment(resources = {"invoice.v1.bpmn", "invoiceBusinessDecisions.dmn"})
  @Test
  void testHappyPath() {
    InputStream invoiceInputStream = Application.class.getClassLoader().getResourceAsStream("invoice.pdf");
    VariableMap variables = Variables.createVariables()
        .putValue("creditor", "Great Pizza for Everyone Inc.")
        .putValue("amount", 300.0d)
        .putValue("invoiceCategory", "Travel Expenses")
        .putValue("invoiceNumber", "GPFE-23232323")
        .putValue("invoiceDocument", fileValue("invoice.pdf")
            .file(invoiceInputStream)
            .mimeType("application/pdf")
            .create());

    ProcessInstance pi = runtimeService.startProcessInstanceByKey("invoice", variables);
    Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
    assertEquals("approveInvoice", task.getTaskDefinitionKey());
  }
}

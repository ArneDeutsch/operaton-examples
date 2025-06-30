package com.example.operaton;

import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.operaton.SimpleSpringTestCase;
import org.operaton.bpm.engine.RuntimeService;
import org.operaton.bpm.engine.TaskService;
import org.operaton.bpm.engine.test.Deployment;
import org.operaton.bpm.engine.variable.VariableMap;
import org.operaton.bpm.engine.variable.Variables;
import org.operaton.bpm.engine.runtime.ProcessInstance;
import org.operaton.bpm.engine.task.Task;

import static org.junit.jupiter.api.Assertions.*;
import static org.operaton.bpm.engine.variable.Variables.*;

class InvoiceTestCase extends SimpleSpringTestCase {

  @BeforeEach
  void setUpEngine() throws Exception {
    super.setUp();
  }

  @AfterEach
  void tearDownEngine() throws Exception {
    super.tearDown();
  }


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

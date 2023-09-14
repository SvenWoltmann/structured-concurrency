package eu.happycoders.structuredconcurrency.service;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.model.InvoiceTemplate;
import java.util.concurrent.ThreadLocalRandom;

public class InvoiceTemplateService {

  public InvoiceTemplate getTemplate(String language) throws InterruptedException {
    log("Loading template");
    try {
      Thread.sleep(ThreadLocalRandom.current().nextLong(500, 1000));
    } catch (InterruptedException e) {
      log("Template loading was interrupted");
      throw e;
    }

    if (ThreadLocalRandom.current().nextDouble() > 0.8) {
      log("Error loading template");
      throw new RuntimeException("Error loading template");
    }

    log("Finished loading template");
    return new InvoiceTemplate();
  }
}

package eu.happycoders.structuredconcurrency.demo1_invoice.service;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.demo1_invoice.model.InvoiceTemplate;
import java.util.concurrent.ThreadLocalRandom;

public class InvoiceTemplateService {

  public InvoiceTemplate getTemplate(String language) throws InterruptedException {
    log("Loading template");
    try {
      long minSleepTime = Properties.PRESENTATION_MODE ? 600 : 500;
      long maxSleepTime = Properties.PRESENTATION_MODE ? 700 : 1000;
      Thread.sleep(ThreadLocalRandom.current().nextLong(minSleepTime, maxSleepTime));
    } catch (InterruptedException e) {
      log("Template loading was interrupted");
      throw e;
    }

    double errorProbability = Properties.PRESENTATION_MODE ? 0.5 : 0.2;
    if (ThreadLocalRandom.current().nextDouble() < errorProbability) {
      log("Error loading template");
      throw new RuntimeException("Error loading template");
    }

    log("Finished loading template");
    return new InvoiceTemplate();
  }
}
